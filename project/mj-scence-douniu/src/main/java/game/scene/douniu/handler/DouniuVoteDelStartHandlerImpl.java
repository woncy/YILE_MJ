package game.scene.douniu.handler;

import game.scene.douniu.room.DouniuSceneUser;

import org.springframework.stereotype.Component;
import mj.net.handler.game.douniu.DouniuVoteDelStartHandler;
import mj.net.message.game.douniu.DouniuVoteDelStart;

/**
 * @author zuoge85@gmail.com on 2017/1/18.
 */
@Component
public class DouniuVoteDelStartHandlerImpl  implements DouniuVoteDelStartHandler<DouniuSceneUser> {

	@Override
	public boolean handler(DouniuVoteDelStart msg, DouniuSceneUser user) {
	    user.getRoom().voteDelStart(msg, user);
        return false;
	}
}
