package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DNGameStart;

public interface DNGameStartHandler<U> extends MessageHandler<DNGameStart, U>{

	@Override
	public boolean handler(DNGameStart msg, U user);
	

}
