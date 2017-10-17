package game.zjh.scene.handler;

import org.springframework.stereotype.Component;

import game.zjh.scene.room.ZJHSceneUser;
import mj.net.handler.login.zjh.ZJHGameStartHandler;
import mj.net.message.game.zjh.ZJHGameSatrt;
@Component
public class ZJHGameSatrtHandlerImpl implements ZJHGameStartHandler<ZJHSceneUser>{

	@Override
	public boolean handler(ZJHGameSatrt msg, ZJHSceneUser user) {
		user.getRoom().gameStart(user);
		return false;
	}

}
