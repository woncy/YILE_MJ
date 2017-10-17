package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class AddZhu extends  AbstractMessage{
	public static final int TYPE = 3;
	public static final int ID = 12;
	
	private int zhu;
	public AddZhu() {
		// TODO Auto-generated constructor stub
	}
	
	public AddZhu(int zhu) {
		super();
		this.zhu = zhu;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.zhu = in.readInt();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(getZhu());
	}
	
	public int getZhu() {
		return zhu;
	}

	public void setZhu(int zhu) {
		this.zhu = zhu;
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
		return "AddZhu [zhu=" + zhu + "]";
	}

}
