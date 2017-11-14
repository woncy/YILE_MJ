package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.douniu.DNGameStartHandler;
import mj.net.message.game.douniu.DNGameStart;
@Component
public class DNGameStartHandlerImpi implements DNGameStartHandler<SceneUser>{

	@Override
	public boolean handler(DNGameStart msg, SceneUser user) {
		user.getRoom().gameStart(user);
		return false;
	}

}
