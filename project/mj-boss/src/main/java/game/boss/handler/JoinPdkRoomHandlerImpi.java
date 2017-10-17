package game.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.RoomService;
import game.boss.services.UserService;
import game.boss.services.pdk.PdkRoomService;
import mj.net.handler.login.pdk.JoinPdkRoomHandler;
import mj.net.message.room.pdk.JoinPdkRoom;

@Component
public class JoinPdkRoomHandlerImpi implements JoinPdkRoomHandler<User>{
	@Autowired
	PdkRoomService roomService;
	@Override
	public boolean handler(JoinPdkRoom msg, User user) {
		roomService.joinRoom(msg.getRoom_no(), user);
		return false;
	}

}
