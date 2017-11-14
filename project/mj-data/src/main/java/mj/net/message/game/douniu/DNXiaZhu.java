package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class DNXiaZhu extends AbstractMessage {
	private static final int TYPE = 2;
	private static final int ID = 15;
	private int index ;
	private int zhu; 

	public DNXiaZhu() {
		super();
	}

	public DNXiaZhu(int index, int zhu) {
		super();
		this.index = index;
		this.zhu = zhu;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.index = in.readInt();
		this.zhu = in.readInt();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(index);
		out.writeInt(zhu);
	}

	@Override
	public int getMessageId() {
		return ID;
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getZhu() {
		return zhu;
	}

	public void setZhu(int zhu) {
		this.zhu = zhu;
	}

	@Override
	public String toString() {
		return "DNXiaZhu [index=" + index + ", zhu=" + zhu + "]";
	}
	
	

}
