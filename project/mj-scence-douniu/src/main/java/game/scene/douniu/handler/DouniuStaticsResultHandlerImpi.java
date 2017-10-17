package game.scene.douniu.handler;

import org.springframework.stereotype.Component;

import game.scene.douniu.room.DouniuSceneUser;
import mj.net.handler.game.douniu.DouniuStaticsResultHandler;
import mj.net.message.game.douniu.StaticsResult;

@Component
public class DouniuStaticsResultHandlerImpi implements DouniuStaticsResultHandler<DouniuSceneUser>{

	@Override
	public boolean handler(StaticsResult msg, DouniuSceneUser user) {
   //   user.getRoom().sendStaticsResultToAllUser(msg);
		  return false;
	}


}
