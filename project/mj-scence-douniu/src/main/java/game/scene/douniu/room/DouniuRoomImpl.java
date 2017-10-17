package game.scene.douniu.room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import Douniu.data.DouniuConfig;
import Douniu.data.DouniuEndResult;
import Douniu.data.DouniuUserPlace;
import game.boss.dao.entity.RoomResultDO;
import game.boss.poker.dao.dao.TbPkRoomDao;
import game.boss.poker.dao.entity.TbPkRoomDO;
import game.boss.poker.dao.entity.TbPkRoomUserDO;
import game.douniu.scene.msg.DouniuChapterEndMsg;
import game.douniu.scene.msg.DouniuChapterStartMsg;
import game.douniu.scene.msg.DouniuRecordRoomMsg;
import game.douniu.scene.msg.RoomOverMsg;
import game.scene.douniu.net.BossClient;
import game.scene.douniu.room.poker.PokerChapter;
import game.scene.douniu.services.DbService;
import game.scene.msg.RoomEndMsg;
import mj.net.message.game.Chat;
import mj.net.message.game.douniu.CompareResult;
import mj.net.message.game.douniu.DouniuBiCard;
import mj.net.message.game.douniu.DouniuChat;
import mj.net.message.game.douniu.DouniuChatRet;
import mj.net.message.game.douniu.DouniuUserOffline;
import mj.net.message.game.douniu.DouniuBiCardOtherRet;
import mj.net.message.game.douniu.DouniuBiCardRet;
import mj.net.message.game.douniu.DouniuFaPaiRet;
import mj.net.message.game.douniu.DouniuGameChapterEnd;
import mj.net.message.game.douniu.DouniuGameRoomInfo;
import mj.net.message.game.douniu.DouniuGameUserInfo;
import mj.net.message.game.douniu.DouniuOutRet;
import mj.net.message.game.douniu.DouniuShow;
import mj.net.message.game.douniu.DouniuShu;
import mj.net.message.game.douniu.DouniuStartGameRet;
import mj.net.message.game.douniu.DouniuVoteDelSelect;
import mj.net.message.game.douniu.DouniuVoteDelSelectRet;
import mj.net.message.game.douniu.DouniuVoteDelStart;
import mj.net.message.game.douniu.StaticsResultRet;
import mj.net.message.game.douniu.DouniuGameExitUser;

/**
 * @author zuoge85@gmail.com on 16/10/18.
 */
public class DouniuRoomImpl extends DouniuRoom {
	private long prevTime = 0;
	private long startTime = 0;

	@Autowired
	private DbService dbService;
	@Autowired
	private BossClient bossClient;

	@Autowired
	private TbPkRoomDao roomResultDao;

	private ArrayList<DouniuUserPlace> douniuUserPlaces;

	public DouniuRoomImpl(RoomAsyncService roomAsyncService, DouniuConfig config) {
		super(roomAsyncService);
		this.config = config;
	}

	@Override
	protected void frame() {
		long now = System.currentTimeMillis();
		if (prevTime == 0) {
			prevTime = now;
		}
		// log.info("frame:{}", now - prevTime);
		prevTime = now;
	}

	/**
	 * 
	 * @Title: join
	 * @Description: 服务器映射同步加入房间
	 * @param @param joinSceneUser
	 * @param @param sceneUsers
	 * @param @param callback 参数
	 * @return void 返回类型
	 * @throws
	 */
	public void join(DouniuSceneUser joinSceneUser,
			List<DouniuSceneUser> sceneUsers, Consumer<Boolean> callback) {
		run(() -> {
			if (isEnd()) {
				callback.accept(false);
			} else {
				for (DouniuSceneUser sceneUser : sceneUsers) {
					roomInfo.updateUserInfo(sceneUser);
				}
				// 更新 网管和session
				DouniuSceneUser sceneUser = roomInfo.getUserInfo(joinSceneUser
						.getLocationIndex());

				sceneUser.setOnline(true);
				sceneUser.setGatewayId(joinSceneUser.getGatewayId());
				sceneUser.setSessionId(joinSceneUser.getSessionId());

				callback.accept(true);
			}
		});
	}

	@Override
	public void start() {
		super.start();
		startTime = System.currentTimeMillis();
	}

	/**
	 * 用户退出房间
	 * 
	 * @param userId
	 * @param callback
	 */
	public void exitRoom(int userId, Consumer<Boolean> callback) {
		run(() -> {
			if (roomInfo.isChapterStart()) {
				callback.accept(false);
			} else {
				callback.accept(true);
				if (roomInfo.removeUserInfo(userId)) {
					sendMessage(new DouniuGameExitUser(userId));
				}
			}
		});
	}

	public void offline(int userId) {
		run(() -> {
			DouniuSceneUser user = getRoomInfo().getUserInfoByUserId(userId);
			user.setOnline(false);
			user.setJoinGame(false);
			sendMessage(new DouniuUserOffline(user.getLocationIndex()), user);
			/**
			 * 如果用户掉线了，就开启托管
			 */
			// MajiangChapter chapter = roomInfo.getChapter();
			// for (int i = 0; i < chapter.getUserPlaces().length; i++) {
			// if(chapter.getUserPlaces()[i].getLocationIndex()==user.getLocationIndex()){
			// chapter.getUserPlaces()[i].setOffLine(true);
			// }
			// }

		});
	}

	public void delRoom(int userId, Consumer<Boolean> callback) {
		run(() -> {
			if (roomInfo.isChapterStart()) {
				callback.accept(false);
			} else {
				callback.accept(true);
			}
		});
	}

	/**
	 * 玩家进入游戏
	 */
	public void joinGame(DouniuSceneUser sceneUser) {

		checkThread();
		sceneUser.setJoinGame(true);

		PokerChapter chapter = roomInfo.getChapter();
		if (sceneUser.isJoinGame()) {
			System.out.println("加入用户的次数：：：：" + chapter.getJoinRoomCount());
			chapter.addJoinRoomCount();
		}
		for (int i = 0; i < chapter.getUserPlaces().length; i++) {
			if (chapter.getUserPlaces()[i].getLocationIndex() == sceneUser
					.getLocationIndex()) {
				chapter.getUserPlaces()[i].setOffLine(false);
				chapter.getUserPlaces()[i].setUserStatus(0); // 设置玩家状态，加入游戏未开始状态
			}
		}
		// 同步游戏信息到客户端
		DouniuGameRoomInfo gameInfo = roomInfo.toMessage(sceneUser
				.getLocationIndex());
		DouniuConfig config = sceneUser.getRoom().getConfig();
		int chapterMax = Integer.parseInt(config
				.getString(DouniuConfig.CHAPTER_MAX));
		System.out.println("最大局数：：" + chapterMax);
		gameInfo.setChapterMax(chapterMax); // 游戏局数
		gameInfo.setInitScore(DouniuConfig.CHUSHIFEN); // 初始化分
		gameInfo.setMoShi(config.getBoolean(DouniuConfig.MOSHI)); // 庄家模式
		gameInfo.setNiuNum(config.getInt(DouniuConfig.NIUNIUNUM)); // 牛牛倍数
		// gameInfo.setIsType(config.getString(DouniuConfig.TYPE)); // 玩法类型
		gameInfo.setUserNum(DouniuConfig.USERNUM); // 玩家数量
		// gameInfo.setLeftChapterNums(leftChapterNums);

		sceneUser.sendMessage(gameInfo);
		// 通知其他玩家
		DouniuGameUserInfo msg = sceneUser.toMessage();
		roomInfo.excuteDistanceKm(msg, sceneUser);
		// 计算距离
		sendMessage(msg, sceneUser);
	}

	/**
	 * 发送聊天
	 */
	public void chatSendToAllUser(DouniuChat msg, DouniuSceneUser sceneUser) {
		    checkThread();
	        DouniuChatRet msgRet = new DouniuChatRet();
	        msgRet.setChatContent(msg.getChatContent());
	        msgRet.setUserindex(sceneUser.getLocationIndex());
	        msgRet.setIndex(msg.getIndex());
	        sendMessage(msgRet, null);
	}

	/*
	 * 发牌
	 */
	public void faPaiRet(DouniuSceneUser user, DouniuFaPaiRet msg) {
		checkThread();
		// this.roomInfo.getChapter().faPaiRet(user.getLocationIndex(),msg.getOpt()
		// ,msg.getPai() );

	}

	/**
	 * 下家说话，占时用不到
	 * 
	 * @param user
	 * @param msg
	 */
	public void outRet(DouniuSceneUser user, DouniuOutRet msg) {

	}

	/**
	 * 比牌
	 */
	public void biCard(DouniuSceneUser user, DouniuBiCard msg) {
		checkThread();
		this.roomInfo.getChapter().biCard(msg.getIndex(), user);
	}

	/*
	 * 将比牌信息发送给其他玩家
	 */
	public void sendOtherRet(DouniuSceneUser user, DouniuBiCardOtherRet msg) {
	}

	/**
	 * 操作类型 下注、跟注、弃牌
	 * 
	 * @param user
	 * @param msg
	 */
	public void zhuRet(DouniuSceneUser user, DouniuShu msg) {
		// 下注、跟注、弃牌
		checkThread();
		this.roomInfo.getChapter().Operation(msg, user);
	}

	/***
	 * 返回给前段牌的类型
	 * 
	 */
	public void showRet(DouniuShow msg, DouniuSceneUser user) {

	}

	/**
	 * 开始游戏
	 * 
	 * @param user
	 */
	public void chapterStart(DouniuSceneUser user) {
		checkThread();
		user.setStart(true);
		DouniuChapterStartMsg chapterStart = new DouniuChapterStartMsg();
		chapterStart.setRoomId(roomInfo.getRoomId());

		if (user.getUserId() == roomInfo.getCreateUserId()) {
			roomInfo.getChapter().addChapterNumsCount();
		}
		int count = roomInfo.getChapter().getChapterNums();
		System.out.println("开始的时候当期的局数" + count);
		chapterStart.setNum(count);
		bossClient.writeAndFlush(chapterStart); // 开始游戏发送
		this.getRoomInfo().chapter.addReadyCount();
		if (roomInfo.isChapterStart() == true) {
			roomInfo.setChapterStart(false); // 第二轮局数开始初始化
		}
		System.out.println(this.getRoomInfo().chapter.getReadyCount()
				+ "==========");
		System.out.println(this.getRoomInfo().chapter.getJoinRoomCount()
				+ "-----------");
		if (this.getRoomInfo().chapter.getReadyCount() >= this.getRoomInfo().chapter
				.getJoinRoomCount()) {
			// roomInfo.getChapter().checkBet();
			if (roomInfo.isFull()) {
				if (!roomInfo.isChapterStart()) {
					roomInfo.setStart(true);
					PokerChapter chapter = roomInfo.getChapter();
					chapter.start();
					roomInfo.setChapterStart(true);
					for (DouniuSceneUser u : roomInfo.getUsers()) {
						if (u != null && u.isJoinGame()) {
							u.sendMessage(chapter.toMessage(u
									.getLocationIndex()));
						}
					}
				} /*
				 * else { user.sendMessage(new DouniuStartGameRet());
				 * log.error("已经开始了！user:{},room:{}", user.getUserId(),
				 * roomInfo); }
				 */
			} else {
				user.sendMessage(new DouniuStartGameRet());
				user.noticeError("room.notEnoughUser");
			}
		} else {
			log.error("非创建者不能开始！user:{},room:{}", user.getUserId(), roomInfo);
			user.sendMessage(new DouniuStartGameRet());
		}
	}

	/**
	 * 将统计消息发送给所有的用户。
	 */
	public void sendStaticsResultToAllUser(TbPkRoomUserDO result) {/*
																	 * checkThread(
																	 * ); int
																	 * roomId =
																	 * getRoomInfo
																	 * (
																	 * ).getRoomId
																	 * (); List<
																	 * TbPkRoomDO
																	 * >
																	 * roomResultDOs
																	 * = (List<
																	 * TbPkRoomDO
																	 * >)
																	 * roomResultDao
																	 * .
																	 * get(roomId
																	 * ); //
																	 * 对数据进行封装。
																	 * if (
																	 * roomResultDOs
																	 * .size() <
																	 * this
																	 * .config
																	 * .getInt
																	 * (DouniuConfig
																	 * .
																	 * CHAPTER_MAX
																	 * )) {
																	 * roomResultDOs
																	 * .addAll((
																	 * Collection
																	 * <?
																	 * extends
																	 * TbPkRoomDO
																	 * >)
																	 * result);
																	 * }
																	 * StaticsResultRet
																	 * staticsResultRet
																	 * = new
																	 * StaticsResultRet
																	 * ();
																	 * staticsResultRet
																	 * .
																	 * setLocationIndex0
																	 * (0);
																	 * staticsResultRet
																	 * .
																	 * setLocationIndex1
																	 * (1);
																	 * staticsResultRet
																	 * .
																	 * setLocationIndex2
																	 * (2);
																	 * staticsResultRet
																	 * .
																	 * setLocationIndex3
																	 * (3);
																	 * staticsResultRet
																	 * .
																	 * setLocationIndex4
																	 * (4);
																	 * staticsResultRet
																	 * .
																	 * setLocationIndex5
																	 * (5); for
																	 * (
																	 * TbPkRoomDO
																	 * roomResultDO
																	 * :
																	 * roomResultDOs
																	 * ) {
																	 * 
																	 * int
																	 * winIndex
																	 * =
																	 * roomResultDO
																	 * .getId();
																	 * 
																	 * switch
																	 * (winIndex
																	 * ) { case
																	 * 0:
																	 * staticsResultRet
																	 * .
																	 * setNiunin0
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin0
																	 * () + 1);
																	 * break;
																	 * case 1:
																	 * staticsResultRet
																	 * .
																	 * setNiunin1
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin1
																	 * () + 1);
																	 * break;
																	 * case 2:
																	 * staticsResultRet
																	 * .
																	 * setNiunin2
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin2
																	 * () + 1);
																	 * break;
																	 * case 3:
																	 * staticsResultRet
																	 * .
																	 * setNiunin3
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin3
																	 * () + 1);
																	 * break;
																	 * case 4:
																	 * staticsResultRet
																	 * .
																	 * setNiunin4
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin4
																	 * () + 1);
																	 * break;
																	 * case 5:
																	 * staticsResultRet
																	 * .
																	 * setNiunin5
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin5
																	 * () + 1);
																	 * break;
																	 * default:
																	 * break; }
																	 * 
																	 * // List<
																	 * TbPkRoomUserDO
																	 * >
																	 * userPaiInfos
																	 * =
																	 * roomResultDO
																	 * .
																	 * getRoomUsers
																	 * (); for (
																	 * TbPkRoomUserDO
																	 * userPaiInfo
																	 * :
																	 * userPaiInfos
																	 * ) {
																	 * switch
																	 * (userPaiInfo
																	 * .
																	 * getLocationIndex
																	 * ()) {
																	 * case 0:
																	 * staticsResultRet
																	 * .
																	 * setMeiniu0
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getMeiniu0
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiunin0
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin0
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiuNum0
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiuNum0
																	 * ());
																	 * 
																	 * break;
																	 * case 1:
																	 * staticsResultRet
																	 * .
																	 * setMeiniu1
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getMeiniu1
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiunin1
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin1
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiuNum1
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiuNum1
																	 * ());
																	 * break;
																	 * case 2:
																	 * staticsResultRet
																	 * .
																	 * setMeiniu2
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getMeiniu2
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiunin2
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin2
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiuNum2
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiuNum2
																	 * ());
																	 * break;
																	 * case 3:
																	 * staticsResultRet
																	 * .
																	 * setMeiniu3
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getMeiniu3
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiunin3
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin3
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiuNum3
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiuNum3
																	 * ());
																	 * break;
																	 * case 4:
																	 * staticsResultRet
																	 * .
																	 * setMeiniu4
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getMeiniu4
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiunin4
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin4
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiuNum4
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiuNum4
																	 * ());
																	 * break;
																	 * case 5:
																	 * staticsResultRet
																	 * .
																	 * setMeiniu5
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getMeiniu5
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiunin5
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiunin5
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setNiuNum5
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getNiuNum5
																	 * ());
																	 * break;
																	 * default:
																	 * break; }
																	 * 
																	 * }
																	 * staticsResultRet
																	 * .
																	 * setScore0
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getScore0
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setScore1
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getScore1
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setScore2
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getScore2
																	 * ());
																	 * staticsResultRet
																	 * .
																	 * setScore3
																	 * (
																	 * staticsResultRet
																	 * .
																	 * getScore3
																	 * ()); }
																	 * 
																	 * sendMessage(
																	 * staticsResultRet
																	 * );
																	 */
	}

	// **

	// *
	// 房间结束显示统计结果
	@Override
	public void endChapter(DouniuChapterEndMsg endResult,
			PokerChapter pokerChapter) {

		checkThread();
		roomInfo.setChapterStart(false);
		DouniuRoomInfo roomInfo = getRoomInfo();
		// 如果是第一句直接扣掉房卡

		if (roomInfo.getChapter().getChapterNums() == 1) {
			dbService.delGoldDouniu(roomInfo.getRoomId(),
					roomInfo.getCreateUserId());
		}
		endResult.setNum(roomInfo.getChapter().getChapterNums());
		System.out
				.println("玩家的当前局数：：" + roomInfo.getChapter().getChapterNums());
		endResult.setRoomId(roomInfo.getRoomId());
		bossClient.writeAndFlush(endResult);
	}

	// 房间解散
	@Override
	public void voteDelStart(DouniuVoteDelStart msg, DouniuSceneUser user) {

		checkThread();
		int onlineNum = roomInfo.getCurrentOnlineUser();   //这个是未开始的次数
		if (user.getUserId() == roomInfo.getCreateUserId() ) {
			if(onlineNum == 0||onlineNum == 1 ){
				delRoom();
			}			
		} else {
			sendMessage(new DouniuVoteDelSelect(user.getUserId(),user.getUserName()), null);
		}
	}

	// 投票结果删除房间
	@Override
	public void voteDelSelect(DouniuVoteDelSelectRet msg, DouniuSceneUser user) {
		checkThread();
		if (msg.getResult()) {
			int votedelNum = roomInfo.votedel(msg, user);
			if (votedelNum >= roomInfo.getCurrentOnlineUser() - 1) {
				delRoom();
			}
		}
	}

	@Override
	public void checkDelRoom() { // >=config.getInt(DouniuConfig.CHAPTER_MAX)
		int count = roomInfo.getChapter().getChapterNums();// 当前局数
		System.out.println("+加入房间的总局数"
				+ Integer.parseInt(config.getString(DouniuConfig.CHAPTER_MAX)));
		if (count >= Integer.parseInt(config
				.getString(DouniuConfig.CHAPTER_MAX))) { // 这个有问题获取不到最大的局数DouniuConfig.CHAPTER_MAX
			delRoom();
			count = 0; // 房间结束，初始化局数
		}
	}

	public void delRoom() {
		RoomOverMsg m = new RoomOverMsg();
		m.setRoomId(getRoomInfo().getRoomId());
		System.out.println("删除房间时，创建房间的ID：：" + getRoomInfo().getCreateUserId());
		m.setCrateUserId(getRoomInfo().getCreateUserId());
		bossClient.writeAndFlush(m);
	}

	/**
	 * 房间结束存取数据
	 * 
	 */
	public void endRoom() {
		PokerChapter chapter = roomInfo.getChapter();
		DouniuRecordRoomMsg msg = chapter.roomBalance();
		msg.setRoomId(roomInfo.getRoomId());
		bossClient.writeAndFlush(msg);
		// sendToAllUser(chapter.getRoomBalanceMsg());

	}

}
