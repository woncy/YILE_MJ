package game.scene.pdk.handler;


import org.springframework.stereotype.Component;

import game.scene.pdk.room.PdkSceneUser;
import mj.net.handler.game.pdk.PdkStartGameHandler;
import mj.net.message.game.pdk.PdkStartGame;

@Component
public class PdkStartGameHandlerImpi implements PdkStartGameHandler<PdkSceneUser>{

	@Override
	public boolean handler(PdkStartGame msg, PdkSceneUser user) {
		user.getRoom().chapterStart(user);
		return false;
	}

}
