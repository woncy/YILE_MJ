package game.scene.douniu.handler;

import game.scene.douniu.room.DouniuSceneUser;
import mj.net.handler.game.douniu.GameJoinDouniuRoomHandler;
import mj.net.message.game.douniu.GameJoinDouniuRoom;
import org.springframework.stereotype.Component;

/**
 * @author zuoge85@gmail.com on 16/10/18.
 */
@Component
public class JoinDouniuGameHandlerImpl implements GameJoinDouniuRoomHandler<DouniuSceneUser> {
    @Override
    public boolean handler(GameJoinDouniuRoom msg, DouniuSceneUser user) {
    	user.getRoom().joinGame(user);
        return false;
    }
}
