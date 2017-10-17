package mj.net.handler.login.pdk;

import mj.net.handler.MessageHandler;
import mj.net.message.room.pdk.CreatePdkRoom;


public interface CreatePdkRoomHandler<U> extends MessageHandler<CreatePdkRoom,U>{
	@Override
	public boolean handler(CreatePdkRoom msg, U user);

}
