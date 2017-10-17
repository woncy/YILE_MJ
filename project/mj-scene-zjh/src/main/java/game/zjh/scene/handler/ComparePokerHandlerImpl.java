package game.zjh.scene.handler;

import org.springframework.stereotype.Component;

import game.zjh.scene.room.ZJHSceneUser;
import mj.net.handler.login.zjh.ComparePokerHandler;
import mj.net.message.game.zjh.ComparePoker;
@Component
public class ComparePokerHandlerImpl implements ComparePokerHandler<ZJHSceneUser>{

	@Override
	public boolean handler(ComparePoker msg, ZJHSceneUser user) {
		user.getRoom().comparPoker(msg, user);
		return false;
	}

}
