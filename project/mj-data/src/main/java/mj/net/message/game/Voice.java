package mj.net.message.game;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class Voice extends AbstractMessage{

	private static final int TYPE = 1;
	private static final int ID = 36;
	private int locationIndex;
	private byte[] voice;

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.locationIndex = in.readInt();
		voice = in.readBytes();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(locationIndex);
		out.writeBytes(voice);
	}

	@Override
	public int getMessageId() {
		return ID;
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}

}
