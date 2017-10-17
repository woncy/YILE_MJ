package game.scene.pdk.room;

import java.util.List;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;

import game.boss.SceneUserInfo;
import game.boss.dao.entity.RoomResultDO;
import game.scene.pdk.exception.ChuPaiException;
import game.scene.pdk.net.BossClient;
import game.scene.pdk.room.poker.PokerChapter;
import game.scene.pdk.services.DbService;
import mj.data.ChapterEndResult;
import mj.data.PdkConfig;
import mj.net.message.game.Chat;
import mj.net.message.game.OperationCPGHRet;
import mj.net.message.game.OperationDingPao;
import mj.net.message.game.OperationFaPaiRet;
import mj.net.message.game.OperationOutRet;
import mj.net.message.game.UserOffline;
import mj.net.message.game.pdk.PdkGameRoomInfo;
import mj.net.message.game.pdk.PdkGameUserInfo;

/**
 * @author zuoge85@gmail.com on 16/10/18.
 */
public class PdkRoomImpi extends PdkRoom {
    private long prevTime = 0;
    private long startTime = 0;
    

    @Autowired
    private DbService dbService;
    @Autowired
    private BossClient bossClient;
    
   


    public PdkRoomImpi(RoomAsyncService roomAsyncService, PdkConfig config) {
        super(roomAsyncService);
        this.config = config;
    }

    @Override
    protected void frame() {
        long now = System.currentTimeMillis();
        if (prevTime == 0) {
            prevTime = now;
        }
//      log.info("frame:{}", now - prevTime);
        prevTime = now;
    }

    /**
     * 
        * @Title: join
        * @Description: 服务器映射同步加入房间
        * @param @param joinSceneUser
        * @param @param sceneUsers
        * @param @param callback    参数
        * @return void    返回类型
        * @throws
     */
    public void join(PdkSceneUser joinSceneUser, List<PdkSceneUser> sceneUsers, Consumer<Boolean> callback) {
        run(() -> {
            if (isEnd()) {
                callback.accept(false);
            } else {
                for (PdkSceneUser sceneUser : sceneUsers) {
                    roomInfo.updateUserInfo(sceneUser);
                }
                //更新 网管和session
                PdkSceneUser sceneUser = roomInfo.getUserInfo(joinSceneUser.getLocationIndex());
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

    public void exitRoom(int userId, Consumer<Boolean> callback) {
//        run(() -> {
//            if (roomInfo.isChapterStart()) {
//                callback.accept(false);
//            } else {
//                callback.accept(true);
//                if (roomInfo.removeUserInfo(userId)) {
//                    sendMessage(new GameExitUser(userId));
//                }
//            }
//        });
    }

    public void offline(int userId) {
        run(() -> {
            PdkSceneUser user = getRoomInfo().getUserInfoByUserId(userId);
            user.setOnline(false);
            user.setJoinGame(false);
            sendMessage(new UserOffline(user.getLocationIndex()), user);
            /**
             * 如果用户掉线了，就开启托管
             */
//            MajiangChapter chapter = roomInfo.getChapter();
//            for (int i = 0; i < chapter.getUserPlaces().length; i++) {
//				if(chapter.getUserPlaces()[i].getLocationIndex()==user.getLocationIndex()){
//					chapter.getUserPlaces()[i].setOffLine(true);
//				}
//			}
            
          
        });
    }

    public void delRoom(int userId, Consumer<Boolean> callback) {
//        run(() -> {
////            if (roomInfo.isChapterStart()) {
////                callback.accept(false);
////            } else {
//                callback.accept(true);
////            }
//        });
    }

    /**
     * 玩家进入游戏
     */
    public void joinGame(PdkSceneUser sceneUser) {
    	
    	checkThread();
    	sceneUser.setJoinGame(true);
    	PokerChapter chapter = roomInfo.getChapter();
    	
    	PdkGameRoomInfo gameRoomInfo = new PdkGameRoomInfo();
    	gameRoomInfo.setConfig(getConfig());
    	gameRoomInfo.setCurrentChapterNum(roomInfo.getCurrentChapterNum());
    	gameRoomInfo.setGameStart(roomInfo.isStart());
    	gameRoomInfo.setMaxChapterNum(roomInfo.getUsers().length);
    	gameRoomInfo.setRoomNO(roomInfo.getRoomCheckId());
    	gameRoomInfo.setUsers(roomInfo.getUserInfosMsg());
    	gameRoomInfo.setChapterInfo(chapter.toMessage(sceneUser));
//    	gameRoomInfo.setCreate_user_id(roomInfo.getCreate_user_id());
    	sceneUser.sendMessage(gameRoomInfo);
    	PdkGameUserInfo userInfo = sceneUser.toMessage();//同步房间内信息
    	sendMessage(userInfo,sceneUser);//给其余的人发送自己的信息
    	
    	
        
    }
    
    /**
     * 发送聊天
     */
    public void chatSendToAllUser(Chat msg, PdkSceneUser sceneUser) {
    }

    public void chuPai(PdkSceneUser sceneUser,List<Integer> paiIndexs){
    	try {
			roomInfo.getChapter().chuPai(sceneUser.getLocationIndex(), paiIndexs);
		} catch (ChuPaiException e) {
//			sceneUser.sendMessage(msg);
		}
    }
 
    /**
     * 
        * @Title: chapterStart
        * @Description:  开始游戏
        * @param @param user    参数
        * @return void    返回类型
        * @throws
     */
	public void chapterStart(PdkSceneUser user) {
		checkThread();
		if(user.getUserId() == roomInfo.getCreate_user_id()){
			PokerChapter chapter = this.getRoomInfo().getChapter();
			chapter.start();
			sendMessage(chapter.toMessage(user));
		}else {
			log.info("非创建者不能开始游戏");
		}
		
    }

	@Override
	public void endChapter(ChapterEndResult endResult, PokerChapter pokerChapter) {
		
	}
}
