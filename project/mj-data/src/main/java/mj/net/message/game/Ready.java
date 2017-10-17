package mj.net.message.game;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 告诉客户端谁准备了
 * @author Administrator
 *
 */
public class Ready extends AbstractMessage{

	private static final int TYPE = 1;
	private static final int ID = 36;
	private int index;
	
	

	public Ready() {
		super();
	}

	public Ready(int index) {
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
	public int getMessageId() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public int getMessageType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "Ready [index=" + index + "]";
	}
	
	
	
}
