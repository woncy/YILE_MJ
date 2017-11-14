package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.douniu.DNdismissUserResultHandler;
import mj.net.message.game.douniu.DNDismissUserResult;

@Component
public class DNdismissUserResultHandlerImpi implements DNdismissUserResultHandler<SceneUser>{

	@Override
	public boolean handler(DNDismissUserResult msg, SceneUser user) {
		user.getRoom().voteDelRoomUserResult(msg,user);
		return false;
	}

}
