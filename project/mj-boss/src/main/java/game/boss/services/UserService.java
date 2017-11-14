package game.boss.services;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.function.Consumer;

import javax.sql.DataSource;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.isnowfox.core.thread.FrameQueueContainer;
import com.isnowfox.util.JsonUtils;

import game.boss.ServerRuntimeException;
import game.boss.dao.dao.SettingDao;
import game.boss.dao.dao.UserDao;
import game.boss.dao.dao.UserLoginLogDao;
import game.boss.dao.dao.UserTransferLogDao;
import game.boss.dao.entity.SettingDO;
import game.boss.dao.entity.UserDO;
import game.boss.dao.entity.UserLoginLogDO;
import game.boss.dao.entity.UserQuestionDO;
import game.boss.dao.entity.UserTransferLogDO;
import game.boss.dao.entity.UserDO.Key;
import game.boss.model.AliPayConfig;
import game.boss.model.User;
import game.boss.model.WXPayConfig;
import game.boss.model.WeixinUserInfo;
import game.boss.msg.WXPayPxMsg;
import game.boss.net.BossService;
import game.boss.utils.RSACoderUtils;
import game.boss.utils.WXPayUtil;
import mj.data.Config;
import mj.data.OrderInfo;
import mj.net.message.login.AliPayRet;
import mj.net.message.login.ClubInfo;
import mj.net.message.login.JoinClub;
import mj.net.message.login.JoinClubRet;
import mj.net.message.login.Location;
import mj.net.message.login.Login;
import mj.net.message.login.LoginError;
import mj.net.message.login.LoginRet;
import mj.net.message.login.Pay;
import mj.net.message.login.Question;
import mj.net.message.login.Radio;
import mj.net.message.login.Record;
import mj.net.message.login.RepeatLoginRet;
import mj.net.message.login.Transfer;
import mj.net.message.login.TransferRet;
import mj.net.message.login.WXPay;
import mj.net.message.login.WXPayQuery;
import mj.net.message.login.WXPayRet;
import mj.net.message.login.WXPaySuccess;
import mj.net.message.login.WXShow;

@Service
public class UserService extends FrameQueueContainer implements BaseService {
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	public static final String LOGIN_TYPE_SMS = "SMS";
	public static final String LOGIN_TYPE_ANONYMOUS = "ANONYMOUS";
	public static final String LOGIN_TYPE_TOKEN = "TOKEN";
	public static final String LOGIN_TYPE_WEIXIN = "WEIXIN";
	public static final int DEL_GOLD = 2;
	private static final Map<String, User> wxPayUserSession = new HashMap<String,User>();
	private static final Map<String, Integer> wxPayGoldSession = new HashMap<String,Integer>();
	private final HashMap<Integer, User> idMap = new HashMap<Integer,User>();
	private final HashMap<Integer, Entry<Login, User>> waitMap = new HashMap<Integer, Entry<Login, User>>();
	@Autowired
	private BossService bossService;
	@Autowired
	private AsyncDbService asyncDbService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserLoginLogDao userLoginLogDao;
	@Autowired
	private RoomService roomService;
	@Autowired
	private SmsService smsService;
	@Autowired
	private SettingService settingService;
	@Autowired
	private WXPayConfig wxPayconfig;
	@Autowired
	private AliPayConfig aliPayConfig;
	@Autowired
	private UserTransferLogDao userTransferLogDao;
	@Autowired
	private SettingDao settingDao;
	@Value("${admin.privateKey}")
	private String privateKey;

	public UserService() {
		super(8, 1024);
		this.start();
	}

	public void loginByOpenId(Login msg, User user) {
		if (msg.getType().equals("SMS")) {
			this.smsService.check(msg.getOpenId(), msg.getCode(), (checkResult) -> {
				if (checkResult == null) {
					this.innerLogin(msg, user);
				} else {
					user.noticeError(checkResult);
					user.send(new LoginError());
				}
			});
		} else if (msg.getType().equals("TOKEN")) {
			this.innerLogin(msg, user);
		} else if (msg.getType().equals("ANONYMOUS")) {
			this.innerLogin(msg, user);
		} else if (msg.getType().equals("WEIXIN")) {
			this.innerLogin(msg, user);
		}

	}

	private void innerLogin(Login msg, User user) {
		this.asyncDbService.excuete(() -> {
			String openId = msg.getOpenId();
			WeixinUserInfo weixinUser = null;
			UserDO userDO;
			if (msg.getType().equals("SMS")) {
				userDO = (UserDO) this.userDao.findObject("mobile", openId);
			} else if (msg.getType().equals("ANONYMOUS")) {
				userDO = null;
				openId = UUID.randomUUID().toString();
			} else if (msg.getType().equals("WEIXIN")) {
				try {
					byte[] ex = RSACoderUtils.decryptByPrivateKey(Base64.decodeBase64(msg.getCode()), this.privateKey);
					weixinUser = (WeixinUserInfo) JsonUtils.deserialize(new String(ex, "utf8"), WeixinUserInfo.class);
					openId = weixinUser.getOpenid();
					userDO = (UserDO) this.userDao.findObject("open_id", openId);
					if (userDO != null) {
						userDO.setName(StringUtils.substring(weixinUser.getNickname(), 0, 8));
						userDO.setSex(weixinUser.transformSex());
						userDO.setAvatar(weixinUser.getHeadimgurl());
						this.userDao.update(userDO);
					}
				} catch (Exception arg7) {
					throw new RuntimeException(arg7);
				}
			} else {
				if (!msg.getType().equals("TOKEN")) {
					return;
				}
				userDO = (UserDO) this.userDao.findObject(UserDO.TABLE_INFO.LOGIN_TOKEN, openId);
				if (userDO == null) {
					user.send(new LoginError());
					return;
				}
			}

			if (userDO != null) {
				this.login(user, userDO, msg);
			} else {
				try {
					userDO = new UserDO();
					userDO.setOpenId(openId);
					userDO.setName(openId.substring(1, 9));
					userDO.setUuid(com.isnowfox.util.UUID.generateNoSep());
					Date ex1 = new Date();
					userDO.setUpdateDate(ex1);
					userDO.setCreateDate(ex1);
					
					SettingDO settingDO = settingDao.get(1);
					int initGold = 20;
					if(settingDO!=null){
						initGold = settingDO.getInitGold();
					}
					userDO.setGold(initGold);
					if (msg.getType().equals("SMS")) {
						userDO.setMobile(openId);
					} else if (msg.getType().equals("WEIXIN") && weixinUser != null) {
						userDO.setName(weixinUser.getNickname());
						userDO.setSex(weixinUser.transformSex());
						userDO.setAvatar(weixinUser.getHeadimgurl());
					}

					int id1 = (int) this.userDao.insert(userDO);
					userDO.setId(id1);
					this.login(user, userDO, msg);
				} catch (DuplicateKeyException arg8) {
					log.error("重复的注册!" + openId, arg8);
					userDO = (UserDO) this.userDao.findObject("open_id", openId);
					if (userDO == null) {
						log.error("重复注册后检查登录失败!关闭连接" + openId, arg8);
						user.close();
					} else {
						this.login(user, userDO, msg);
					}
				}
			}

		});
	}


	public void logout(User user) {
		this.run(() -> {
			User loginUserEntry = (User) this.idMap.get(Integer.valueOf(user.getUserId()));
			if (loginUserEntry == user) {
				this.closeUser(user);
			} else {
				log.error("严重错误,出现不一致,{} <--> {}", user, loginUserEntry);
				this.closeUser(user);
				this.closeUser(loginUserEntry);
			}

			Entry loginUserEntry1 = (Entry) this.waitMap.get(Integer.valueOf(user.getUserId()));
			if (loginUserEntry1 != null && loginUserEntry1.getValue() != user) {
				this.waitMap.remove(Integer.valueOf(user.getUserId()));
				this.login((User) loginUserEntry1.getValue(), ((User) loginUserEntry1.getValue()).getUserDO(),
						(Login) loginUserEntry1.getKey());
			}

		});
	}

	private void closeUser(User user) {
		if (this.frameThread != Thread.currentThread()) {
			throw new ServerRuntimeException("只能在指定的线程调用");
		} else if (user != null) {
			UserDO userDO = user.getUserDO();
			if (userDO != null) {
				this.idMap.remove(Integer.valueOf(userDO.getId()));
				this.roomService.checkOffline(user);
				UserLoginLogDO loginLog = user.getLoginLog();
				Date now = new Date();
				if (loginLog != null) {
					this.asyncDbService.excuete(user, () -> {
						if (loginLog.getId() > 0) {
							this.userLoginLogDao.updatePartial("logout_date", now, loginLog.getKey());
						}

					});
				}

			}
		}
	}

	public void handlerUser(int userId, Consumer<User> callback) {
		this.run(() -> {
			User user = (User) this.idMap.get(Integer.valueOf(userId));
			callback.accept(user);
		});
	}

	public void minusGold(int userId, Config config) {
		this.run(() -> {
			User user = (User) this.idMap.get(Integer.valueOf(userId));
			UserDO userDO = this.userDao.get(user.getUserId());
			if (user != null && userDO.getGold() < user.getUserDO().getGold()) {
				int gold = user.getUserDO().getGold() - userDO.getGold();
				UserDO userDO1 = user.getUserDO();
				userDO.setGold(userDO1.getGold() - gold);
				user.send(new Pay(-gold));
			}

		});
	}

	public void pay(int userId, int gold) {
		this.run(() -> {
			User user = (User) this.idMap.get(Integer.valueOf(userId));
			if (user != null) {
				UserDO userDO = user.getUserDO();
				userDO.setGold(userDO.getGold() + gold);
				userDO.setHistoryGold(userDO.getHistoryGold() + gold);
				user.send(new Pay(gold));
			}

			this.asyncDbService.excuete(userId, () -> {
				this.userDao.incrementUpdatePartial("gold", Integer.valueOf(gold), "history_gold",
						Integer.valueOf(gold), new Key(userId));
			});
		});
	}

	private void login(User user, UserDO userDO, Login msg) {
		this.run(() -> {
			User loginLog = (User) this.idMap.get(Integer.valueOf(userDO.getId()));
			if (loginLog != null) {
				loginLog.send(new RepeatLoginRet());
				loginLog.close();
				Entry msgRet1 = (Entry) this.waitMap.get(Integer.valueOf(userDO.getId()));
				if (msgRet1 != null) {
					((User) msgRet1.getValue()).close();
				}

				user.setUserDO(userDO);
				this.waitMap.put(Integer.valueOf(userDO.getId()), new SimpleImmutableEntry(msg, user));
			} else {
				this.idMap.put(Integer.valueOf(userDO.getId()), user);
				userDO.setLoginToken(UUID.randomUUID().toString());
				userDO.setIp(user.getIp());
				userDO.setLongitude(NumberUtils.toDouble(msg.getLongitude()));
				userDO.setLatitude(NumberUtils.toDouble(msg.getLatitude()));
				user.setUserDO(userDO);
				UserLoginLogDO loginLog1 = new UserLoginLogDO();
				loginLog1.setUserId(userDO.getId());
				loginLog1.setLoginDate(new Date());
				loginLog1.setIp(user.getIp());
				loginLog1.setLoginToken(userDO.getLoginToken());
				user.setLoginLog(loginLog1);
				this.asyncDbService.excuete(user, () -> {
					long id = this.userLoginLogDao.insert(loginLog1);
					loginLog1.setId((int) id);
					HashMap<String,Object> m = new HashMap<String, Object>();
					m.put("login_token", userDO.getLoginToken());
					m.put("ip", userDO.getIp());
					m.put("longitude", Double.valueOf(userDO.getLongitude()));
					m.put("latitude", Double.valueOf(userDO.getLatitude()));
					this.userDao.updatePartial(m, userDO.getKey());
					
				});
				LoginRet msgRet = new LoginRet(userDO.getId(), userDO.getName(), userDO.getOpenId(), userDO.getUuid(),
						userDO.getAvatar(), userDO.getSex(), (String) null, userDO.getGold(), userDO.getLoginToken(),
						userDO.getIp(),userDO.getLevel());
				this.roomService.checkUserRoom(user, (checkId) -> {
					msgRet.setRoomCheckId(checkId);
					user.send(this.settingService.getSettingMsg());
					user.send(msgRet);
					Record record = this.getRecored(user.getUserId());
					ClubInfo clubInfo = this.getClubInfo(user.getUserId());
					if (clubInfo != null) {
						user.send(clubInfo);
					}
					user.send(record);
					SettingDO settingDO = settingDao.findObject(SettingDO.TABLE_INFO.ID,SettingService.DEFAULT_ID);
					user.send(new Radio(settingDO.getRadio()));
				});
				
			}
		});
//		run(()->{
//			int count = 100000;
//			while(count<=count+5000){
//				Pong pong = new Pong();
//				pong.setTime("");
//				pong.setTest(count++);
//				user.send(pong);
//				
//			}
//		});
	}

	private ClubInfo getClubInfo(int userId) {
		ClubInfo clubInfo = null;
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSet e2;
		try {
			String queryInfo;
			try {
				String e = "SELECT pid  FROM user WHERE id=?";
				queryInfo = "SELECT notice FROM `user` WHERE id=?";
				String e1 = "select * from proxy_user where proxy_gameid=? and proxy_status=? order by id desc";
				connection = this.userDao.getDataSource().getConnection();
				pst = connection.prepareStatement(e);
				pst.setInt(1, userId);
				rs = pst.executeQuery();
				if (rs == null) {
					Object proxyId1 = null;
					return (ClubInfo) proxyId1;
				}
				clubInfo = new ClubInfo();
				rs.next();
				int proxyId = rs.getInt("pid");
				rs.close();
				rs = null;
				
				if (pst != null) {
					pst.close();
					pst = null;
				}
				
				if (proxyId != 0) {
					pst = connection.prepareStatement(queryInfo);
					pst.setInt(1, proxyId);
					e2 = pst.executeQuery();
					String name;
					if (e2 != null) {
						e2.next();
						name = e2.getString("notice");
						clubInfo.setNotice(name);
						e2.close();
						e2 = null;
						pst.close();
						pst = null;
					} else {
						clubInfo.setNotice("");
					}

					pst = connection.prepareStatement(e1);
					pst.setInt(1, proxyId);
					pst.setString(2, "2");
					rs = pst.executeQuery();
					if (rs != null) {
						rs.next();
						name = rs.getString("proxy_truename");
						String e3 = rs.getString("proxy_weixin");
						clubInfo.setClubId(proxyId + "");
						clubInfo.setCreateUserName(name);
						clubInfo.setWxNo(e3);
					}

					ClubInfo name1 = clubInfo;
					return name1;
				}else{
					return null;
				}

			
			} catch (SQLException arg22) {
				log.error(arg22.getMessage());
				
			}
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}

				if (pst != null && !pst.isClosed()) {
					pst.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException arg21) {
				log.error(arg21.getMessage());
			}

		}
		return clubInfo;

	}

	private Record getRecored(int userId) {
		Record record = new Record();
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			String e = "SELECT COUNT(0) as c FROM room_result WHERE winUserId=?";
			
			
			
			String queryAllCount = "SELECT COUNT(0) as c FROM room_result WHERE user0 = ? OR user1=? OR user2=? OR user3=?";
			connection = this.userDao.getDataSource().getConnection();
			pst = connection.prepareStatement(queryAllCount);

			int allCount;
			for (allCount = 1; allCount <= 4; ++allCount) {
				pst.setInt(allCount, userId);
			}

			rs = pst.executeQuery();
			if (rs != null) {
				rs.next();
				allCount = rs.getInt("c");
				rs.close();
				if (pst != null) {
					pst.close();
					pst = null;
				}

				if (allCount > 0) {
					int winCount = 0;
					for (int i = 0; i < 4; i++) {
						String winCountSql = "SELECT COUNT(0) as c FROM room_result WHERE user"+i+" = ? and hu_pai_index = "+i;
						pst = connection.prepareStatement(winCountSql);
						pst.setInt(1, userId);
						rs = pst.executeQuery();
						if (rs != null) {
							rs.next();
							winCount += rs.getInt("c");
							
						}
					}
					record.setMajiangNum(allCount);
					record.setMajiangWinCount(winCount);
					record.setPokerNum(0);
					record.setPokerWinCount(0);
				}
			}
		} catch (SQLException arg17) {
			log.error(arg17.getMessage());
		} finally {
			try {
				if (rs != null && !rs.isClosed()) {
					rs.close();
				}

				if (pst != null && !pst.isClosed()) {
					pst.close();
				}

				if (connection != null) {
					connection.close();
				}
			} catch (SQLException arg16) {
				log.error(arg16.getMessage());
			}

		}

		return record;
	}

	
	
	protected void threadMethod(int frameCount, long time, long passedTime) {
	}

	protected void errorHandler(Throwable e) {
		log.error("严重异常", e);
	}

	public void treasferGold(User user, Transfer msg) {
		boolean datebaseGold = true;
		UserDO srcuserDO = (UserDO) this.userDao.findObject("id", Integer.valueOf(user.getUserId()));
		UserDO destUserDO = (UserDO) this.userDao.findObject("id", Integer.valueOf(msg.getDestId()));
		if (srcuserDO != null && msg.getGold() > 0) {
			DataSource ds = this.userDao.getDataSource();
			Connection connect = null;
			Statement stm = null;
			boolean flage = true;

			try {
				connect = ds.getConnection();
				connect.setAutoCommit(false);
				stm = connect.createStatement();
				String e = "select * from user where id = " + user.getUserId();
				ResultSet ignore = stm.executeQuery(e);
				if (ignore != null) {
					ignore.next();
				}

				int datebaseGold1 = ignore.getInt("gold");
				if (ignore == null || datebaseGold1 < msg.getGold()) {
					user.send(new TransferRet(false, "房卡数不足"));
					return;
				}

				stm.addBatch("update `user` set gold = " + (srcuserDO.getGold() - msg.getGold()) + "  where id = "
						+ user.getUserId());
				stm.addBatch("update `user` set gold = " + (destUserDO.getGold() + msg.getGold()) + "  where id = "
						+ msg.getDestId());
				int[] rst = stm.executeBatch();
				flage = rst.length == 2;
				connect.commit();
				UserTransferLogDO transfer = new UserTransferLogDO();
				transfer.setSrcUserId(user.getUserId());
				transfer.setDestUserId(msg.getDestId());
				transfer.setCreateTime(new Date());
				transfer.setGold(msg.getGold());
				this.userTransferLogDao.insert(transfer);
				if (flage) {
					user.getUserDO().setGold(datebaseGold1 - msg.getGold());
					user.send(new TransferRet(flage, "转账成功！"));
					user.send(new Pay(-msg.getGold()));
				} else {
					log.error("操作扣除金币失败");
				}
			} catch (SQLException arg24) {
				user.send(new TransferRet(false, "未知错误，请联系管理员"));

				try {
					if (connect != null) {
						connect.rollback();
					}
				} catch (Exception arg23) {
					arg23.printStackTrace();
				}

				arg24.printStackTrace();
			} finally {
				try {
					if (stm != null) {
						stm.close();
					}
				} catch (Exception arg22) {
					;
				}
				if(connect!=null){
					try {
						connect.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		} else {
			user.send(new TransferRet(false, "未知错误，请联系管理员"));
		}

	}

	public void payGold(WXPay msg, User user) {
		if (msg.getPayType() == 0) {
			this.wxPay(msg, user);
		} else {
			this.aliPay(msg, user);
		}

	}

	private void aliPay(WXPay msg, User user) {
		int gold = msg.getGoldCount();
		int price = this.getPriveByLevel(gold, user);
		String url = this.aliPayConfig.getAlipay_Url();
		String appid = this.aliPayConfig.getApp_id();
		String app_private_key = this.aliPayConfig.getApp_private_key();
		String app_public_key = this.aliPayConfig.getApp_public_key();
		String sellId = this.aliPayConfig.getPartner_id();
		String notifyUrl = this.aliPayConfig.getNotifyurl();
		String body = "亲友局-游戏充值";
		String subject = "购买房卡";
		String timeoutExpress = this.aliPayConfig.getTimeoutExpress();
		double money = (double) (gold * price) / 100.0D;
		DefaultAlipayClient alipayClient = new DefaultAlipayClient(url, appid, app_private_key, "json", "UTF-8",
				app_public_key, "RSA2");
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		String trade_on = WXPayUtil.getTrade_no();
		model.setBody(body);
		model.setSellerId(sellId);
		model.setSubject(subject);
		model.setOutTradeNo(trade_on);
		model.setTimeoutExpress(timeoutExpress);
		model.setTotalAmount(String.valueOf(money));
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setNotifyUrl(notifyUrl);
		request.setBizModel(model);

		try {
			AlipayTradeAppPayResponse e = (AlipayTradeAppPayResponse) alipayClient.sdkExecute(request);
			log.info("aliPay Info :{" + this.aliPayConfig + "}");
			user.send(new AliPayRet(e.getBody()));
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setCount((int) (money * 100.0D));
			orderInfo.setGold(gold);
			orderInfo.setOrder_id(WXPayUtil.getTrade_no());
			orderInfo.setPlat_order_id(trade_on);
			orderInfo.setStatus(1);
			orderInfo.setUserid(user.getUserId());
			orderInfo.setTime(new java.sql.Date(System.currentTimeMillis()));
			orderInfo.setType(msg.getPayType());
			this.addOrderInfoToDB(orderInfo);
			wxPayUserSession.put(trade_on, user);
			wxPayGoldSession.put(trade_on, Integer.valueOf(gold));
		} catch (AlipayApiException arg21) {
			log.info("aliPay error :{" + arg21.getMessage() + "}Config:{" + this.aliPayConfig + "}");
			user.send(new AliPayRet("-1"));
		}

	}

	public void aliPayhuidiao(WXPayPxMsg msg) throws AlipayApiException {
		if (msg.getResult() == 1) {
			String trade_no = msg.getTrade_no();
			User user = (User) wxPayUserSession.get(trade_no);
			int gold = ((Integer) wxPayGoldSession.get(trade_no)).intValue();
			this.addGoldFromPay(gold, user);
			user.send(new WXPaySuccess(true, msg.getMoney(), gold));
			this.updateOrderInfo(trade_no);
			wxPayUserSession.remove(trade_no);
			wxPayGoldSession.remove(trade_no);
		}

	}

	private void updateOrderInfo(String trade_no) {
		Connection connection = null;
		PreparedStatement pst = null;

		try {
			connection = this.userDao.getDataSource().getConnection();
			String e = "update online_pay_log set status=2 where plat_id=?";
			pst = connection.prepareStatement(e);
			pst.setString(1, trade_no);
			pst.execute();
		} catch (SQLException arg4) {
			arg4.printStackTrace();
		}finally {
			closeSql(null, pst, connection);
		}

	}

	private int getPriveByLevel(int gold, User user) {
		UserDO userDO = (UserDO) this.userDao.findObject("id", Integer.valueOf(user.getUserId()));
		int level = userDO.getLevel();
		Connection connection = null;
		int price = -1;
		 

		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			connection = this.userDao.getDataSource().getConnection();
			String e = "select price from price where level=" + level;
			pst = connection.prepareStatement(e);
			rs = pst.executeQuery();
			log.info("Pay Info: price query");
			if (null != rs) {
				rs.next();
				price = rs.getInt("price");
			}

			return price;
		} catch (Exception arg9) {
			log.error("Pay error:" + arg9.getMessage());
			return -1;
		}finally{
			closeSql(rs, pst, connection);
		}
	}
	
	private void closeSql(ResultSet rs,PreparedStatement pst,Connection conn){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pst!=null){
			try {
				pst.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void wxPay(WXPay msg, User user) {
		int gold = msg.getGoldCount();
		String trade_no = WXPayUtil.getTrade_no();
		boolean price = true;

		try {
			int price1 = this.getPriveByLevel(gold, user);
			int e1 = price1 * gold;
			String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder", new Object[0]);
			String entity = this.getProductArgs(user.getIp(), e1, this.wxPayconfig, trade_no);
			String content = WXPayUtil.httpPost(url, entity);
			Map resultMap = WXPayUtil.decodeXml(content);
			if (null != resultMap) {
				String result_code = (String) resultMap.get("return_code");
				if (result_code.toLowerCase().equals("SUCCESS".toLowerCase())) {
					String appid = (String) resultMap.get("appid");
					String noncestr = WXPayUtil.getNonceStr();
					String myPackage = "Sign=WXPay";
					String partnerid = (String) resultMap.get("mch_id");
					String prepayid = (String) resultMap.get("prepay_id");
					String timestamp = String.valueOf(System.currentTimeMillis() / 1000L);
					LinkedList signParams = new LinkedList();
					signParams.add(new UserService.NameValuePair("appid", appid));
					signParams.add(new UserService.NameValuePair("noncestr", noncestr));
					signParams.add(new UserService.NameValuePair("package", myPackage));
					signParams.add(new UserService.NameValuePair("partnerid", partnerid));
					signParams.add(new UserService.NameValuePair("prepayid", prepayid));
					signParams.add(new UserService.NameValuePair("timestamp", timestamp));
					String sign = this.getPackageSign(signParams, this.wxPayconfig.getKey());
					
					
					WXPayRet msg_ret = new WXPayRet(true, appid, noncestr, myPackage, partnerid, prepayid, timestamp,
							sign);
					user.send(msg_ret);
					
					
					OrderInfo orderInfo = new OrderInfo();
					orderInfo.setCount(e1);
					orderInfo.setGold(gold);
					orderInfo.setOrder_id(WXPayUtil.getTrade_no());
					orderInfo.setPlat_order_id(trade_no);
					orderInfo.setStatus(1);
					orderInfo.setUserid(user.getUserId());
					orderInfo.setTime(new java.sql.Date(System.currentTimeMillis()));
					orderInfo.setType(msg.getPayType());
					this.addOrderInfoToDB(orderInfo);
					log.info("{}:wxPay success" + content);
					wxPayUserSession.put(trade_no, user);
					wxPayGoldSession.put(trade_no, Integer.valueOf(gold));
				} else {
					log.error("{}:WXPay:content=" + content);
					user.send(new WXPayRet(false));
				}

				log.info("WXPay Config{" + this.wxPayconfig + "}");
			} else {
				log.error("{}:deccodeXml=null" + content);
				user.send(new WXPayRet(false));
			}
		} catch (Exception arg21) {
			log.error(arg21.getMessage());
			user.send(new WXPayRet(false));
		}

	}

	private void addOrderInfoToDB(OrderInfo info) {
		Connection connection = null;
		PreparedStatement pst = null;

		try {
			connection = this.userDao.getDataSource().getConnection();
			String e = "insert into online_pay_log values(?,?,?,?,?,?,?,?)";
			pst = connection.prepareStatement(e);
			byte i = 0;
			pst.setInt(i + 1, info.getUserid());
			pst.setString(i + 2, info.getOrder_id());
			pst.setString(i + 3, info.getPlat_order_id());
			pst.setInt(i + 4, info.getGold());
			pst.setInt(i + 5, info.getCount());
			pst.setDate(i + 6, info.getTime());
			pst.setInt(i + 7, info.getStatus());
			pst.setInt(i + 8, info.getType());
			pst.execute();
			log.info("inset OrederINfo:" + info);
		} catch (SQLException arg17) {
			log.error("add OrderInfo error :{" + arg17.getMessage() + "}");
		} finally {
			if (null != pst) {
				try {
					pst.close();
				} catch (SQLException arg16) {
					log.error("close preparestatment erro:" + arg16.getMessage());
				}
			}

			if (null != connection) {
				try {
					connection.close();
				} catch (SQLException arg15) {
					log.error("close connection erro:" + arg15.getMessage());
				}
			}

		}

	}

	private String getProductArgs(String ip, int total_fee, WXPayConfig config, String trade_no) {
		String body = null;

		try {
			body = new String("Pay-gold".getBytes(), "utf-8");
		} catch (UnsupportedEncodingException arg10) {
			;
		}

		try {
			String e = WXPayUtil.getNonceStr();
			LinkedList packageParams = new LinkedList();
			packageParams.add(new UserService.NameValuePair("appid", config.getAppid()));
			packageParams.add(new UserService.NameValuePair("body", body));
			packageParams.add(new UserService.NameValuePair("mch_id", config.getMch_id()));
			packageParams.add(new UserService.NameValuePair("nonce_str", e));
			packageParams.add(new UserService.NameValuePair("notify_url", config.getNotify_url()));
			packageParams.add(new UserService.NameValuePair("out_trade_no", trade_no));
			packageParams.add(new UserService.NameValuePair("spbill_create_ip", ip));
			packageParams.add(new UserService.NameValuePair("total_fee", String.valueOf(total_fee)));
			packageParams.add(new UserService.NameValuePair("trade_type", "APP"));
			String sign = this.getPackageSign(packageParams, config.getKey());
			packageParams.add(new UserService.NameValuePair("sign", sign));
			String xmlstring = "<xml><appid>" + config.getAppid() + "</appid><body>" + body + "</body><mch_id>"
					+ config.getMch_id() + "</mch_id><nonce_str>" + e + "</nonce_str><notify_url>"
					+ config.getNotify_url() + "</notify_url><out_trade_no>" + trade_no
					+ "</out_trade_no><spbill_create_ip>" + ip + "</spbill_create_ip><total_fee>" + total_fee
					+ "</total_fee><trade_type>APP</trade_type><sign>" + sign + "</sign></xml>";
			return new String(xmlstring.getBytes(), "utf-8");
		} catch (Exception arg9) {
			return null;
		}
	}

	private String toXml(List<UserService.NameValuePair> params) throws UnsupportedEncodingException {
		String sb = "";
		sb = sb + "<xml>";

		for (int i = 0; i < params.size(); ++i) {
			sb = sb + "<" + ((UserService.NameValuePair) params.get(i)).getName() + ">";
			sb = sb + ((UserService.NameValuePair) params.get(i)).getValue();
			sb = sb + "</" + ((UserService.NameValuePair) params.get(i)).getName() + ">";
		}

		sb = sb + "</xml>";
		log.info("微信支付提交的xml文件" + sb);
		return new String(sb.getBytes(), "utf-8");
	}

	private String getPackageSign(List<UserService.NameValuePair> params, String key) {
		StringBuilder sb = new StringBuilder();

		for (int packageSign = 0; packageSign < params.size(); ++packageSign) {
			sb.append(((UserService.NameValuePair) params.get(packageSign)).getName());
			sb.append('=');
			sb.append(((UserService.NameValuePair) params.get(packageSign)).getValue());
			sb.append('&');
		}

		sb.append("key=");
		sb.append(key);

		try {
			String arg6 = WXPayUtil.getSign((new String(sb.toString().getBytes(), "utf-8")).getBytes()).toUpperCase();
			return arg6;
		} catch (UnsupportedEncodingException arg5) {
			arg5.printStackTrace();
			return null;
		}
	}

	public void wxPayHuiDiao(WXPayPxMsg msg) {
		if (msg.getResult() == 1) {
			String trade_no = msg.getTrade_no();
			User user = (User) wxPayUserSession.get(trade_no);
			int gold = ((Integer) wxPayGoldSession.get(trade_no)).intValue();
			WXPaySuccess success = new WXPaySuccess(true, msg.getMoney(), gold);
			this.addGoldFromPay(gold, user);
			this.updateOrderInfo(trade_no);
			user.send(success);
			log.info("send WXPaySeccess:{" + success + "}");
			wxPayUserSession.remove(trade_no);
			wxPayGoldSession.remove(trade_no);
		}

	}

	private void addGoldFromPay(int gold, User user) {
		Connection conn = null;
		PreparedStatement pst = null;

		try {
			conn = this.userDao.getDataSource().getConnection();
			String e = "UPDATE `user` SET gold = (gold+ " + gold + ") WHERE id = " + user.getUserId();
			pst = conn.prepareStatement(e);
			pst.execute();
			log.info("WXPayHuiDiao Info:update user sql:{" + e + "}");
		} catch (SQLException arg17) {
			log.error(arg17.getMessage());
		} finally {
			if (null != pst) {
				try {
					pst.close();
				} catch (SQLException arg15) {
					log.error("Close PrepareStatment error:{" + arg15.getMessage() + "}");
				}
			}
			if (null != conn) {
				try {
					conn.close();
				} catch (SQLException arg16) {
					log.error("Close Connection error:{" + arg16.getMessage() + "}");
				}
			}

			

		}

	}

	public void wxPayQuery(WXPayQuery msg, User user) {
		String out_trade_no = msg.getOut_trade_no();

		try {
			try {
				String e = "https://api.mch.weixin.qq.com/pay/orderquery";
				String appid = this.wxPayconfig.getAppid();
				String mch_id = this.wxPayconfig.getMch_id();
				String nonce_str = WXPayUtil.getNonceStr();
				String sign = "";
				LinkedList params = new LinkedList();
				params.add(new UserService.NameValuePair("appid", appid));
				params.add(new UserService.NameValuePair("mch_id", mch_id));
				params.add(new UserService.NameValuePair("nonce_str", nonce_str));
				params.add(new UserService.NameValuePair("out_trade_no", out_trade_no));
				sign = this.getPackageSign(params, this.wxPayconfig.getKey());
				params.add(new UserService.NameValuePair("sign", sign));
				String entity = this.toXml(params);
				String content = WXPayUtil.httpPost(e, entity);
				Map resultMap = WXPayUtil.decodeXml(content);
				if (null != resultMap) {
					if (((String) resultMap.get("return_code")).equals("SUCCESS")) {
						user.send(new WXPaySuccess(true, Integer.parseInt((String) resultMap.get("total_fee")),
								((Integer) wxPayGoldSession.get(msg.getOut_trade_no())).intValue()));
					} else {
						user.send(new WXPaySuccess(false, -1, -1));
					}
				}
			} catch (UnsupportedEncodingException arg15) {
				arg15.printStackTrace();
			}

		} finally {
			;
		}
	}

	public void wxshow(WXShow msg, User user) {
		Connection connection = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String todaystr = format.format(today) + " 00:00:00";

		try {
			connection = this.userDao.getDataSource().getConnection();
			String e = "insert into wxshow values(null,?,?)";
			PreparedStatement pst2 = connection.prepareStatement(e);
			pst2.setInt(1, user.getUserId());
			pst2.setString(2, dateTimeFormat.format(new Date(System.currentTimeMillis())));
			pst2.execute();
			pst2.close();
			String sql = "select count(0) as c from wxshow where userid=? and `time`> ?";
			pst = connection.prepareStatement(sql);
			pst.setInt(1, user.getUserId());
			pst.setString(2, todaystr);
			rs = pst.executeQuery();
			if (rs != null) {
				rs.next();
				int count = rs.getInt("c");
				if (count <= 2) {
					this.addGoldFromPay(1, user);
					user.send(new WXPaySuccess(true, 0, 1));
				}
			}
		} catch (SQLException arg29) {
			log.error("wxShow error:" + arg29.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException arg28) {
					;
				}
			}

			if (pst != null) {
				try {
					pst.close();
				} catch (SQLException arg27) {
					;
				}
			}

			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException arg26) {
					;
				}
			}

		}

	}

	public void joinClub(JoinClub joinClub, User user) {
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		PreparedStatement pst2 = null;

		try {
			String e = joinClub.getClubId();
			conn = this.userDao.getDataSource().getConnection();
			String queryClubCreater = "select `level` from `user` where `id` = ?";
			pst = conn.prepareStatement(queryClubCreater);
			pst.setInt(1, Integer.parseInt(e));
			rs = pst.executeQuery();
			if (rs == null) {
				user.send(new JoinClubRet(false, 0));
				return;
			}

			rs.next();
			boolean level = true;

			int level1;
			try {
				level1 = rs.getInt("level");
			} catch (Exception arg41) {
				user.send(new JoinClubRet(false, 0));
				return;
			}

			if (level1 == 0) {
				user.send(new JoinClubRet(false, 0));
				return;
			}

			String joinSql = "update `user` set pid=? where id = ?";
			pst2 = conn.prepareStatement(joinSql);
			pst2.setInt(1, Integer.parseInt(e));
			pst2.setInt(2, user.getUserId());
			boolean r = pst2.execute();
			if (!r) {
				user.send(new JoinClubRet(true));
				ClubInfo e1 = this.getClubInfo(user.getUserId());
				if (e1 != null) {
					user.send(e1);
				}

				return;
			}

			user.send(new JoinClubRet(false, -1));
		} catch (Exception arg42) {
			user.send(new JoinClubRet(false, -1));
			return;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception arg40) {
				user.send(new JoinClubRet(false, -1));
			}

			try {
				if (pst != null) {
					pst.close();
				}
			} catch (Exception arg39) {
				user.send(new JoinClubRet(false, -1));
			}

			try {
				if (pst2 != null) {
					pst2.close();
				}
			} catch (Exception arg38) {
				user.send(new JoinClubRet(false, -1));
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException arg37) {
					arg37.printStackTrace();
				}
			}

		}

	}

	public void addQuestion(Question msg, User user) {
		UserQuestionDO questionDO = new UserQuestionDO();
		if (msg.getContent() != null) {
			questionDO.setQuestion(msg.getContent());
			questionDO.setUserId(user.getUserId());
			questionDO.setTime(new Date(System.currentTimeMillis()));
			this.asyncDbService.excuete(() -> {
				Connection conn = null;
				PreparedStatement pst = null;

				try {
//					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss");
					conn = this.userDao.getDataSource().getConnection();
					String sql = "insert into user_question values(null,?,?,?)";
					pst = conn.prepareStatement(sql);
					pst.setInt(1, questionDO.getUserId());
					pst.setString(2, questionDO.getQuestion());
					pst.setDate(3, new java.sql.Date(System.currentTimeMillis()));
					pst.execute();
				} catch (Exception e) {
					log.error(e.getMessage());
				} finally {
					if (pst != null) {
						try {
							pst.close();
						} catch (SQLException se) {
							log.error(se.getMessage());
						}
					}

					if (conn != null) {
						try {
							conn.close();
						} catch (SQLException se) {
							log.error(se.getMessage());
						}
					}

				}

			});
		}

	}

	class NameValuePair {
		private String name;
		private String value;

		public NameValuePair(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return this.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return this.value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	
	
	    /**
		    * @Title: updatLoction
		    * @Description: 这里用一句话描述这个方法的作用
		    * @param @param msg
		    * @param @param user    参数
		    * @return void    返回类型
		    * @throws
		    */
	public void updatLoction(Location msg, User user) {
		UserDO userDO = userDao.get(user.getUserId());
		double longtitude = -1;
		double latitude = -1;
		try {
			longtitude = Double.parseDouble(msg.getLongtitude());
			latitude = Double.parseDouble(msg.getLatitude());
		}catch(Exception e){
			
		}finally {
			userDO.setLongitude(longtitude);
			userDO.setLatitude(latitude);
			userDao.update(userDO);
		}
		
		
	}

		public void minusGoldDouniu(int createUserId) {
			// TODO 自动生成的方法存根
			
		}
}