package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 比牌
 * @author 13323659953@163.com
 *
 */
public class ComparePoker extends  AbstractMessage{
	public static final int TYPE = 3;
	public static final int ID = 15;
	
	private int Index;
	
	public ComparePoker() {
		// TODO Auto-generated constructor stub
	}
	
	public ComparePoker(int index) {
		super();
		Index = index;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.Index = in.readInt();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(getIndex());
	}

	public int getIndex() {
		return Index;
	}

	public void setIndex(int index) {
		Index = index;
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
}
