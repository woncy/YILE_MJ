package game.zjh.scene.handler;

import org.springframework.stereotype.Component;

import game.zjh.scene.room.ZJHSceneUser;
import mj.net.handler.game.zjh.VoteDelZJHSelectResultHandler;
import mj.net.message.game.zjh.VoteDelZJHSelectResult;
@Component
public class VoteDelZJHSelectResultHandlerImpl implements VoteDelZJHSelectResultHandler<ZJHSceneUser>{

	@Override
	public boolean handler(VoteDelZJHSelectResult msg, ZJHSceneUser user) {
		user.getRoom().VoteResult(msg, user);
		return false;
	}

}
