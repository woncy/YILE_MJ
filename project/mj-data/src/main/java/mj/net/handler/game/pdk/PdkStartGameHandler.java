package mj.net.handler.game.pdk;

import mj.net.handler.MessageHandler;
import mj.net.message.game.pdk.PdkStartGame;

public interface PdkStartGameHandler<U> extends MessageHandler<PdkStartGame, U>{
	@Override
	public boolean handler(PdkStartGame msg, U user);
}
