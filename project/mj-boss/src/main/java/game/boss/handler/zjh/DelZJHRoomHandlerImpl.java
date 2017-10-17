package game.boss.handler.zjh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.ZJH.ZJHRoomService;
import mj.net.handler.login.zjh.DelZJHRoomHandler;
import mj.net.message.login.zjh.DelZJHRoom;
@Component
public class DelZJHRoomHandlerImpl implements DelZJHRoomHandler<User>{
@Autowired
private ZJHRoomService zjhRoomService;
	@Override
	public boolean handler(DelZJHRoom msg, User user) {
		zjhRoomService.delRoom(user.getUserId(), user, false);
		return false;
	}

}
