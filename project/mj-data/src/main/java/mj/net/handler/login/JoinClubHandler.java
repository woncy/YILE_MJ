package mj.net.handler.login;

import mj.net.handler.MessageHandler;
import mj.net.message.login.JoinClub;

public interface JoinClubHandler<U> extends MessageHandler<JoinClub, U>{

	@Override
	public boolean handler(JoinClub joinClub,U u);
}
