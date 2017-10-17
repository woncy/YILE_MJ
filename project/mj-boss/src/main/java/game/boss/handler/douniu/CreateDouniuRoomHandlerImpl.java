package game.boss.handler.douniu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import game.boss.model.User;
import game.boss.services.douniu.DouniuRoomService;
import mj.net.handler.login.douniu.CreateDouniuRoomHandler;
import mj.net.message.login.douniu.CreateDouniuRoom;

@Component
public class CreateDouniuRoomHandlerImpl implements CreateDouniuRoomHandler<User> {

	@Autowired
	private DouniuRoomService douniuRoomService;
	@Override
	public boolean handler(CreateDouniuRoom msg, User user) {
		douniuRoomService.createRoom(msg, user);
		return false;
	}


}
