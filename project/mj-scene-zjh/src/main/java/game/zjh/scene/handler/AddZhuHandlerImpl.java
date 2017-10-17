package game.zjh.scene.handler;

import org.springframework.stereotype.Component;

import game.zjh.scene.room.ZJHSceneUser;
import mj.net.handler.login.zjh.AddZhuHandler;
import mj.net.message.game.zjh.AddZhu;
@Component
public class AddZhuHandlerImpl implements AddZhuHandler<ZJHSceneUser>{

	@Override
	public boolean handler(AddZhu msg, ZJHSceneUser user) {
		user.getRoom().addZhu(msg, user);
		return false;
	}

}
