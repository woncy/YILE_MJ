package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.douniu.DNKaiPaiHandler;
import mj.net.message.game.douniu.DNKaiPai;

@Component
public class DNKaiPaiHandlerImpi implements DNKaiPaiHandler<SceneUser>{

	@Override
	public boolean handler(DNKaiPai msg, SceneUser user) {
		user.getRoom().kaipai(user);
		return false;
	}

}
