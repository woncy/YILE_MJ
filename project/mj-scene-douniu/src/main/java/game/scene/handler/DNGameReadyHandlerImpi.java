package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.douniu.DNGameReadyHandler;
import mj.net.message.game.douniu.DNGameReady;

@Component
public class DNGameReadyHandlerImpi implements DNGameReadyHandler<SceneUser> {

	@Override
	public boolean handler(DNGameReady msg, SceneUser user) {
		user.getRoom().ready(msg,user);
		return false;
	}

}
