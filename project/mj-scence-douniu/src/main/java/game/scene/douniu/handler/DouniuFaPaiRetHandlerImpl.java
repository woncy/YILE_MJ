package game.scene.douniu.handler;

import game.scene.douniu.room.DouniuSceneUser;
import mj.net.handler.game.douniu.DouniuFaPaiRetHandler;
import mj.net.message.game.douniu.DouniuFaPaiRet;

import org.springframework.stereotype.Component;

/**
 * @author zuoge85@gmail.com on 2016/10/31.
 */
@Component
public class DouniuFaPaiRetHandlerImpl implements DouniuFaPaiRetHandler<DouniuSceneUser> {
    @Override
    public boolean handler(DouniuFaPaiRet msg, DouniuSceneUser user) {
        user.getRoom().faPaiRet(user, msg);
        return false;
    }

	

	
}
