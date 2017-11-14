package mj.net.handler.game;

import mj.net.handler.MessageHandler;
import mj.net.message.game.AudioMsg;

public interface AudioMsgHandler<U> extends MessageHandler<AudioMsg, U> {

	@Override 
	public boolean handler(AudioMsg msg, U user);
	
}
