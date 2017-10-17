package game.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.UserService;
import mj.net.handler.login.LoginHandler;
import mj.net.message.login.Login;

/**
 * @author zuoge85@gmail.com on 16/9/27.
 */
@Component
public class LoginHandlerImpi implements LoginHandler<User> {
    @Autowired
    private UserService userService;

    @Override
    public boolean handler(Login msg, User user) {
        userService.loginByOpenId(msg, user);
        return false;
    }
}
