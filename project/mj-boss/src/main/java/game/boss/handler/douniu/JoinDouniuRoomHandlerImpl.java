package game.boss.handler.douniu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import game.boss.model.User;
import game.boss.services.douniu.DouniuRoomService;
import mj.net.handler.login.douniu.JoinDouniuRoomHandler;
import mj.net.message.login.douniu.JoinDouniuRoom;

@Component
public class JoinDouniuRoomHandlerImpl implements JoinDouniuRoomHandler<User>{
	
	@Autowired
	private DouniuRoomService douniuRoomService;

	@Override
	public boolean handler(JoinDouniuRoom msg, User user) {
		douniuRoomService.joinDouniuroom(msg.getRoomCheckId(), user);
		return false;
	}


}
