package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class AddZhuResult  extends  AbstractMessage{
	public static final int TYPE = 3;
	public static final int ID = 30;
	
	private int index;
	public AddZhuResult() {
	}
	
	public AddZhuResult(int index) {
		super();
		this.index = index;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.index = in.readInt();
		
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(getIndex());
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}
	@Override
	public int getMessageId() {
		return ID;
	}

	@Override
	public String toString() {
		return "AddZhuResult [index=" + index + "]";
	}

}
