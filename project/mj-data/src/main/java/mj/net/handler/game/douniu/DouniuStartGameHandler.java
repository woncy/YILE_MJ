package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DouniuStartGame;

public interface DouniuStartGameHandler<U> extends MessageHandler<DouniuStartGame, U>{
	@Override
	public boolean handler(DouniuStartGame msg, U user);
}
