package game.scene.douniu.handler;


import org.springframework.stereotype.Component;

import game.scene.douniu.room.DouniuSceneUser;
import mj.net.handler.game.douniu.DouniuShowHandler;
import mj.net.message.game.douniu.DouniuShow;

/**
 * 返回给前端牌的类型
 * @author Administrator
 *
 */
@Component
public class DouniuShowHandlerImpl implements DouniuShowHandler<DouniuSceneUser>{
	@Override
	public boolean handler(DouniuShow msg, DouniuSceneUser user) {
		user.getRoom().showRet(msg, user);
		return false;
	}

}
