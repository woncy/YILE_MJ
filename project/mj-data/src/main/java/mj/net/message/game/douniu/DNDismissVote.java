package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class DNDismissVote extends AbstractMessage{
	private static final int TYPE = 2;
	private static final int ID = 18;
	private int index;
	public DNDismissVote() {
		super();
	}

	public DNDismissVote(int index) {
		super();
		this.index = index;
	}
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.index = in.readInt();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(index);
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}
	@Override
	public int getMessageId() {
		return ID;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "DNDismissVote [index=" + index + "]";
	}
	

}
