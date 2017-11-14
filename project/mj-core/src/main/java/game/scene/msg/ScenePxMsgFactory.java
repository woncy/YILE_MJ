package game.scene.msg;

import com.isnowfox.game.proxy.PxMsgFactory;

import game.douniu.scene.msg.CheckExitRoom2Msg;
import game.douniu.scene.msg.CheckJoinRoom2Msg;
import game.douniu.scene.msg.CheckJoinRoom2Result;
import game.douniu.scene.msg.ChapterEnd2Msg;
import game.douniu.scene.msg.ChapterStart2Msg;
import game.douniu.scene.msg.DelRoom2Msg;


public class ScenePxMsgFactory extends PxMsgFactory {
    @Override
    protected void init() {
        super.init();
        super.add(RegSceneMsg.ID, RegSceneMsg.class);
        super.add(CheckJoinRoomMsg.ID, CheckJoinRoomMsg.class);
        super.add(CheckJoinRoomRetMsg.ID, CheckJoinRoomRetMsg.class);
        super.add(CheckOfflineRoomMsg.ID, CheckOfflineRoomMsg.class);
        initRoom2();
    }

	private void initRoom2() {
		super.add(CheckExitRoom2Msg.ID, CheckExitRoom2Msg.class);
		super.add(CheckJoinRoom2Msg.ID, CheckJoinRoom2Msg.class);
		super.add(CheckJoinRoom2Result.ID, CheckJoinRoom2Result.class);
		super.add(ChapterEnd2Msg.ID, ChapterEnd2Msg.class);
		super.add(ChapterStart2Msg.ID, ChapterStart2Msg.class);
		super.add(DelRoom2Msg.ID, DelRoom2Msg.class);
	}
}
