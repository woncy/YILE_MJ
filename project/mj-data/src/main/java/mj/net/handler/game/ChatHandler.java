package mj.net.handler.game;

import mj.net.handler.MessageHandler;
import mj.net.message.game.Chat;

public interface ChatHandler  <U> extends MessageHandler<Chat, U>{
	
	@Override
    boolean handler(Chat msg, U user);

}
