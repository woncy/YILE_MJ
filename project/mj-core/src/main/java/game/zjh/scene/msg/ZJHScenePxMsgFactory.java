package game.zjh.scene.msg;

import com.isnowfox.game.proxy.PxMsgFactory;

import game.boss.ZJH.msg.JoinZJHRoomMsg;
/**
 * 未完成
 * @author 13323659953@163.com
 *
 */
public class ZJHScenePxMsgFactory extends PxMsgFactory {
	 @Override
	   protected void init() {
		 super.init();
		 super.add(RegZJHSceneMsg.ID, RegZJHSceneMsg.class);
		 super.add(CheckJoinZJHRoomMsg.ID, CheckJoinZJHRoomMsg.class);
		 super.add(CheckJoinZJHRoomResult.ID, CheckJoinZJHRoomResult.class);
		 super.add(RoomOverMsg.ID, RoomOverMsg.class);
		 super.add(CheckDelZJHRoomMsg.ID, CheckDelZJHRoomMsg.class);
		 
	 }
}
