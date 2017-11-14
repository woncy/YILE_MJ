package game.boss.handler.dn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.RoomService;
import mj.net.handler.login.douniu.JoinDouniuRoomHandler;
import mj.net.message.login.douniu.JoinDouniuRoom;
@Component
public class JoinDouNiuRoomHandlerImpi implements JoinDouniuRoomHandler<User>{
	@Autowired
	RoomService roomService;
	@Override
	public boolean handler(JoinDouniuRoom msg, User user) {
		roomService.joinRoom2(msg.getRoomCheckId(),user);
		return false;
	}

}
