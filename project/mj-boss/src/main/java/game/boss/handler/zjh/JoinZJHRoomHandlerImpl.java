package game.boss.handler.zjh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.ZJH.ZJHRoomService;
import mj.net.handler.login.zjh.JoinZJHRoomHandler;
import mj.net.message.login.zjh.JoinZJHRoom;

@Component
public class JoinZJHRoomHandlerImpl implements JoinZJHRoomHandler<User>{
 @Autowired
 private ZJHRoomService ZJHRoomService;

	@Override
	public boolean handler(JoinZJHRoom msg, User user) {
		ZJHRoomService.joinZJHroom(msg.getRoomCheckId(),user);
		return false;
	}


}
