package mj.net.handler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import mj.net.message.game.pdk.PdkStartGame;
import mj.net.message.room.pdk.CreatePdkRoom;
import mj.net.message.room.pdk.JoinPdkRoom;
import mj.net.message.room.pdk.JoinPdkRoomReadyDone;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;


public final class MessageHandlerFactory implements ApplicationContextAware {
	private static final MessageHandlerFactory instance = new MessageHandlerFactory();
	public static final MessageHandlerFactory getInstance(){
		return instance;
	}
	
	private ApplicationContext applicationContext;
	
	private final MessageHandler<?,?>[][] array;
	
	private MessageHandlerFactory(){
		array = new MessageHandler<?,?>[8][];
	}

	@PostConstruct
    public void init(){

		array[1] = new MessageHandler[50];

		array[1][1] = getBean(mj.net.handler.game.GameChapterStartHandler.class);

		array[1][6] = getBean(mj.net.handler.game.GameJoinRoomHandler.class);

		array[1][11] = getBean(mj.net.handler.game.OperationCPGHRetHandler.class);

		array[1][13] = getBean(mj.net.handler.game.OperationFaPaiRetHandler.class);

		array[1][15] = getBean(mj.net.handler.game.OperationOutRetHandler.class);

		array[1][22] = getBean(mj.net.handler.game.VoteDelSelectRetHandler.class);

		array[1][23] = getBean(mj.net.handler.game.VoteDelStartHandler.class);
		array[1][24] = getBean(mj.net.handler.game.ChatHandler.class);
		
		array[1][28] = getBean(mj.net.handler.game.OperationDingPaoHandler.class);
		array[1][36] = getBean(mj.net.handler.game.VoiceHandler.class);
		array[1][38] = getBean(mj.net.handler.game.AudioMsgHandler.class);
		
		array[7] = new MessageHandler[50];

		array[7][0] = getBean(mj.net.handler.login.CreateRoomHandler.class);

		array[7][2] = getBean(mj.net.handler.login.DelRoomHandler.class);

		array[7][4] = getBean(mj.net.handler.login.ExitRoomHandler.class);

		array[7][6] = getBean(mj.net.handler.login.JoinRoomHandler.class);

		array[7][8] = getBean(mj.net.handler.login.LoginHandler.class);

		array[7][14] = getBean(mj.net.handler.login.PingHandler.class);

		array[7][19] = getBean(mj.net.handler.login.RoomHistoryListHandler.class);

		array[7][21] = getBean(mj.net.handler.login.SendAuthCodeHandler.class);
    
		array[7][25] = getBean(mj.net.handler.login.TransferHandler.class);
		
		array[7][27] = getBean(mj.net.handler.login.WXPayHandler.class);
		
		array[7][30] = getBean(mj.net.handler.login.WXPayQueryHandler.class);
		
		array[7][32] = getBean(mj.net.handler.login.WXShowHandler.class);
		array[7][33] = getBean(mj.net.handler.login.JoinClubHandler.class);
		array[7][37] = getBean(mj.net.handler.login.QuestionHandler.class);
		array[7][38] = getBean(mj.net.handler.login.WangbangPlayBackHandler.class);
		array[7][40] = getBean(mj.net.handler.login.LocationHandler.class);
		array[7][42] = getBean(mj.net.handler.login.QueryProxyRoomListHandler.class);
		initDouniu();
	}
	private void initZJH() {
		array[3] = new MessageHandler[50];
		//创建房间处理器【3,0】
		array[3][0] = getBean(mj.net.handler.login.zjh.CreateZJHRoomHandler.class);
		//加入房间【3，3】
		array[3][3] = getBean(mj.net.handler.login.zjh.JoinZJHRoomHandler.class);
		//准备游戏
		array[3][5] = getBean(mj.net.handler.login.zjh.GameReadySatrtHandler.class);
		//开始游戏
		array[3][6] = getBean(mj.net.handler.login.zjh.GameReadySatrtHandler.class);
		
		array[3][7] = getBean(mj.net.handler.login.zjh.ZJHGameStartHandler.class);
		//操作  看、qi、跟
		array[3][10] = getBean(mj.net.handler.login.zjh.ZJHOperationHandler.class);
		//加注
		array[3][12] = getBean(mj.net.handler.login.zjh.AddZhuHandler.class);
		//比牌
		array[3][15] = getBean(mj.net.handler.login.zjh.ComparePokerHandler.class);
		//删除房间
		array[3][21] = getBean(mj.net.handler.login.zjh.DelZJHRoomHandler.class);
		//发起投票
		array[3][24] = getBean(mj.net.handler.game.VoteDelStartHandler.class);
		//投票结果
		array[3][26] = getBean(mj.net.handler.game.zjh.VoteDelZJHSelectResultHandler.class);
		//查询总战绩
		array[3][27] = getBean(mj.net.handler.login.zjh.ZJHHistoryHandler.class);
		//房间内战绩
		array[3][36] = getBean(mj.net.handler.login.zjh.SelectEveryHistoryHandler.class);
		
		
		
	}

	private void initPdk() {
		array[4] = new MessageHandler[33];
		//创建房间处理器【4,0】
		array[CreatePdkRoom.TYPE][CreatePdkRoom.ID] = getBean(mj.net.handler.login.pdk.CreatePdkRoomHandler.class);
		// 加入房间【4，2】
		array[CreatePdkRoom.TYPE][JoinPdkRoom.ID] = getBean(mj.net.handler.login.pdk.JoinPdkRoomHandler.class);
		//准备完毕【4,9】
		array[CreatePdkRoom.TYPE][JoinPdkRoomReadyDone.ID] = getBean(mj.net.handler.game.pdk.JoinPdkRoomReadyDoneHandler.class);
		//开始游戏【4,10】
		array[PdkStartGame.TYPE][PdkStartGame.ID] = getBean(mj.net.handler.game.pdk.PdkStartGameHandler.class);
	}

	private void initDouniu() {
		array[2] = new MessageHandler[52];
		//创建房间处理器【7,0】
//		array[2][0] = getBean(mj.net.handler.login.douniu.CreateDouniuRoomHandler.class);
		//加入房間
		array[2][2] = getBean(mj.net.handler.login.douniu.JoinDouniuRoomHandler.class);
		//退出房間
//		array[2][6] = getBean(mj.net.handler.login.douniu.ExitDouniuRoomHandler.class);
		//加入房间准备
		array[2][4] = getBean(mj.net.handler.game.douniu.JoinDouniuRoomReadyDoneHandler.class);
		array[2][8] = getBean(mj.net.handler.game.douniu.DNGameStartHandler.class);
		array[2][9] = getBean(mj.net.handler.game.douniu.DNGameReadyHandler.class);
		array[2][10] = getBean(mj.net.handler.game.douniu.DNQiangZhuangHandler.class);
		array[2][12] = getBean(mj.net.handler.game.douniu.DNKaiPaiHandler.class);
		array[2][15] = getBean(mj.net.handler.game.douniu.DNXiaZhuHandler.class);
		array[2][17] = getBean(mj.net.handler.game.douniu.DNExitUserHandler.class);
		array[2][18] = getBean(mj.net.handler.game.douniu.DNdismissVoteHandler.class);
		array[2][19] = getBean(mj.net.handler.game.douniu.DNdismissUserResultHandler.class);
		//加入游戏开始发送房间信息
//		array[2][28] = getBean(mj.net.handler.game.douniu.GameJoinDouniuRoomHandler.class);
		//斗牛开始游戏
//		array[2][8] = getBean(mj.net.handler.game.douniu.DouniuStartGameHandler.class);
		//斗牛发牌
//		array[2][11] = getBean(mj.net.handler.game.douniu.DouniuFaPaiRetHandler.class);
		//斗牛下注
//		array[2][12] = getBean(mj.net.handler.game.douniu.DouniuZhuHandler.class);
		//斗牛比牌
//		array[2][15] = getBean(mj.net.handler.game.douniu.DouniuBiCardHandler.class);
		//斗牛比牌结果渲染
//		array[2][17] = getBean(mj.net.handler.game.douniu.DouniuBiCardOtherRetHandler.class);		
		//斗牛下家说话
//		array[2][19] = getBean(mj.net.handler.game.douniu.DouniuOutRetHandler.class);
		//斗牛返回给前端的牌型
//		array[2][37] = getBean(mj.net.handler.game.douniu.DouniuShowHandler.class);
		//斗牛开始解散房间
//		array[2][41] = getBean(mj.net.handler.game.douniu.DouniuVoteDelStartHandler.class);
		//斗牛开始投票
//		array[2][43] = getBean(mj.net.handler.game.douniu.DouniuVoteDelSelectRetHandler.class);
		
//		array[2][46] = getBean(mj.net.handler.login.douniu.DeDouniuRoomHandler.class);
	     
		//战绩查询
//		array[2][48] = getBean(mj.net.handler.login.douniu.DouniuRoomHistoryListHandler.class);
		
		//聊天
//		array[2][50] = getBean(mj.net.handler.game.douniu.DouniuChatHandler.class);

	}
	
	
	public MessageHandler<?,?> getHandler(int type, int id){
		return array[type][id];
	}
	@Resource
	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private <T> T getBean(Class<T> cls) {
        try {
            return applicationContext.getBean(cls);
        } catch (NoSuchBeanDefinitionException ex) {
            return null;
        }
    }
}
