package game.scene.douniu.handler;

import game.scene.douniu.room.DouniuSceneUser;
import mj.net.handler.game.ChatHandler;
import mj.net.handler.game.douniu.DouniuChatHandler;
import mj.net.message.game.Chat;
import mj.net.message.game.douniu.DouniuChat;

/**
    * @ClassName: ChatHandlerImpl
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
    * @date 2017年6月22日
    *
    */

public class DouniuChatHandlerImpl implements DouniuChatHandler<DouniuSceneUser> {

	
	@Override
	public boolean handler(DouniuChat msg, DouniuSceneUser user) {
		user.getRoom().chatSendToAllUser(msg, user);
		return false;
	}

}

