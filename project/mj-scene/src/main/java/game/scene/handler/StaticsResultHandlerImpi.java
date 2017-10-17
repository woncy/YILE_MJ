package game.scene.handler;

import game.scene.room.SceneUser;
import mj.net.handler.game.StaticsResultHandler;
import mj.net.message.game.StaticsResult;

import org.springframework.stereotype.Component;

/**
 * @author zuoge85@gmail.com on 2016/10/24.
 */
@Component
public class StaticsResultHandlerImpi implements StaticsResultHandler<SceneUser> {
    @Override
    public boolean handler(StaticsResult msg, SceneUser user) {
//        user.getRoom().sendStaticsResultToAllUser();
        return false;
    }
}
