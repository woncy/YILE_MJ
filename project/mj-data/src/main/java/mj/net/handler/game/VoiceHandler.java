package mj.net.handler.game;

import mj.net.handler.MessageHandler;
import mj.net.message.game.Voice;

public interface VoiceHandler<U> extends MessageHandler<Voice, U> {

	@Override
	public boolean handler(Voice msg, U user);
}
