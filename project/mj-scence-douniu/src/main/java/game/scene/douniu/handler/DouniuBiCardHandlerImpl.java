package game.scene.douniu.handler;

import game.scene.douniu.room.DouniuSceneUser;
import mj.net.handler.game.douniu.DouniuBiCardHandler;
import mj.net.message.game.douniu.DouniuBiCard;

public class DouniuBiCardHandlerImpl implements DouniuBiCardHandler<DouniuSceneUser> {

		@Override
		public boolean handler(DouniuBiCard msg, DouniuSceneUser user) {
			user.getRoom().biCard(user, msg);
			return false;
		}

}
