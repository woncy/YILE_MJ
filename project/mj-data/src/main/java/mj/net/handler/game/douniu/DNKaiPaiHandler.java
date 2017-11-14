package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DNKaiPai;

public interface DNKaiPaiHandler<U> extends MessageHandler<DNKaiPai, U>{

	@Override 
	public boolean handler(DNKaiPai msg, U user);
	
}
