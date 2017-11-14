package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.AudioMsgHandler;
import mj.net.message.game.AudioMsg;

@Component
public class AudioMsgHandlerImpi implements AudioMsgHandler<SceneUser>{

	@Override
	public boolean handler(AudioMsg msg, SceneUser user) {
		user.getRoom().audioMsg(msg,user);
		return false;
	}

}
