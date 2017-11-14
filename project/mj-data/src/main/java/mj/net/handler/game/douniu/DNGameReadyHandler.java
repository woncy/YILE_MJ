package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DNGameReady;

public interface DNGameReadyHandler<U> extends MessageHandler<DNGameReady, U> {

	@Override 
	public boolean handler(DNGameReady msg, U user);
	
	

}
