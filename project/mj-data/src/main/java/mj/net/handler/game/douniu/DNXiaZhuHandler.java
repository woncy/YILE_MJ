package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DNXiaZhu;

public interface DNXiaZhuHandler<U> extends MessageHandler<DNXiaZhu, U>{

	@Override 
	public boolean handler(DNXiaZhu msg, U user);

}
