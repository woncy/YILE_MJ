package game.scene.douniu.handler;

import game.scene.douniu.room.DouniuSceneUser;
import mj.net.handler.game.douniu.DouniuBiCardOtherRetHandler;
import mj.net.message.game.douniu.DouniuBiCardOtherRet;

public class DouniuBiCardOtherRetHandlerImpl implements DouniuBiCardOtherRetHandler<DouniuSceneUser> {

		@Override
		public boolean handler(DouniuBiCardOtherRet msg, DouniuSceneUser user) {
			user.getRoom().sendOtherRet(user, msg);
			return false;
		}

}
