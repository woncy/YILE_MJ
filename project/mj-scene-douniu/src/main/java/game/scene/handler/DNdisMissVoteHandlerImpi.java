package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.douniu.DNdismissVoteHandler;
import mj.net.message.game.douniu.DNDismissVote;

@Component
public class DNdisMissVoteHandlerImpi implements DNdismissVoteHandler<SceneUser>{

	@Override
	public boolean handler(DNDismissVote msg, SceneUser user) {
		user.getRoom().voteDelRoom(msg,user);
		return false;
	}

}
