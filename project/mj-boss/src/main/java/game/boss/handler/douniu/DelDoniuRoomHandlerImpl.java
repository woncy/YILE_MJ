package game.boss.handler.douniu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.douniu.DouniuRoomService;
import mj.net.handler.login.douniu.DeDouniuRoomHandler;
import mj.net.message.login.douniu.DeDouniuRoom;
@Component
public class DelDoniuRoomHandlerImpl implements DeDouniuRoomHandler<User>{
@Autowired
private DouniuRoomService douniuRoomService;
	@Override
	public boolean handler(DeDouniuRoom msg, User user) {
		douniuRoomService.delRoom(user.getUserId(), user, false);
		return false;
	}

}
