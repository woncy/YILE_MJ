package game.boss.handler.zjh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.ZJH.ZJHRoomService;
import mj.net.handler.login.zjh.SelectEveryHistoryHandler;
import mj.net.message.login.zjh.SelectEveryHistory;
@Component
public class SelectEveryHistoryHandlerImpl implements SelectEveryHistoryHandler<User>{
@Autowired
private ZJHRoomService zjhRoomService;
	@Override
	public boolean handler(SelectEveryHistory msg, User user) {
		zjhRoomService.getEveryHistory(user, msg.getRoomCheckId());
		return false;
	}

}
