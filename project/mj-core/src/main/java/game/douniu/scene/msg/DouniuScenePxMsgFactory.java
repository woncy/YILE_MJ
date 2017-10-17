package game.douniu.scene.msg;

import com.isnowfox.game.proxy.PxMsgFactory;

/**
 * 未完成
 * @author 13323659953@163.com
 *
 */
public class DouniuScenePxMsgFactory extends PxMsgFactory {
	 @Override
	   protected void init() {
		 super.init();
		 super.add(RegDouniuSceneMsg.ID, RegDouniuSceneMsg.class);
		 super.add(CheckJoinDouniuRoomMsg.ID, CheckJoinDouniuRoomMsg.class);
		 super.add(CheckJoinDouniuRoomResult.ID, CheckJoinDouniuRoomResult.class);
		 super.add(DouniuChapterStartMsg.ID,DouniuChapterStartMsg.class);
		 super.add(RoomOverMsg.ID, RoomOverMsg.class);
		 super.add(CheckDelDouniuRoomMsg.ID, CheckDelDouniuRoomMsg.class);
		 super.add(DouniuChapterEndMsg.ID, DouniuChapterEndMsg.class);
		 super.add(DouniuRecordRoomMsg.ID,DouniuRecordRoomMsg.class);
		 super.add(CheckExitDouniuRoomMsg.ID, CheckExitDouniuRoomMsg.class);
	 }
}
