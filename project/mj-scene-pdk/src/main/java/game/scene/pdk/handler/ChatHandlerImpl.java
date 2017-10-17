package game.scene.pdk.handler;

import game.scene.pdk.room.PdkSceneUser;
import mj.net.handler.game.ChatHandler;
import mj.net.message.game.Chat;

/**
    * @ClassName: ChatHandlerImpl
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
    * @date 2017年6月22日
    *
    */

public class ChatHandlerImpl implements ChatHandler<PdkSceneUser> {

	
	@Override
	public boolean handler(Chat msg, PdkSceneUser user) {
		user.getRoom().chatSendToAllUser(msg, user);
		return false;
	}

}

