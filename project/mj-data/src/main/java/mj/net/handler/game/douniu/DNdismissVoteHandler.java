package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DNDismissVote;

public interface DNdismissVoteHandler<U> extends MessageHandler<DNDismissVote, U> {

	@Override
	public boolean handler(DNDismissVote msg, U user);
}
