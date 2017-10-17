package game.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.UserService;
import mj.net.handler.login.QuestionHandler;
import mj.net.message.login.Question;

@Component
public class QuestionHandlerImpi implements QuestionHandler<User>{

	@Autowired
	UserService userService;
	@Override
	public boolean handler(Question msg, User user) {
		userService.addQuestion(msg,user);
		return false;
	}

}
