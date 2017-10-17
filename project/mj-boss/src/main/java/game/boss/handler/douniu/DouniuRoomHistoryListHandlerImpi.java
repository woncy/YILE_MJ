package game.boss.handler.douniu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.douniu.DouniuRoomService;
import mj.net.handler.login.douniu.DouniuRoomHistoryListHandler;
import mj.net.message.login.douniu.DouniuRoomHistoryList;

/**
 * @author zuoge85@gmail.com on 2017/1/3.
 */
@Component
public class DouniuRoomHistoryListHandlerImpi implements DouniuRoomHistoryListHandler<User> {
    @Autowired
    private DouniuRoomService douniuRoomService;

    @Override
    public boolean handler(DouniuRoomHistoryList msg, User user) {
    	douniuRoomService.requestHistory(user);
        return false;
    }
}
