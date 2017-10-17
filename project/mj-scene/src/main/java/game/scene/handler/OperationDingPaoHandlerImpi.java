package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.game.OperationDingPaoHandler;
import mj.net.message.game.OperationDingPao;

/**
 * @Package:game.scene.handler
 * @ClassName: OperationDingPaoHandlerImpi
 * @Description: TODO
 * @author XuKaituo
 * @date May 17, 2017  3:00:30 PM
 */
@Component
public class OperationDingPaoHandlerImpi implements OperationDingPaoHandler<SceneUser> {

	@Override
	public boolean handler(OperationDingPao msg, SceneUser user) {
		// TODO Auto-generated method stub
//		int paoCount = msg.getPaoCount();
//		int userId = msg.getUserId();
//		user.getRoom().sendMessage(new OperationDingPaoRet(userId, paoCount));
		 user.getRoom().dingPaoRet(user, msg);
		return false;
	}

}
