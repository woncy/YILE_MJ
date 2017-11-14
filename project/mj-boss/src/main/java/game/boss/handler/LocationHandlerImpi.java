package game.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.UserService;
import mj.net.handler.login.LocationHandler;
import mj.net.message.login.Location;

@Component
public class LocationHandlerImpi implements LocationHandler<User> {
	@Autowired
	UserService userService;
	@Override
	public boolean handler(Location msg, User user) {
		userService.updatLoction(msg, user);
		return false;
	}

}
