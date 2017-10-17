package game.scene.douniu.handler;

import org.springframework.stereotype.Component;

import game.scene.douniu.room.DouniuSceneUser;
import mj.net.handler.game.douniu.DouniuZhuHandler;
import mj.net.message.game.douniu.DouniuShu;


@Component
public class DouniuZhuHandlerImpl implements DouniuZhuHandler<DouniuSceneUser> {

	@Override
	public boolean handler(DouniuShu msg, DouniuSceneUser user) {
		user.getRoom().zhuRet(user, msg);
		return false;
	}

}
