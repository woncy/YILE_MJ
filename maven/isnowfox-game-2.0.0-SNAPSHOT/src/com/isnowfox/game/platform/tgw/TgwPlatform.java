// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   TgwPlatform.java

package com.isnowfox.game.platform.tgw;

import com.isnowfox.core.net.message.MessageProtocol;
import com.isnowfox.game.platform.*;
import com.isnowfox.util.JsonUtils;
import com.isnowfox.util.ObjectUtils;
import com.qq.open.OpenApiV3;
import com.qq.open.OpensnsException;
import gnu.trove.map.hash.TIntIntHashMap;
import io.netty.buffer.ByteBuf;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;

public class TgwPlatform extends PlatformAdapter {

	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/game/platform/tgw/TgwPlatform);
	public static final String HEAD = "tgw_l7_forward";
	public static final String MASK = "\r\n";
	private String key;
	private boolean check;
	private String apiAppKey;
	private String apiAppId;
	private String apiUrl;
	private String apiPayZoneid;
	private GamePayResult gamePayResult;
	public ConcurrentHashMap loginInfo;
	private ScheduledExecutorService asyncExecutor;
	private static final TIntIntHashMap rmbMap = new TIntIntHashMap(new int[] {
		5, 10, 50, 100, 500, 1000
	}, new int[] {
		50, 105, 550, 1150, 6000, 12500
	});

	public TgwPlatform() {
		loginInfo = new ConcurrentHashMap();
	}

	public boolean onIn(ByteBuf in, User user) throws Exception {
		if (user.isCheckConnect()) {
			return true;
		}
		int readerIndex = in.readerIndex();
		int readableLen = in.readableBytes();
		if (readableLen >= 4) {
			byte data[] = new byte[readableLen];
			in.readBytes(data);
			if (data[0] == 116 && data[1] == 103 && data[2] == 119) {
				String str = new String(data, "ascii");
				int index = 0;
				for (int i = 0; i < 3; i++) {
					index = str.indexOf("\r\n", index + "\r\n".length());
					if (index == -1) {
						in.readerIndex(readerIndex);
						return false;
					}
					if (i == 2) {
						in.readerIndex(readerIndex + index + "\r\n".length());
						user.setCheckConnect(true);
						return true;
					}
				}

			} else {
				user.setCheckConnect(true);
			}
		}
		in.readerIndex(readerIndex);
		return false;
	}

	public UserInfo login(String info) {
		Map map;
		String openid;
		String yellow;
		String year;
		String sign;
		map = (Map)JsonUtils.deserialize(info, java/util/HashMap);
		openid = String.valueOf(map.get("openid"));
		yellow = String.valueOf(map.get("yellow"));
		year = String.valueOf(map.get("year"));
		sign = String.valueOf(map.get("sign"));
		String curSign;
		if (!check) {
			break MISSING_BLOCK_LABEL_145;
		}
		curSign = DigestUtils.md5DigestAsHex(openid.getBytes("ascii"));
		curSign = DigestUtils.md5DigestAsHex((new StringBuilder()).append(curSign).append(key).append(year).append(yellow).toString().getBytes("ascii"));
		curSign = DigestUtils.md5DigestAsHex(curSign.getBytes("ascii"));
		if (!sign.equals(curSign)) {
			return null;
		}
		UserInfo userInfo;
		userInfo = new UserInfo();
		userInfo.setParam(map);
		userInfo.setUuid(openid);
		userInfo.setYear(year.equals("1"));
		userInfo.setYellow(NumberUtils.toInt(yellow, 0));
		loginInfo.put(openid, new ConcurrentHashMap(map));
		return userInfo;
		UnsupportedEncodingException e;
		e;
		throw new RuntimeException();
	}

	public void logout(String openId) {
		loginInfo.remove(openId);
	}

	public void pay(final int rmb, final String openId, final ApiCallback callback) {
		getAsyncExecutor().submit(new Runnable() {

			final String val$openId;
			final ApiCallback val$callback;
			final int val$rmb;
			final TgwPlatform this$0;

			public void run() {
				Map objectMap = (Map)loginInfo.get(openId);
				if (objectMap == null) {
					TgwPlatform.log.error("不存在玩家信息!");
					callback.callback(false, null);
					return;
				}
				if (!TgwPlatform.rmbMap.containsKey(rmb)) {
					TgwPlatform.log.error("错误的套餐!");
					callback.callback(false, null);
					return;
				}
				int gold = TgwPlatform.rmbMap.get(rmb);
				OpenApiV3 api = new OpenApiV3(apiAppId, apiAppKey);
				api.setServerName(apiUrl);
				String scriptName = "/v3/pay/buy_goods";
				String protocol = "https";
				HashMap params = new HashMap();
				params.put("openid", openId);
				params.put("openkey", String.valueOf(objectMap.get("openkey")));
				params.put("pf", String.valueOf(objectMap.get("pf")));
				params.put("pfkey", String.valueOf(objectMap.get("pfkey")));
				params.put("amt", (new StringBuilder()).append(rmb * 10).append("*1").toString());
				params.put("ts", String.valueOf(System.currentTimeMillis() / 1000L));
				params.put("payitem", (new StringBuilder()).append("").append(rmb).append("*").append(rmb * 10).append("*1").toString());
				params.put("appmode", "1");
				params.put("goodsmeta", (new StringBuilder()).append(gold).append("金币宝箱*").append(gold).append("金币宝箱！").toString());
				params.put("goodsurl", (new StringBuilder()).append("http://app1104202590.imgcache.qzoneapp.com/app1104202590/img/pay").append(rmb).append(".png").toString());
				params.put("zoneid", apiPayZoneid);
				try {
					String resp = api.api(scriptName, params, protocol);
					Map map = (Map)JsonUtils.deserialize(resp, java/util/HashMap);
					if (0 == ObjectUtils.toInt(map.get("ret"))) {
						callback.callback(true, map);
					} else {
						TgwPlatform.log.error("充值错误错误:{}", resp);
						callback.callback(false, null);
					}
				}
				catch (OpensnsException e) {
					TgwPlatform.log.error("Request Failed.msg:", e.getMessage(), e);
					callback.callback(false, null);
				}
			}

			 {
				this.this$0 = TgwPlatform.this;
				openId = s;
				callback = apicallback;
				rmb = i;
				super();
			}
		});
	}

	public static void main(String args[]) {
		System.out.println(getGold("50*50*1"));
	}

	protected static int getGold(String payitem) {
		int index = payitem.indexOf("*");
		if (index < 0) {
			return -1;
		}
		int rmb = NumberUtils.toInt(payitem.substring(0, index), -1);
		if (!rmbMap.containsKey(rmb)) {
			log.error("错误的套餐!");
			return -1;
		} else {
			return rmbMap.get(rmb);
		}
	}

	protected static int getRmb(String payitem) {
		int index = payitem.indexOf("*");
		if (index < 0) {
			return -1;
		} else {
			int rmb = NumberUtils.toInt(payitem.substring(0, index), -1);
			return rmb;
		}
	}

	public void payResult(final Map allParams) {
		log.info("不存在玩家信息!{}", allParams);
		final String openId = (String)allParams.get("openid");
		if (openId == null) {
			return;
		}
		final Map objectMap = (Map)loginInfo.get(openId);
		if (objectMap == null) {
			log.error("不存在玩家信息!");
			return;
		}
		String payitem = (String)allParams.get("payitem");
		int gold = getGold(payitem);
		if (gold == -1) {
			log.error("错误的套餐!{}", allParams);
			return;
		} else {
			int rmb = getRmb(payitem);
			gamePayResult.pay(allParams, rmb, gold, openId, new ApiCallback() {

				final Map val$allParams;
				final String val$openId;
				final Map val$objectMap;
				final TgwPlatform this$0;

				public void callback(boolean result, Map map) {
					if (!result) {
						return;
					} else {
						Runnable runnable = new Runnable() {

							private volatile int nums;
							final _cls2 this$1;

							public void run() {
								TgwPlatform.log.info("开始确认回调不存在玩家信息!{}", allParams);
								OpenApiV3 api = new OpenApiV3(apiAppId, apiAppKey);
								api.setServerName(apiUrl);
								String scriptName = "/v3/pay/confirm_delivery";
								String protocol = "https";
								HashMap params = new HashMap();
								params.put("openid", openId);
								params.put("openkey", String.valueOf(objectMap.get("openkey")));
								params.put("pf", String.valueOf(objectMap.get("pf")));
								params.put("ts", String.valueOf(System.currentTimeMillis() / 1000L));
								params.put("payitem", allParams.get("payitem"));
								params.put("token_id", allParams.get("token"));
								String billno = (String)allParams.get("billno");
								params.put("billno", billno);
								params.put("zoneid", allParams.get("zoneid"));
								params.put("providetype", allParams.get("providetype"));
								params.put("provide_errno", allParams.get("provide_errno"));
								params.put("amt", allParams.get("amt"));
								params.put("payamt_coins", allParams.get("payamt_coins"));
								params.put("pubacct_payamt_coins", allParams.get("pubacct_payamt_coins"));
								try {
									String resp = api.api(scriptName, params, protocol);
									Map map = (Map)JsonUtils.deserialize(resp, java/util/HashMap);
									TgwPlatform.log.info("参数:{}", map);
									if (0 == ObjectUtils.toInt(map.get("ret"))) {
										TgwPlatform.log.error("充值确认成功：{}", resp);
										gamePayResult.paySuccess(openId, billno);
									} else
									if (nums > 0) {
										nums--;
										TgwPlatform.log.error("充值错误错误:{}，准备重试", resp);
										getAsyncExecutor().schedule(this, 5L, TimeUnit.SECONDS);
									} else {
										TgwPlatform.log.error(" 充值失败10次:{}，不在重试", resp);
									}
								}
								catch (OpensnsException e) {
									TgwPlatform.log.error("Request Failed.msg:", e.getMessage(), e);
								}
							}

					 {
						this.this$1 = _cls2.this;
						super();
						nums = 10;
					}
						};
						getAsyncExecutor().schedule(runnable, 2L, TimeUnit.SECONDS);
						return;
					}
				}

			 {
				this.this$0 = TgwPlatform.this;
				allParams = map;
				openId = s;
				objectMap = map1;
				super();
			}
			});
			return;
		}
	}

	private ScheduledExecutorService getAsyncExecutor() {
		if (asyncExecutor != null) {
			break MISSING_BLOCK_LABEL_57;
		}
		TgwPlatform tgwplatform = this;
		JVM INSTR monitorenter ;
		if (asyncExecutor == null) {
			final Thread.UncaughtExceptionHandler exceptionHandler = new Thread.UncaughtExceptionHandler() {

				final TgwPlatform this$0;

				public void uncaughtException(Thread t, Throwable e) {
					TgwPlatform.log.error("线程池错误，会恢复！", e);
				}

			 {
				this.this$0 = TgwPlatform.this;
				super();
			}
			};
			asyncExecutor = Executors.newScheduledThreadPool(16, new ThreadFactory() {

				final Thread.UncaughtExceptionHandler val$exceptionHandler;
				final TgwPlatform this$0;

				public Thread newThread(Runnable r) {
					Thread thread = new Thread(r, "tgw AsyncThread");
					thread.setUncaughtExceptionHandler(exceptionHandler);
					return thread;
				}

			 {
				this.this$0 = TgwPlatform.this;
				exceptionHandler = uncaughtexceptionhandler;
				super();
			}
			});
		}
		return asyncExecutor;
		Exception exception;
		exception;
		throw exception;
		return asyncExecutor;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean getCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public void setApiAppId(String apiAppId) {
		this.apiAppId = apiAppId;
	}

	public void setApiAppKey(String apiAppKey) {
		this.apiAppKey = apiAppKey;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public void setApiPayZoneid(String apiPayZoneid) {
		this.apiPayZoneid = apiPayZoneid;
	}

	public void setGamePayResult(GamePayResult gamePayResult) {
		this.gamePayResult = gamePayResult;
	}

	public void close() throws InterruptedException {
		synchronized (this) {
			asyncExecutor.shutdown();
			asyncExecutor.awaitTermination(10L, TimeUnit.SECONDS);
		}
	}









}
