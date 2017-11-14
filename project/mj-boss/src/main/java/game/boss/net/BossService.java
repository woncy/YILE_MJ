package game.boss.net;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.net.message.Message;

import game.boss.SceneUserInfo;
import game.boss.ServerRuntimeException;
import game.boss.model.Room2;
import game.boss.model.User;
import game.boss.msg.DelRoomMsg;
import game.boss.msg.ExitRoomMsg;
import game.boss.msg.JoinRoomMsg;
import game.douniu.scene.msg.CheckExitRoom2Msg;
import game.douniu.scene.msg.DelRoom2Msg;
import game.scene.msg.CheckOfflineRoomMsg;

/**
 * 暴露较为安全的管理接口
 *
 * @author zuoge85@gmail.com on 16/9/30.
 */
public class BossService {
    private static final Logger log = LoggerFactory.getLogger(BossService.class);

    @Autowired
    private GatewayManager gatewayManager;

    @Autowired
    private SceneManager sceneManager;

    /**
     * 檢查是否掉線
     *@param user
     *@param room
     *@param sessionId
     *@return
     * 2017年6月29日
     */
    public void startOfflineScene(User user,int roomId,int sceneId, int sessionId) {
        CheckOfflineRoomMsg m = new CheckOfflineRoomMsg();
        m.setUserId(user.getUserId());
        m.setRoomId(roomId);
        m.setJoinSessionId(sessionId);
        m.setJoinGatewayId((short) user.getGatewayId());
        sceneManager.send(sceneId, m);
    }
    public void startDNExitRoomScene(CheckExitRoom2Msg msg){
    	Scene scene = sceneManager.getScene(msg.getSceneId());
        if (scene == null) {
            throw new ServerRuntimeException("scene 为空, sceneId:" + msg.getSceneId());
        }
    	sceneManager.send(msg.getSceneId(), msg);
    }

   

    public void startExitRoomGateway(User user, int sceneId) {
        ExitRoomMsg msg = new ExitRoomMsg();
        msg.setSessionId(user.getSessionId());
        msg.setSceneId((short) sceneId);
        user.sendToGateway(msg);
    }
  

    public void startDelRoomGateway(List<SceneUserInfo> infos) {
        for (SceneUserInfo userInfo : infos) {
            DelRoomMsg msg = new DelRoomMsg();
            msg.setSessionId(msg.getSessionId());
            msg.setGatewayId(msg.getGatewayId());
            msg.setUserId(msg.getUserId());
            gatewayManager.writeMsg(userInfo.getGatewayId(), msg);
        }
    }

    public void writeToAll(Message msg) {
        gatewayManager.writeToAll(msg);
    }

	public void startJoinScene2(User user,Room2 room2, short sessionId) {
			int sceneId = room2.getRoomDo().getSceneId();
	        Scene scene = sceneManager.getScene(sceneId);
	        if (scene == null) {
	            throw new ServerRuntimeException("scene 为空, sceneId:" + sceneId);
	        }
	        
	        sceneManager.send(sceneId, room2.toMsg(user));
	        
	        JoinRoomMsg msg = new JoinRoomMsg();
	        msg.setSessionId(user.getSessionId());
	        msg.setSceneId((short) sceneId);
	        msg.setSceneAddress(scene.getAddress());
	        msg.setScenePort(scene.getPort());
	        user.sendToGateway(msg);
	}


	public void delRoomToScene(int sceneId,int roomId) {
		Scene scene = sceneManager.getScene(sceneId);
        if (scene == null) {
            throw new ServerRuntimeException("scene 为空, sceneId:" + sceneId);
        }
        DelRoom2Msg msg = new DelRoom2Msg();
        msg.setRoomId(roomId);
        sceneManager.send(sceneId, msg);
		
	}
	
}
