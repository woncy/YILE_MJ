package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.douniu.DNQiangZhuangHandler;
import mj.net.message.game.douniu.DNQiangZhuang;

@Component
public class DNQiangZhuangHandlerImpi implements DNQiangZhuangHandler<SceneUser>{

	@Override
	public boolean handler(DNQiangZhuang msg, SceneUser user) {
		user.getRoom().qiangzhuang(msg,user);
		return false;
	}

}
