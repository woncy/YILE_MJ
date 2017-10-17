package mj.net.handler.game.pdk;

import mj.net.handler.MessageHandler;
import mj.net.message.room.pdk.JoinPdkRoomReady;
import mj.net.message.room.pdk.JoinPdkRoomReadyDone;

public interface JoinPdkRoomReadyDoneHandler<U> extends MessageHandler<JoinPdkRoomReadyDone, U>{

	@Override
	public boolean handler(JoinPdkRoomReadyDone msg, U user) ;

}
