package mj.net.handler.login;

import mj.net.handler.MessageHandler;
import mj.net.message.login.Pong;

public interface PongHandler<U> extends MessageHandler<Pong, U>{

	@Override
	public boolean handler(Pong msg, U user);

	
}
