package mj.net.handler.login.pdk;

import mj.net.handler.MessageHandler;
import mj.net.message.room.pdk.JoinPdkRoom;

public interface JoinPdkRoomHandler<U> extends MessageHandler<JoinPdkRoom, U>{

	@Override
	public boolean handler(JoinPdkRoom msg, U user) ;
	

}
