package game.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.UserService;
import mj.net.handler.login.TransferHandler;
import mj.net.message.login.Transfer;

/**
 * @author zuoge85@gmail.com on 16/10/3.
 */
@Component
public class TransferHandlerImpi implements TransferHandler<User> {
    
	
	@Autowired
    private UserService userService;
	
	@Override
    public boolean handler(Transfer msg, User user) {
        /*
         * 对数据库进行操作。
         */
		userService.treasferGold(user, msg);
        return false;
    }
}
