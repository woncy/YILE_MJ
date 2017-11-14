package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DNGameExitUser;

public interface DNExitUserHandler<U> extends MessageHandler<DNGameExitUser, U>{

	@Override 
	public boolean handler(DNGameExitUser msg, U user);

}
