package game.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.UserService;
import mj.net.handler.login.JoinClubHandler;
import mj.net.message.login.JoinClub;

@Component
public class JoinClubHandlerImpi implements JoinClubHandler<User> {
	@Autowired
	UserService userService;
	
	@Override
	public boolean handler(JoinClub joinClub, User user) {
		userService.joinClub(joinClub,user);
		return false;
	}

}
