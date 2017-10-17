package game.zjh.scene.net;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.isnowfox.core.net.message.Message;
import com.isnowfox.game.proxy.message.PxMsg;

import game.scene.msg.CheckDelRoomMsg;
import game.scene.msg.CheckExitRoomMsg;
import game.scene.msg.CheckJoinRoomMsg;
import game.scene.msg.CheckOfflineRoomMsg;
import game.scene.msg.RegSceneMsg;
import game.zjh.scene.msg.CheckDelZJHRoomMsg;
import game.zjh.scene.msg.CheckJoinZJHRoomMsg;
import game.zjh.scene.msg.RegZJHSceneMsg;
import game.zjh.scene.room.ZJHRoomService;
@Service
public class ZJHSceneService {
	 @Value("${ZJHsceneId}")
	    private int sceneId;
	    @Value("${address}")
	    private String address;

	    @Value("${port}")
	    private int port;

	    @Autowired
	    private BossClient bossClient;
	    @Autowired
	    private ZJHRoomService ZJHroomService;
	    @Autowired ZGatewayManager gatewayManager;

	    public void onBossConnect() {
	    	int a= sceneId;
	    	String ad = address;
	    	int p = port;
	        bossClient.writeAndFlush(new RegZJHSceneMsg((short) sceneId, address, port));
	    }

	    public void onBossMessage(PxMsg msg) {
	        switch (msg.getType()) {
	            case CheckJoinZJHRoomMsg.ID: {
	              ZJHroomService.checkJoinZJHRoom((CheckJoinZJHRoomMsg) msg);
	                break;
	            }
	            case CheckDelZJHRoomMsg.ID: {
	            	ZJHroomService.checkDelRoom((CheckDelZJHRoomMsg) msg);
	            	break;
	            }
//	            case CheckOfflineRoomMsg.ID: {
//	               // roomService.checkOfflineRoom((CheckOfflineRoomMsg) msg);
//	                break;
//	            }
	            case CheckExitRoomMsg.ID: {
	                //roomService.checkExitRoom((CheckExitRoomMsg) msg);
	                break;
	            }
	        }
	    }

	    public void sendMessage(int gatewayId, int sessionId, Message msg) {
	        gatewayManager.sendMessage(gatewayId, sessionId, msg);
	    }

		public ZJHRoomService getZJHroomService() {
			return ZJHroomService;
		}

		public void setZJHroomService(ZJHRoomService zJHroomService) {
			ZJHroomService = zJHroomService;
		}
	    
	    
}
