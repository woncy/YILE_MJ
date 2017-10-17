package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class RoomInfoPoker extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 9;
	
	private int[] indexs;
	private int diFen;
	public RoomInfoPoker() {
		// TODO Auto-generated constructor stub
	}
	
	public RoomInfoPoker(int[] indexs, int diFen) {
		super();
		this.indexs = indexs;
		this.diFen = diFen;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.indexs = in.readIntArray();
		this.diFen = in.readInt();
		
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeIntArray(getIndexs());
		out.writeInt(getDiFen());
	}
	
	public int[] getIndexs() {
		return indexs;
	}
	public void setIndexs(int[] indexs) {
		this.indexs = indexs;
	}
	public int getDiFen() {
		return diFen;
	}
	public void setDiFen(int diFen) {
		this.diFen = diFen;
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
