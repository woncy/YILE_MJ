package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class DNKaiPai extends AbstractMessage {

	private static final int TYPe = 2;
	private static final int ID = 12;
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		
	}

	@Override
	public int getMessageId() {
		return ID;
	}

	@Override
	public int getMessageType() {
		return TYPe;
	}

}
