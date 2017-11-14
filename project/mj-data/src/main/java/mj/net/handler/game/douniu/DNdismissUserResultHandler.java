package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DNDismissUserResult;

public interface DNdismissUserResultHandler<U> extends MessageHandler<DNDismissUserResult, U> {

	@Override
	public boolean handler(DNDismissUserResult msg, U user);
}
