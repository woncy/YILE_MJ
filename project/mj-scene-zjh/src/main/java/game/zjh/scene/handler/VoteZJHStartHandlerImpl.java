package game.zjh.scene.handler;

import org.springframework.stereotype.Component;

import game.zjh.scene.room.ZJHSceneUser;
import mj.net.handler.game.zjh.VoteZJHStartHandler;
import mj.net.message.game.zjh.VoteStart;
@Component
public class VoteZJHStartHandlerImpl implements VoteZJHStartHandler<ZJHSceneUser>{

	@Override
	public boolean handler(VoteStart msg, ZJHSceneUser user) {
		user.getRoom().VoteStart(msg, user);
		return false;
	}

}
