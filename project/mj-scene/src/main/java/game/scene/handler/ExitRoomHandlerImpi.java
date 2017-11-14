package game.scene.handler;

import org.springframework.stereotype.Component;

import game.scene.room.SceneUser;
import mj.net.handler.login.ExitRoomHandler;
import mj.net.message.login.ExitRoom;

/**
 * @author zuoge85@gmail.com on 2016/10/23.
 */
@Component
public class ExitRoomHandlerImpi implements ExitRoomHandler<SceneUser> {

    @Override
    public boolean handler(ExitRoom msg, SceneUser user) {
        user.getRoom().exitRoom(user);
        return false;
    }
}
