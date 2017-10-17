package game.scene.douniu.handler;


import org.springframework.stereotype.Component;

import game.scene.douniu.room.DouniuSceneUser;
import mj.net.handler.game.douniu.DouniuStartGameHandler;
import mj.net.message.game.douniu.DouniuStartGame;

@Component
public class DouniuStartGameHandlerImpl implements DouniuStartGameHandler<DouniuSceneUser>{

	@Override
	public boolean handler(DouniuStartGame msg, DouniuSceneUser user) {
		user.getRoom().chapterStart(user);
		return false;
	}

}
