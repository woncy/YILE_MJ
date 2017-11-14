package game.boss.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.game.proxy.message.PxMsg;

import game.boss.services.RoomService;
import game.douniu.scene.msg.ChapterEnd2Msg;
import game.douniu.scene.msg.ChapterStart2Msg;
import game.douniu.scene.msg.CheckExitRoom2Msg;
import game.douniu.scene.msg.DelRoom2Msg;
import game.scene.msg.CheckJoinRoomRetMsg;
import game.scene.msg.RegSceneMsg;

/**
 * @author zuoge85@gmail.com on 16/9/27.
 */
public class SceneMessageManager {
    private static final Logger log = LoggerFactory.getLogger(SceneMessageManager.class);

    @Autowired
    private SceneManager sceneManager;
    @Autowired
    private RoomService roomService;
   

    void handler(PxMsg msg) {
        try {
            switch (msg.getType()){
                case RegSceneMsg.ID:{
                    RegSceneMsg regSceneMsg = (RegSceneMsg) msg;
                    log.info("注册Scene", regSceneMsg);
                    Scene scene = sceneManager.reg(
                            msg.getSession().channel, regSceneMsg.getSceneId(),
                            regSceneMsg.getSceneAddress(), regSceneMsg.getScenePort()
                    );
                    if (scene != null) {
                        msg.getSession().set(scene);
                    }
                    break;
                }
                case CheckJoinRoomRetMsg.ID:{
                    CheckJoinRoomRetMsg checkJoinRoomRetMsg = (CheckJoinRoomRetMsg) msg;
                    roomService.joinRoomSceneSuccess(checkJoinRoomRetMsg.getJoinUserId(), checkJoinRoomRetMsg.isSucccess());
                    break;
                }
                
            }
            handlerRoom2(msg);
            
        } catch (Throwable th) {
            log.error("严重消息错误 " + msg, th);
//            Scene scene = (Scene) msg.getSession().get();
//            if (sessionId > -1 && scene != null) {
////                Net.getInstance().closeSession(sessionId, SystemErrorCode.PROTOCAL_ERROR);
//                log.error("踢掉session:" + sessionId);
//                scene.unReg(sessionId);
//            }
        }
    }
    
    

    private void handlerRoom2(PxMsg msg) {
		switch (msg.getType()) {
		case ChapterStart2Msg.ID:
			roomService.douniuChapterStart((ChapterStart2Msg)msg);
			break;
		case ChapterEnd2Msg.ID:
			roomService.douniuChapterEnd((ChapterEnd2Msg)msg);
			break;
		case DelRoom2Msg.ID:
			roomService.douniuDelRoom((DelRoom2Msg)msg);
			break;
		case CheckExitRoom2Msg.ID:
			roomService.douniuExitRoom((CheckExitRoom2Msg)msg);
			break;
		default:
			break;
		}
		
	}



}
