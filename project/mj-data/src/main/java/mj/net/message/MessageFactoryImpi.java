package mj.net.message;

import net.sf.cglib.core.Constants;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

import org.springframework.stereotype.Component;

import com.isnowfox.core.net.message.AbstractMessageFactory;

import mj.net.message.game.pdk.PdkGameChapterInfo;
import mj.net.message.game.pdk.PdkGameRoomInfo;
import mj.net.message.game.pdk.PdkGameUserInfo;
import mj.net.message.game.pdk.PdkStartGame;
import mj.net.message.game.pdk.PdkUserPlaceMsg;
import mj.net.message.login.douniu.CreateDouniuRoom;
import mj.net.message.room.pdk.CreatePdkRoom;
import mj.net.message.room.pdk.CreatePdkRoomRet;
import mj.net.message.room.pdk.JoinPdkRoom;
import mj.net.message.room.pdk.JoinPdkRoomReady;
import mj.net.message.room.pdk.JoinPdkRoomReadyDone;
import mj.net.message.room.pdk.JoinPdkRoomRet;

/**
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * 
 * @author isnowfox消息生成器
 */
@Component
public final class MessageFactoryImpi extends AbstractMessageFactory {

	public static final int TYPE_GAME = 1;
	public static final int TYPE_LOGIN = 7;

	private static final MessageFactoryImpi instance = new MessageFactoryImpi();

	public static final MessageFactoryImpi getInstance() {
		return instance;
	}

	private MessageFactoryImpi() {
	}

	@Override
	protected FastConstructor[][] init() {
		FastConstructor[][] array = new FastConstructor[8][];
		initPdkMessage(array);
		initZJHMessage(array);
		initDouniuMessage(array);
		array[1] = new FastConstructor[40];
		array[1][0] = FastClass.create(mj.net.message.game.GameChapterEnd.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][1] = FastClass.create(mj.net.message.game.GameChapterStart.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][2] = FastClass.create(mj.net.message.game.GameChapterStartRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][3] = FastClass.create(mj.net.message.game.GameDelRoom.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][4] = FastClass.create(mj.net.message.game.GameExitUser.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][5] = FastClass.create(mj.net.message.game.GameFanResult.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][6] = FastClass.create(mj.net.message.game.GameJoinRoom.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][7] = FastClass.create(mj.net.message.game.GameRoomInfo.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][8] = FastClass.create(mj.net.message.game.GameUserInfo.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][9] = FastClass.create(mj.net.message.game.MajiangChapterMsg.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][10] = FastClass.create(mj.net.message.game.OperationCPGH.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][11] = FastClass.create(mj.net.message.game.OperationCPGHRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][12] = FastClass.create(mj.net.message.game.OperationFaPai.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][13] = FastClass.create(mj.net.message.game.OperationFaPaiRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][14] = FastClass.create(mj.net.message.game.OperationOut.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][15] = FastClass.create(mj.net.message.game.OperationOutRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][16] = FastClass.create(mj.net.message.game.SyncOpt.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][17] = FastClass.create(mj.net.message.game.SyncOptTime.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][18] = FastClass.create(mj.net.message.game.TingPai.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][19] = FastClass.create(mj.net.message.game.UserOffline.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][20] = FastClass.create(mj.net.message.game.UserPlaceMsg.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][21] = FastClass.create(mj.net.message.game.VoteDelSelect.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][22] = FastClass.create(mj.net.message.game.VoteDelSelectRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][23] = FastClass.create(mj.net.message.game.VoteDelStart.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][24] = FastClass.create(mj.net.message.game.Chat.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][25] = FastClass.create(mj.net.message.game.ChatRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][26] = FastClass.create(mj.net.message.game.StaticsResultRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][28] = FastClass.create(mj.net.message.game.OperationDingPao.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][29] = FastClass.create(mj.net.message.game.OperationDingPaoRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][30] = FastClass.create(mj.net.message.game.ShowPaoRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][35] = FastClass.create(mj.net.message.login.Position.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[1][36] = FastClass.create(mj.net.message.game.Ready.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		
		
		
		array[7] = new FastConstructor[50];
		array[7][0] = FastClass.create(mj.net.message.login.CreateRoom.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][1] = FastClass.create(mj.net.message.login.CreateRoomRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][2] = FastClass.create(mj.net.message.login.DelRoom.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][3] = FastClass.create(mj.net.message.login.DelRoomRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][4] = FastClass.create(mj.net.message.login.ExitRoom.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][5] = FastClass.create(mj.net.message.login.ExitRoomRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][6] = FastClass.create(mj.net.message.login.JoinRoom.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][7] = FastClass.create(mj.net.message.login.JoinRoomRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][8] = FastClass.create(mj.net.message.login.Login.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][9] = FastClass.create(mj.net.message.login.LoginError.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][10] = FastClass.create(mj.net.message.login.LoginRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][11] = FastClass.create(mj.net.message.login.Notice.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][12] = FastClass.create(mj.net.message.login.OptionEntry.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][13] = FastClass.create(mj.net.message.login.Pay.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][14] = FastClass.create(mj.net.message.login.Ping.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][15] = FastClass.create(mj.net.message.login.Pong.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][16] = FastClass.create(mj.net.message.login.Radio.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][17] = FastClass.create(mj.net.message.login.RepeatLoginRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][18] = FastClass.create(mj.net.message.login.RoomHistory.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][19] = FastClass.create(mj.net.message.login.RoomHistoryList.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][20] = FastClass.create(mj.net.message.login.RoomHistoryListRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][21] = FastClass.create(mj.net.message.login.SendAuthCode.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][22] = FastClass.create(mj.net.message.login.SendAuthCodeRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][23] = FastClass.create(mj.net.message.login.StartGame.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][24] = FastClass.create(mj.net.message.login.SysSetting.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][25] = FastClass.create(mj.net.message.login.Transfer.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][26] = FastClass.create(mj.net.message.login.TransferRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][27] = FastClass.create(mj.net.message.login.WXPay.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][28] = FastClass.create(mj.net.message.login.WXPayRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][29] = FastClass.create(mj.net.message.login.WXPaySuccess.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][30] = FastClass.create(mj.net.message.login.WXPayQuery.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][31] = FastClass.create(mj.net.message.login.AliPayRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][32] = FastClass.create(mj.net.message.login.WXShow.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][33] = FastClass.create(mj.net.message.login.JoinClub.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][34] = FastClass.create(mj.net.message.login.JoinClubRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][35] = FastClass.create(mj.net.message.login.Record.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][36] = FastClass.create(mj.net.message.login.ClubInfo.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][37] = FastClass.create(mj.net.message.login.Question.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][38] = FastClass.create(mj.net.message.login.WangbangPlayBack.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][39] = FastClass.create(mj.net.message.login.WangbangPlayBackRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
//		array[7][40] = FastClass.create(mj.net.message.login.SubmitIDCard.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][40] = FastClass.create(mj.net.message.login.Location.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][41] = FastClass.create(mj.net.message.login.ProxyRoom.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][42] = FastClass.create(mj.net.message.login.QueryProxyRoomList.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[7][43] = FastClass.create(mj.net.message.login.ProxyRoomList.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		return array;
	}

	private void initZJHMessage(FastConstructor[][] array) {
		array[3] = new FastConstructor[50];
		// 创建房间
		array[3][0] = FastClass.create(mj.net.message.login.zjh.CreateZJHRoom.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 创建房间结果
		array[3][1] = FastClass.create(mj.net.message.login.zjh.CreateZJHRoomResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 加入房间
		array[3][3] = FastClass.create(mj.net.message.login.zjh.JoinZJHRoom.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 加入房间结果
		array[3][4] = FastClass.create(mj.net.message.login.zjh.JoinZJHRoomResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 准备就绪
		array[3][5] = FastClass.create(mj.net.message.login.zjh.GameReadySatrt.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 房间信息
		array[3][6] = FastClass.create(mj.net.message.game.zjh.GameRoomInfoT.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 开始游戏
		array[3][7] = FastClass.create(mj.net.message.game.zjh.ZJHGameSatrt.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 返回发票消息
		array[3][8] = FastClass.create(mj.net.message.game.zjh.FaInfo.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 房间分数信息
		array[3][9] = FastClass.create(mj.net.message.game.zjh.RoomInfoPoker.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 操作 看、qi、跟
		array[3][10] = FastClass.create(mj.net.message.game.zjh.ZJHOperation.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 看牌结果
		array[3][11] = FastClass.create(mj.net.message.game.zjh.SelectPokerResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 加注
		array[3][12] = FastClass.create(mj.net.message.game.zjh.AddZhu.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 跟注结果
		array[3][13] = FastClass.create(mj.net.message.game.zjh.GenZhuResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 扣分信息
		array[3][14] = FastClass.create(mj.net.message.game.zjh.DelScoreResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 比牌
		array[3][15] = FastClass.create(mj.net.message.game.zjh.ComparePoker.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 比牌结果
		array[3][16] = FastClass.create(mj.net.message.game.zjh.ComparePokerResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 删除房间
		array[3][21] = FastClass.create(mj.net.message.login.zjh.DelZJHRoom.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 删除放间结果
		array[3][22] = FastClass.create(mj.net.message.login.zjh.DelZJHRoomResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 发起投票
		array[3][24] = FastClass.create(mj.net.message.game.zjh.VoteStart.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 投票结果
		array[3][26] = FastClass.create(mj.net.message.game.zjh.VoteDelZJHSelectResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 显示发起投票玩家信息
		array[3][25] = FastClass.create(mj.net.message.game.zjh.VoteDelZJHSelect.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 查询总战绩
		array[3][27] = FastClass.create(mj.net.message.game.zjh.ZJHHistory.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 房间战绩
		array[3][28] = FastClass.create(mj.net.message.game.zjh.ZJHRoomHistory.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 所有房间战绩列表
		array[3][29] = FastClass.create(mj.net.message.game.zjh.ZJHRoomHistoryList.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);

		// 加注结果
		array[3][30] = FastClass.create(mj.net.message.game.zjh.AddZhuResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 弃牌结果
		array[3][31] = FastClass.create(mj.net.message.game.zjh.QiPokerResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 用戶信息
		array[3][32] = FastClass.create(mj.net.message.game.zjh.GameUserinfo2.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 返回初始信息
		array[3][33] = FastClass.create(mj.net.message.game.zjh.OperationNext.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 发起投票
		array[3][34] = FastClass.create(mj.net.message.game.zjh.ReadyGame.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[3][35] = FastClass.create(mj.net.message.game.zjh.ZJHChapterMsg.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 房间内战绩
		array[3][36] = FastClass.create(mj.net.message.login.zjh.SelectEveryHistory.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
	}

	private void initPdkMessage(FastConstructor[][] array) {
		array[4] = new FastConstructor[30];
		/**
		 * 创建房间【4,0】
		 */
		array[CreatePdkRoom.TYPE][CreatePdkRoom.ID] = FastClass.create(mj.net.message.room.pdk.CreatePdkRoom.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		/**
		 * 创建房间返回【4,1】
		 */
		array[CreatePdkRoomRet.TYPE][CreatePdkRoomRet.ID] = FastClass
				.create(mj.net.message.room.pdk.CreatePdkRoomRet.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		/**
		 * 以下皆为房间信息
		 */
		array[CreatePdkRoomRet.TYPE][PdkGameRoomInfo.ID] = FastClass
				.create(mj.net.message.game.pdk.PdkGameRoomInfo.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[CreatePdkRoomRet.TYPE][PdkGameUserInfo.ID] = FastClass
				.create(mj.net.message.game.pdk.PdkGameUserInfo.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[CreatePdkRoomRet.TYPE][PdkGameChapterInfo.ID] = FastClass
				.create(mj.net.message.game.pdk.PdkGameChapterInfo.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[CreatePdkRoomRet.TYPE][PdkUserPlaceMsg.ID] = FastClass
				.create(mj.net.message.game.pdk.PdkUserPlaceMsg.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		/**
		 * 加入房间【4,6】
		 */
		array[JoinPdkRoom.TYPE][JoinPdkRoom.ID] = FastClass.create(mj.net.message.room.pdk.JoinPdkRoom.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		/**
		 * 加入房间返回【4,7】
		 */
		array[JoinPdkRoomRet.TYPE][JoinPdkRoomRet.ID] = FastClass.create(mj.net.message.room.pdk.JoinPdkRoomRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		/**
		 * 加入房间就绪【4,8】
		 */
		array[JoinPdkRoom.TYPE][JoinPdkRoomReady.ID] = FastClass.create(mj.net.message.room.pdk.JoinPdkRoomReady.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		/**
		 * 加入房间就绪【4,9】
		 */
		array[JoinPdkRoomReadyDone.TYPE][JoinPdkRoomReadyDone.ID] = FastClass
				.create(mj.net.message.room.pdk.JoinPdkRoomReadyDone.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		/**
		 * 开始游戏【4,10】
		 */
		array[PdkStartGame.TYPE][PdkStartGame.ID] = FastClass.create(mj.net.message.game.pdk.PdkStartGame.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		/**
		 * 出牌【4,12】
		 */
		// array[PdkStartGame.TYPE][PdkStartGame.ID] =
		// FastClass.create(mj.net.message.game.pdk.PdkStartGame.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);

	}

	@SuppressWarnings("unused")
	private void initDouniuMessage(FastConstructor[][] array) {
		array[5] = new FastConstructor[52];
		// 创建房间
		array[5][0] = FastClass.create(CreateDouniuRoom.class).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 创建房间结果
		array[5][1] = FastClass.create(mj.net.message.login.douniu.CreateDouniuRoomResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 加入房间
		array[5][2] = FastClass.create(mj.net.message.login.douniu.JoinDouniuRoom.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 加入房间结果
		array[5][3] = FastClass.create(mj.net.message.login.douniu.JoinDouniuRoomResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 加入房间准备
		array[5][4] = FastClass.create(mj.net.message.login.douniu.JoinDouniuRoomReady.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 加入房间准备结果
		array[5][5] = FastClass.create(mj.net.message.login.douniu.JoinDouniuRoomReadyDone.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 退出房间
		array[5][6] = FastClass.create(mj.net.message.login.douniu.ExitDouniuRoom.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 退出房间结果
		array[5][7] = FastClass.create(mj.net.message.login.douniu.ExitDouniuRoomResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 斗牛开始游戏消息
		
		array[5][8] = FastClass.create(mj.net.message.game.douniu.DouniuStartGame.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 斗牛开始游戏消息结果
		array[5][9] = FastClass.create(mj.net.message.game.douniu.DouniuStartGameRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 发牌
		array[5][10] = FastClass.create(mj.net.message.game.douniu.DouniuFaPai.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 发牌 结果
		array[5][11] = FastClass.create(mj.net.message.game.douniu.DouniuFaPaiRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 下注
		array[5][12] = FastClass.create(mj.net.message.game.douniu.DouniuShu.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 下注 结果
		array[5][13] = FastClass.create(mj.net.message.game.douniu.DouniuZhuRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 斗牛发送服务器下注的倍数
				array[5][26] = FastClass.create(mj.net.message.game.douniu.DouniuSendMsgShu.class)
						.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 比牌
		array[5][14] = FastClass.create(mj.net.message.game.douniu.DouniuBiCard.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 比牌 结果
		array[5][15] = FastClass.create(mj.net.message.game.douniu.DouniuBiCardRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 比牌渲染
		array[5][16] = FastClass.create(mj.net.message.game.douniu.DouniuBiCardOther.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 比牌渲染 结果
		array[5][17] = FastClass.create(mj.net.message.game.douniu.DouniuBiCardOtherRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 下家说话
		array[5][18] = FastClass.create(mj.net.message.game.douniu.DouniuOut.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 下家说话回复
		array[5][19] = FastClass.create(mj.net.message.game.douniu.DouniuOutRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 一局斗牛的信息
		array[5][20] = FastClass.create(mj.net.message.game.douniu.DouniuChapterMsg.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 斗牛扣分
		array[5][21] = FastClass.create(mj.net.message.game.douniu.DouniuDelScoreResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 斗牛牌局结束
		array[5][22] = FastClass.create(mj.net.message.game.douniu.DouniuGameChapterEnd.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 用户退出游戏
		array[5][23] = FastClass.create(mj.net.message.game.douniu.DouniuGameExitUser.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 斗牛创建房间同步消息
		array[5][24] = FastClass.create(mj.net.message.game.douniu.DouniuGameRoomInfo.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 斗牛同步玩家游戏消息
		array[5][25] = FastClass.create(mj.net.message.game.douniu.DouniuGameUserInfo.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		
		// 一局用户牌的信息
		array[5][27] = FastClass.create(mj.net.message.game.douniu.DouniuUserPlaceMsg.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		// 准备就绪,通知服务器可以开始发送房间信息了
		array[5][28] = FastClass.create(mj.net.message.game.douniu.GameJoinDouniuRoom.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		
		array[5][29] = FastClass.create(mj.net.message.game.douniu.ReadyGame.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][30] = FastClass.create(mj.net.message.game.douniu.StaticsResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][31] = FastClass.create(mj.net.message.game.douniu.StaticsResultRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][32] = FastClass.create(mj.net.message.game.douniu.SyncDouniuTime.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][33]=FastClass.create(mj.net.message.game.douniu.DouniuOnePai.class)
					.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][34] = FastClass.create(mj.net.message.game.douniu.ShowQingZhuangRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][35] = FastClass.create(mj.net.message.game.douniu.QiangZhuangRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][36] = FastClass.create(mj.net.message.game.douniu.CompareResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][37] = FastClass.create(mj.net.message.game.douniu.DouniuShow.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][38] = FastClass.create(mj.net.message.game.douniu.DouniuShowRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][39] = FastClass.create(mj.net.message.game.douniu.JingJiResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		
		array[5][40] = FastClass.create(mj.net.message.game.douniu.DouniuGameDelRoom.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		//开始解散房间
		array[5][41] = FastClass.create(mj.net.message.game.douniu.DouniuVoteDelStart.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		//解散房间查询
		array[5][42] = FastClass.create(mj.net.message.game.douniu.DouniuVoteDelSelect.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		//解散房间投票
		array[5][43] = FastClass.create(mj.net.message.game.douniu.DouniuVoteDelSelectRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][44] = FastClass.create(mj.net.message.game.douniu.DouniuUserOffline.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		//删除房间
		array[5][45] = FastClass.create(mj.net.message.login.douniu.DeDouniuRoomResult.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][46] = FastClass.create(mj.net.message.login.douniu.DeDouniuRoom.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
	   //战绩查询
		array[5][47] = FastClass.create(mj.net.message.login.douniu.DouniuRoomHistory.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][48] = FastClass.create(mj.net.message.login.douniu.DouniuRoomHistoryList.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][49] = FastClass.create(mj.net.message.login.douniu.DouniuRoomHistoryListRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
	   //聊天
		array[5][50] = FastClass.create(mj.net.message.game.douniu.DouniuChat.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
		array[5][51] = FastClass.create(mj.net.message.game.douniu.DouniuChatRet.class)
				.getConstructor(Constants.EMPTY_CLASS_ARRAY);
	}

}
