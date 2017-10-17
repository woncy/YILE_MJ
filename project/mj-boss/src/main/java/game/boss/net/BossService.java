package game.boss.net;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.isnowfox.core.net.message.Message;

import game.boss.SceneUserInfo;
import game.boss.ServerRuntimeException;
import game.boss.ZJH.ZJHSceneUserInfo;
import game.boss.ZJH.msg.DelZJHRoomMsg;
import game.boss.ZJH.msg.JoinZJHRoomMsg;
import game.boss.dao.entity.RoomDO;
import game.boss.dao.entity.RoomUserDO;
import game.boss.douniu.DouniuSceneUserInfo;
import game.boss.douniu.msg.DeDouniuRoomMsg;
import game.boss.douniu.msg.DouniuExitRoomMsg;
import game.boss.douniu.msg.JoinDouniuRoomMsg;
import game.boss.model.DouniuRoom;
import game.boss.model.PdkRoom;
import game.boss.model.Room;
import game.boss.model.User;
import game.boss.model.ZJHRoom;
import game.boss.msg.DelRoomMsg;
import game.boss.msg.ExitRoomMsg;
import game.boss.msg.JoinRoomMsg;
import game.boss.net.poker.douniu.DouniuScene;
import game.boss.net.poker.douniu.DouniuSceneManager;
import game.boss.net.poker.zjh.ZJHScene;
import game.boss.net.poker.zjh.ZJHSceneManager;
import game.douniu.scene.msg.CheckDelDouniuRoomMsg;
import game.douniu.scene.msg.CheckExitDouniuRoomMsg;
import game.boss.poker.dao.entity.TbPkRoomDO;
import game.boss.poker.dao.entity.TbPkRoomUserDO;
import game.douniu.scene.msg.DouniuUserInfo;
import game.scene.msg.CheckDelRoomMsg;
import game.scene.msg.CheckExitRoomMsg;
import game.scene.msg.CheckOfflineRoomMsg;
import game.zjh.scene.msg.CheckDelZJHRoomMsg;

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
    
    @Autowired
    private ZJHSceneManager ZJHSceneManager;

    @Autowired
    private DouniuSceneManager douniuSceneManager;
    
    public int getRandomSceneId() {
        return sceneManager.getRandomSceneId();
    }
    //扎金花
    public int getRandomZJHSceneId() {
    	return ZJHSceneManager.getRandomZJHSceneId();
    }
  //斗牛
    public int getRandomDouniuSceneId() {
    	return douniuSceneManager.getRandomDouniuSceneId();
    }
    
    /**
     * 斗牛
     * @param user
     * @param Douniuroom
     * @param TbPkRoomUserDO
     * @param sessionId
     * 2017年7月10日
     */
    public void startJoinDouniuScene(User user, DouniuRoom Douniuroom, TbPkRoomUserDO TbPkRoomUserDO, int sessionId){
    	TbPkRoomDO tbpkRoomDO = Douniuroom.getTbPkRoomDO();
    	
    	DouniuScene douniuScene = douniuSceneManager.getDouniuScene(tbpkRoomDO.getSceneId());
    	if (douniuScene == null) {
            throw new ServerRuntimeException("Douniuscene 为空, sceneId:" + tbpkRoomDO.getSceneId());
        }
        douniuSceneManager.send(tbpkRoomDO.getSceneId(), Douniuroom.toMsg(user));                
        JoinDouniuRoomMsg msg = new JoinDouniuRoomMsg();
        msg.setSessionId(user.getSessionId());
        msg.setDouniuSceneId( new Integer(tbpkRoomDO.getSceneId()).shortValue());
        msg.setDouniuSceneAddress(douniuScene.getAddress());
        msg.setDouniuScenePort(douniuScene.getPort());
        user.sendToGateway(msg);  //youwenti
    }
    /**
     * 扎金花
     *@param user
     *@param room
     *@param roomUserDO
     *@param sessionId
     *@return
     * 2017年6月29日
     */
    public void startJoinZJHScene(User user, ZJHRoom ZJHroom, TbPkRoomUserDO TbPkRoomUserDO, int sessionId) {
//    	TbPkRoomDO room = TbPkRoomUserDO.getRoom();
//    	ZJHroom.setTbPkRoomDO(room);
//        TbPkRoomDO tbpkRoomDO = ZJHroom.getTbPkRoomDO();
//        ZJHScene zjhScene = ZJHSceneManager.getZJHScene(tbpkRoomDO.getSceneId());
//        if (zjhScene == null) {
//            throw new ServerRuntimeException("ZJHscene 为空, sceneId:" + tbpkRoomDO.getSceneId());
//        }
//        ZJHSceneManager.send(tbpkRoomDO.getSceneId(), ZJHroom.toMsg(user));
//        JoinZJHRoomMsg msg = new JoinZJHRoomMsg();
//        msg.setSessionId(user.getSessionId());
//        msg.setZJHSceneId(new Integer(tbpkRoomDO.getSceneId()).shortValue());
//        msg.setZJHSceneAddress(zjhScene.getAddress());
//        msg.setZJHScenePort(zjhScene.getPort());
//        user.sendToGateway(msg);
    }
    /**
     * 麻将
     *@param user
     *@param room
     *@param roomUserDO
     *@param sessionId
     *@return
     * 2017年6月29日
     */
    public void startJoinScene(User user, Room room, RoomUserDO roomUserDO, int sessionId) {
        RoomDO roomDO = room.getRoomDO();
        Scene scene = sceneManager.getScene(roomDO.getSceneId());
        if (scene == null) {
            throw new ServerRuntimeException("scene 为空, sceneId:" + roomDO.getSceneId());
        }

        sceneManager.send(roomDO.getSceneId(), room.toMsg(user));
        JoinRoomMsg msg = new JoinRoomMsg();
        msg.setSessionId(user.getSessionId());
        msg.setSceneId((short) roomDO.getSceneId());
        msg.setSceneAddress(scene.getAddress());
        msg.setScenePort(scene.getPort());
        user.sendToGateway(msg);
    }
    
    /**
     * 跑得快
     * @param user
     * @param room
     * @param roomUserDO
     * @param sessionId
     */
    public void startJoinScene(User user, PdkRoom room, TbPkRoomUserDO roomUserDO, int sessionId) {
//    	TbPkRoomDO roomDO = room.getRoomDO();
//    	Scene scene = sceneManager.getScene(roomDO.getSceneId());
//    	if (scene == null) {
//    		throw new ServerRuntimeException("scene 为空, sceneId:" + roomDO.getSceneId());
//    	}
//    	
//    	sceneManager.send(roomDO.getSceneId(), room.toMsg(user));
//    	JoinRoomMsg msg = new JoinRoomMsg();
//    	msg.setSessionId(user.getSessionId());
//    	msg.setSceneId(new Integer(roomDO.getSceneId()).shortValue());
//    	msg.setSceneAddress(scene.getAddress());
//    	msg.setScenePort(scene.getPort());
//    	user.sendToGateway(msg);
    }
 

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

    public void startExitRoomScene(User user, RoomDO room) {
        Scene scene = sceneManager.getScene(room.getSceneId());
        if (scene == null) {
            throw new ServerRuntimeException("scene 为空, sceneId:" + room.getSceneId());
        }

        CheckExitRoomMsg m = new CheckExitRoomMsg();
        m.setSceneId(scene.getId());
        m.setRoomId(room.getId());
        m.setUserId(user.getUserId());
        sceneManager.send(room.getSceneId(), m);
    }

    public void startExitRoomGateway(User user, int sceneId) {
        ExitRoomMsg msg = new ExitRoomMsg();
        msg.setSessionId(user.getSessionId());
        msg.setSceneId((short) sceneId);
        user.sendToGateway(msg);
    }


    public void startDelRoomScene(int UserId, RoomDO room, boolean isEnd) {
        Scene scene = sceneManager.getScene(room.getSceneId());
        if (scene == null) {
            throw new ServerRuntimeException("scene 为空, sceneId:" + room.getSceneId());
        }
        CheckDelRoomMsg m = new CheckDelRoomMsg();
        m.setSceneId((short) scene.getId());
        m.setRoomId(room.getId());
        m.setUserId(UserId);
        m.setEnd(isEnd);
        sceneManager.send(room.getSceneId(), m);
    }
    /**
     * 扎金花开始删除房间
     * @param UserId
     * @param room
     * @param isEnd
     */
    public void startDelZJHRoomScene(int UserId, TbPkRoomDO room, boolean isEnd) {
    	Scene scene = sceneManager.getScene(room.getSceneId());
    	if (scene == null) {
    		throw new ServerRuntimeException("scene 为空, sceneId:" + room.getSceneId());
    	}
    	CheckDelZJHRoomMsg m = new CheckDelZJHRoomMsg();
    	m.setSceneId((short) scene.getId());
    	m.setRoomId(room.getId());
    	m.setUserId(UserId);
    	m.setEnd(isEnd);
    	ZJHSceneManager.send(room.getSceneId(), m);
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
	public void startZJHDelRoomGateway(List<ZJHSceneUserInfo> infos) {
		for (ZJHSceneUserInfo zjhSceneUserInfo : infos) {
			DelZJHRoomMsg msg = new DelZJHRoomMsg();
			msg.setGatewayId(msg.getGatewayId());
			msg.setSessionId(msg.getSessionId());
			msg.setUserId(msg.getUserId());
			gatewayManager.writeMsg(zjhSceneUserInfo.getGatewayId(), msg);
		}
		
	}
	/**
	 * 斗牛退出房间
	 */
	 public void startExitRoomScene(User user, TbPkRoomDO room) {
	        DouniuScene scene = douniuSceneManager.getDouniuScene(room.getSceneId());
	        if (scene == null) {
	            throw new ServerRuntimeException("scene 为空, sceneId:" + room.getSceneId());
	        }

	        CheckExitDouniuRoomMsg m = new CheckExitDouniuRoomMsg();
	        m.setSceneId(scene.getId());
	        m.setRoomId(room.getId());
	        m.setUserId(user.getUserId());
	        douniuSceneManager.send(room.getSceneId(), m);
	    }
	 public void douniuStartExitRoomGateway(User user, int sceneId) {
	        DouniuExitRoomMsg msg = new DouniuExitRoomMsg();
	        msg.setSessionId(user.getSessionId());
	        msg.setSceneId((short) sceneId);
	        user.sendToGateway(msg);
	    }
	/**
	 * 斗牛删除房间
	 * 
	 */
	 public void startDouniuDelRoomScene(int userId,TbPkRoomDO room ,boolean isEnd ) {
	        DouniuScene scene = douniuSceneManager.getDouniuScene(room.getSceneId());
	        if (scene == null) {
	            throw new ServerRuntimeException("Douniuscene 为空, sceneId:" + room.getSceneId());
	        }

	        CheckDelDouniuRoomMsg m = new CheckDelDouniuRoomMsg();
	        m.setSceneId((short) scene.getId());
	        m.setRoomId(room.getId());
	        m.setUserId(userId);
	        m.setEnd(isEnd);
	        douniuSceneManager.send(room.getSceneId(), m);
	    }
	 public void startDouniuDelRoomGateway(List<DouniuSceneUserInfo> infos) {
			for (DouniuSceneUserInfo douniuUserInfo : infos) {
				DeDouniuRoomMsg msg = new DeDouniuRoomMsg();
				msg.setGatewayId(msg.getGatewayId());
				msg.setSessionId(msg.getSessionId());
				msg.setUserId(msg.getUserId());
				gatewayManager.writeMsg(douniuUserInfo.getGatewayId(), msg);
			}
			
		}
	
}
