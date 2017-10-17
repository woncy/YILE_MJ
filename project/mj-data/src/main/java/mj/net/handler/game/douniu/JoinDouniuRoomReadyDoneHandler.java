package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.login.douniu.JoinDouniuRoomReadyDone;

public interface JoinDouniuRoomReadyDoneHandler<U> extends MessageHandler<JoinDouniuRoomReadyDone, U>{

	@Override
	public boolean handler(JoinDouniuRoomReadyDone msg, U user) ;

}
