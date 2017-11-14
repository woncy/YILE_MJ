package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class DNDismissUserResult extends AbstractMessage{
	private static final int TYPE = 2;
	private static final int ID = 19;
	private int index;
	private boolean result;
	
	public DNDismissUserResult() {
		super();
	}

	public DNDismissUserResult(int index) {
		super();
		this.index = index;
	}
	


	public DNDismissUserResult(int index, boolean result) {
		super();
		this.index = index;
		this.result = result;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.index = in.readInt();
		this.result = in.readBoolean();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(index);
		out.writeBoolean(this.result);
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
		return "DNDismissUserResult [index=" + index + ", result=" + result + "]";
	}


	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}
	

}
