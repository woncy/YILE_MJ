package game.boss.handler;

import game.boss.services.RoomService;
import game.boss.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import mj.net.handler.login.WangbangPlayBackHandler;
import mj.net.message.login.WangbangPlayBack;

@Component
public class WangbangPlayBackHandlerImpi implements WangbangPlayBackHandler<User>{

	  @Autowired
	  private RoomService roomService;
	
	  @Override
	public boolean handler(WangbangPlayBack msg, User user) {
		 roomService.playBack(user,msg.getCheckRoomId(), msg.getChapterIndex());
		return false;
	}

}
