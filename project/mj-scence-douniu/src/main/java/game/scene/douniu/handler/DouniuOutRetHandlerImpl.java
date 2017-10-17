package game.scene.douniu.handler;

import game.scene.douniu.room.DouniuSceneUser;
import mj.net.handler.game.douniu.DouniuOutRetHandler;
import mj.net.message.game.douniu.DouniuOutRet;
import org.springframework.stereotype.Component;

/**
 * @author zuoge85@gmail.com on 2016/10/31.
 */
@Component
public class DouniuOutRetHandlerImpl implements DouniuOutRetHandler<DouniuSceneUser> {
    @Override
    public boolean handler(DouniuOutRet msg, DouniuSceneUser user) {
        user.getRoom().outRet(user, msg);
        return false;
    }
}
