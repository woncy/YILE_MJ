package game.boss.net.poker.zjh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.net.Session;
import com.isnowfox.game.proxy.message.PxMsg;

import game.boss.ServerRuntimeException;
import game.boss.services.RoomService;
import game.boss.services.UserService;
import game.boss.services.ZJH.ZJHRoomService;
import game.scene.msg.CheckJoinRoomRetMsg;
import game.zjh.scene.msg.CheckDelZJHRoomMsg;
import game.zjh.scene.msg.CheckJoinZJHRoomResult;
import game.zjh.scene.msg.RegZJHSceneMsg;
import game.zjh.scene.msg.RoomOverMsg;
import game.zjh.scene.msg.ZJHChapterStartMsg;

/**
 * 未完成
 * @author 13323659953@163.com
 *
 */
public class ZJHSceneMessageManager {
	  private static final Logger log = LoggerFactory.getLogger(ZJHSceneMessageManager.class);
	  @Autowired
	  private ZJHSceneManager ZJHSceneManager;
	  	@Autowired
	    private ZJHRoomService zjhroomService;
	    @Autowired
	    private UserService userService;

	  
	  void handler(PxMsg msg){
		  //处理消息业务
				try{
					  switch (msg.getType()) {
							case RegZJHSceneMsg.ID:
								 RegZJHSceneMsg ZJHScene = (RegZJHSceneMsg) msg;
								 log.info("注册ZJHScene666", ZJHScene);
								 ZJHScene ZJHscene = ZJHSceneManager.reg(
					                     msg.getSession().channel, ZJHScene.getZJHsceneId(),
					                     ZJHScene.getZJHsceneAddress(), ZJHScene.getZJHscenePort()
					             );
					             if (ZJHscene != null) {
					                 msg.getSession().set(ZJHscene);
					             }
								break;
							case CheckJoinZJHRoomResult.ID:
									CheckJoinZJHRoomResult CheckJoinZJHRoomResult = (CheckJoinZJHRoomResult) msg;
					                zjhroomService.joinRoomSceneSuccess(CheckJoinZJHRoomResult.getJoinUserId(), 
					                		CheckJoinZJHRoomResult.isSucccess());
				                break;
							case ZJHChapterStartMsg.ID:
								ZJHChapterStartMsg startMsg = (ZJHChapterStartMsg)msg;
								zjhroomService.chapterStart(startMsg);
							case RoomOverMsg.ID:
								RoomOverMsg roomOverMsg = (RoomOverMsg)msg;
								zjhroomService.delRoom(roomOverMsg.getCrateUserId(), null, true);
							case CheckDelZJHRoomMsg.ID:
								CheckDelZJHRoomMsg delMsg = (CheckDelZJHRoomMsg)msg;
								zjhroomService.delRoomSceneSuccess(delMsg);
							default:
								break;
							}
				}catch(Throwable th){
					log.error("严重消息错误 " + msg, th);
				}
				  
	  }
	  
	  private ZJHScene checkScene(PxMsg msg) {
	        Session<ZJHScene> sceneSession = msg.getSession();
	        final ZJHScene ZJHscene = sceneSession.get();
	        if (ZJHscene == null) {
	            log.error("未注册的ZJHScene:{},ZJHsceneSession:{}", msg, sceneSession);
	            throw new ServerRuntimeException("未注册的ZJHScene:" + msg + ",ZJHsceneSession:" + sceneSession);
	        }
	        return ZJHscene;
	    }
}
