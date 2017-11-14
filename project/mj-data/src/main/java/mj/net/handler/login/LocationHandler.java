package mj.net.handler.login;

import mj.net.handler.MessageHandler;
import mj.net.message.login.Location;

public interface LocationHandler<U> extends MessageHandler<Location, U> {
	@Override
	public boolean handler(Location msg, U user);
	
}
