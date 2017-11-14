package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.douniu.DNExitUserHandler;
import mj.net.message.game.douniu.DNGameExitUser;

@Component
public class DNExitUserHandlerImpi implements DNExitUserHandler<SceneUser> {

	@Override
	public boolean handler(DNGameExitUser msg, SceneUser user) {
		user.getRoom().exitUser(msg,user);
		return false;
	}

}
