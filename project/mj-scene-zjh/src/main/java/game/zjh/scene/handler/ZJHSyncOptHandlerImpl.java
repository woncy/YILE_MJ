package game.zjh.scene.handler;

import org.springframework.stereotype.Component;

import game.zjh.scene.room.ZJHSceneUser;
import mj.net.handler.login.zjh.ZJHOperationHandler;
import mj.net.message.game.zjh.ZJHOperation;
@Component
public class ZJHSyncOptHandlerImpl implements ZJHOperationHandler<ZJHSceneUser>{

	@Override
	public boolean handler(ZJHOperation msg, ZJHSceneUser user) {
		user.getRoom().Operation(msg, user);
		return false;
	}

}
