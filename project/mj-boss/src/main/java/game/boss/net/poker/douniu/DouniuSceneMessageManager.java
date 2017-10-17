package game.boss.net.poker.douniu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.net.Session;
import com.isnowfox.game.proxy.message.PxMsg;

import game.boss.ServerRuntimeException;
import game.boss.douniu.msg.DouniuExitRoomMsg;
import game.boss.services.UserService;
import game.boss.services.douniu.DouniuRoomService;
import game.douniu.scene.msg.CheckDelDouniuRoomMsg;
import game.douniu.scene.msg.CheckExitDouniuRoomMsg;
import game.douniu.scene.msg.CheckJoinDouniuRoomResult;
import game.douniu.scene.msg.DouniuChapterEndMsg;
import game.douniu.scene.msg.DouniuChapterStartMsg;
import game.douniu.scene.msg.DouniuRecordRoomMsg;
import game.douniu.scene.msg.RegDouniuSceneMsg;
import game.douniu.scene.msg.RoomOverMsg;

/**
 * 未完成
 * 
 * @author 13323659953@163.com
 *
 */
public class DouniuSceneMessageManager {
	private static final Logger log = LoggerFactory.getLogger(DouniuSceneMessageManager.class);
	@Autowired
	private DouniuSceneManager DouniuSceneManager;
	@Autowired
	private DouniuRoomService douniuRoomService;
	@Autowired
	private UserService userService;
	  
	  void handler(PxMsg msg){
		  //处理消息业务
				try{
					  switch (msg.getType()) {
							case RegDouniuSceneMsg.ID:
								 RegDouniuSceneMsg douniuSceneMsg = (RegDouniuSceneMsg) msg;
								 log.info("注册DouniuScene", douniuSceneMsg);
								 DouniuScene douniuScene = DouniuSceneManager.reg(
					                     msg.getSession().channel, douniuSceneMsg.getDouniuSceneId(),
					                     douniuSceneMsg.getDouniuSceneAddress(), douniuSceneMsg.getDouniuScenePort()
					             );
					             if (douniuScene != null) {
					                 msg.getSession().set(douniuScene);
					             }
								break;
							case CheckJoinDouniuRoomResult.ID:
									CheckJoinDouniuRoomResult CheckJoinDouniuRoomResult = (CheckJoinDouniuRoomResult) msg;
					                douniuRoomService.joinRoomSceneSuccess(CheckJoinDouniuRoomResult.getJoinUserId(), 
					                		CheckJoinDouniuRoomResult.isSucccess());
				                break;
							case RoomOverMsg.ID:
								RoomOverMsg roomOverMsg = (RoomOverMsg)msg;
								douniuRoomService.delRoom(roomOverMsg.getCrateUserId(),null, true);
								 break;
							case CheckDelDouniuRoomMsg.ID:
								CheckDelDouniuRoomMsg delMsg = (CheckDelDouniuRoomMsg)msg;
								douniuRoomService.delRoomSceneSuccess(delMsg);  //斗牛删除房间
								 break;
							case DouniuChapterStartMsg.ID:
								DouniuChapterStartMsg startMsg = (DouniuChapterStartMsg)msg;
								douniuRoomService.chapterStart(startMsg);
								 break;
							case DouniuChapterEndMsg.ID:  //斗牛一局结束
								DouniuChapterEndMsg endMsg =(DouniuChapterEndMsg)msg;
								douniuRoomService.chapterEnd(endMsg);
								break;
							case CheckExitDouniuRoomMsg.ID:{
								 CheckExitDouniuRoomMsg checkExitRoomMsg = (CheckExitDouniuRoomMsg) msg;
								 douniuRoomService.exitDouniuRoomSceneSuccess(checkExitRoomMsg.getUserId(), checkExitRoomMsg.getSceneId(), checkExitRoomMsg.isResult());
				                    break;
				                }
							case DouniuRecordRoomMsg.ID:{
								DouniuRecordRoomMsg roomMsg = (DouniuRecordRoomMsg) msg;
								douniuRoomService.balanceRoom(roomMsg);
			                	break;
							}
							default:
								break;
							}
				}catch(Throwable th){
					log.error("严重消息错误 " + msg, th);
				}
	}

	private DouniuScene checkScene(PxMsg msg) {
		Session<DouniuScene> sceneSession = msg.getSession();
		final DouniuScene DouniuScene = sceneSession.get();
		if (DouniuScene == null) {
			log.error("未注册的DouniuScene:{},DouniuSceneSession:{}", msg, sceneSession);
			throw new ServerRuntimeException("未注册的DouniuScene:" + msg + ",DouniuSceneSession:" + sceneSession);
		}
		return DouniuScene;
	}
}
