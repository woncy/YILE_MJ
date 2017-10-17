package mj.net.handler.game;

import mj.net.handler.MessageHandler;
import mj.net.message.game.StaticsResult;

public interface StaticsResultHandler  <U> extends MessageHandler<StaticsResult, U>{
	
	@Override
    boolean handler(StaticsResult msg, U user);

}
