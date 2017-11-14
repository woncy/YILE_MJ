package mj.net.message.game;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class ShowStartGame extends AbstractMessage{

	private static final int TYPE = 1;
	private static final int ID = 37;

	@Override
	public void decode(Input arg0) throws IOException, ProtocolException {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void encode(Output arg0) throws IOException, ProtocolException {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public int getMessageId() {
		// TODO 自动生成的方法存根
		return ID;
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}

}
