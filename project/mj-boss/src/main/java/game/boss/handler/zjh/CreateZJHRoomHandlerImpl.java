package game.boss.handler.zjh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.ZJH.ZJHRoomService;
import mj.net.handler.login.zjh.CreateZJHRoomHandler;
import mj.net.message.login.zjh.CreateZJHRoom;

@Component
public class CreateZJHRoomHandlerImpl  implements CreateZJHRoomHandler<User>{
	@Autowired
    private ZJHRoomService ZJHroomService;
	@Override
	public boolean handler(CreateZJHRoom msg, User user) {
		ZJHroomService.createRoom(msg, user);
		return false;
	}

}
