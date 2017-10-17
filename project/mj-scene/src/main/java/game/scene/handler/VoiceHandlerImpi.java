package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.VoiceHandler;
import mj.net.message.game.Voice;

@Component
public class VoiceHandlerImpi implements VoiceHandler<SceneUser>{

	@Override
	public boolean handler(Voice msg, SceneUser user) {
		user.getRoom().voice(msg);
		return false;
	}

}
