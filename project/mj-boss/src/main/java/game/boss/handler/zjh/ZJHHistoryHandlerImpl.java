package game.boss.handler.zjh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.isnowfox.core.net.message.Message;

import game.boss.model.User;
import game.boss.services.ZJH.ZJHRoomService;
import mj.net.handler.login.zjh.ZJHHistoryHandler;
import mj.net.message.game.zjh.ZJHHistory;
@Component
public class ZJHHistoryHandlerImpl implements ZJHHistoryHandler<User>{
	@Autowired
	private ZJHRoomService zjhRoomService;

	@Override
	public boolean handler(ZJHHistory msg, User user) {
		zjhRoomService.getHistoryList(user);
		return false;
	}


}
