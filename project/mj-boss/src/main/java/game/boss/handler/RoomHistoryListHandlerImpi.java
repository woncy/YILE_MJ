package game.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.RoomService;
import mj.net.handler.login.RoomHistoryListHandler;
import mj.net.message.login.RoomHistoryList;

/**
 * @author zuoge85@gmail.com on 2017/1/3.
 */
@Component
public class RoomHistoryListHandlerImpi implements RoomHistoryListHandler<User> {
    @Autowired
    private RoomService roomService;

    @Override
    public boolean handler(RoomHistoryList msg, User user) {
        roomService.requestHistory(user);
        return false;
    }
}
