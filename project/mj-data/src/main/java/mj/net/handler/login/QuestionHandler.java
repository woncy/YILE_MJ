package mj.net.handler.login;

import mj.net.handler.MessageHandler;
import mj.net.message.login.Question;

public interface QuestionHandler<U> extends MessageHandler<Question, U>{
	@Override
	public boolean handler(Question msg, U user);

}
