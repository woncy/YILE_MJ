package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class ZJHOperation extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 10;
	//操作 0看牌 1 弃牌 3跟注
	private int opt;
	
	public ZJHOperation(){
		
	}
	
	public ZJHOperation(int opt) {
		super();
		this.opt = opt;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.opt = in.readInt();
		
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(getOpt());
		
	}
	
	


	public int getOpt() {
		return opt;
	}

	public void setOpt(int opt) {
		this.opt = opt;
	}

	@Override
	public int getMessageId() {
		
		return ID;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}

	@Override
	public String toString() {
		return "ZJHOperation [opt=" + opt + "]";
	}

}
