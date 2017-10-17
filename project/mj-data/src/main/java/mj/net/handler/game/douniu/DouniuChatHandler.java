package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DouniuChat;


public interface DouniuChatHandler  <U> extends MessageHandler<DouniuChat, U>{
	
	@Override
    boolean handler(DouniuChat msg, U user);

}
