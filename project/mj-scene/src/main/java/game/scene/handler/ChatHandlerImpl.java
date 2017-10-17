package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.ChatHandler;
import mj.net.message.game.Chat;

/**
    * @ClassName: ChatHandlerImpl
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
    * @date 2017年6月22日
    *
    */
@Component
public class ChatHandlerImpl implements ChatHandler<SceneUser> {

	
	@Override
	public boolean handler(Chat msg, SceneUser user) {
		user.getRoom().chatSendToAllUser(msg, user);
		return false;
	}

}

