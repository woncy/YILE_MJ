package game.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.RoomService;
import mj.net.handler.login.QueryProxyRoomListHandler;
import mj.net.message.login.QueryProxyRoomList;
@Component
public class QueryProxyRoomListHandlerImpi implements QueryProxyRoomListHandler<User>{
	@Autowired
	private RoomService roomService;
	@Override
	public boolean handler(QueryProxyRoomList msg, User user) {
		roomService.queryProxyRoom(user);
		return false;
	}

}
