package game.boss.handler.douniu;

import game.boss.model.User;
import game.boss.services.douniu.DouniuRoomService;
import mj.net.handler.login.douniu.ExitDouniuRoomHandler;
import mj.net.message.login.douniu.ExitDouniuRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zuoge85@gmail.com on 2016/10/23.
 */
@Component
public class ExitDouniuRoomHandlerImpl implements ExitDouniuRoomHandler<User> {
    @Autowired
    private DouniuRoomService douniuRoomService;

    @Override
    public boolean handler(ExitDouniuRoom msg, User user) {
    	douniuRoomService.exitDouniuRoom( msg, user);
        return false;
    }
}
