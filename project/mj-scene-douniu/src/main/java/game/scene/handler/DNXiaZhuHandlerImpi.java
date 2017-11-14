package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.douniu.DNXiaZhuHandler;
import mj.net.message.game.douniu.DNXiaZhu;

@Component
public class DNXiaZhuHandlerImpi implements DNXiaZhuHandler<SceneUser>{

	@Override
	public boolean handler(DNXiaZhu msg, SceneUser user) {
		user.getRoom().xiazhu(msg,user);
		return false;
	}

}
