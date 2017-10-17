package game.boss.handler.pdk;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.pdk.PdkRoomService;
import mj.net.handler.login.pdk.CreatePdkRoomHandler;
import mj.net.message.room.pdk.CreatePdkRoom;

@Component
public class CreatePdkRoomHandlerImpl implements CreatePdkRoomHandler<User>{
	
	@Autowired
	PdkRoomService pdkRoomService;
	
	@Override
	public boolean handler(CreatePdkRoom msg, User user) {
		pdkRoomService.createRoom(msg, user);
		return false;
	}


}
