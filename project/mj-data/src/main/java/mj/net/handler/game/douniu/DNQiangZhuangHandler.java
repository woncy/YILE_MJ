package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DNQiangZhuang;

public interface DNQiangZhuangHandler<U> extends MessageHandler<DNQiangZhuang, U>{

	@Override 
	public boolean handler(DNQiangZhuang msg, U user);
}
