package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.douniu.JoinDouniuRoomReadyDoneHandler;
import mj.net.message.login.douniu.JoinDouniuRoomReadyDone;

@Component
public class JoinGameImpi implements JoinDouniuRoomReadyDoneHandler<SceneUser>{

	@Override
	public boolean handler(JoinDouniuRoomReadyDone msg, SceneUser user) {
		user.getRoom().joinGame(user);
		return false;
	}

}
