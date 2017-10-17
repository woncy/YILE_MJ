package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.StaticsResult;

public interface DouniuStaticsResultHandler <U> extends MessageHandler<StaticsResult,U>{
  
	@Override
	boolean handler(StaticsResult msg,U user);
}
