package game.zjh.scene.handler;

import org.springframework.stereotype.Component;
import game.zjh.scene.room.ZJHSceneUser;
import mj.net.handler.login.zjh.GameReadySatrtHandler;
import mj.net.message.login.zjh.GameReadySatrt;

@Component
public class GameReadyStartHandlerImpl implements GameReadySatrtHandler<ZJHSceneUser>{

	@Override
	public boolean handler(GameReadySatrt msg, ZJHSceneUser user) {
		user.getRoom().joinZJHGame(user);
		return false;
	}

}
