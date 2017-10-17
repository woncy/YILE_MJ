package game.scene.pdk.handler;

import org.springframework.stereotype.Component;

import game.scene.pdk.room.PdkSceneUser;
import mj.net.handler.game.pdk.JoinPdkRoomReadyDoneHandler;
import mj.net.message.room.pdk.JoinPdkRoomReady;
import mj.net.message.room.pdk.JoinPdkRoomReadyDone;

@Component
public class JoinPdkRoomReadyDoneHandlerImpi implements JoinPdkRoomReadyDoneHandler<PdkSceneUser>{

	@Override
	public boolean handler(JoinPdkRoomReadyDone msg, PdkSceneUser user) {
		user.getRoom().joinGame(user);
		return false;
	}

}
