package mj.net.message.room.pdk;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class JoinPdkRoomRet extends AbstractMessage{
	public static final int TYPE = 4;
	public static final int ID = 7;
	boolean result;
	
	
	
	public JoinPdkRoomRet() {
		super();
	}
	

	public JoinPdkRoomRet(boolean result) {
		super();
		this.result = result;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.result = in.readBoolean();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeBoolean(result);
	}

	@Override
	public int getMessageId() {
		return ID;
	}

	@Override
	public int getMessageType() {
		// TODO 自动生成的方法存根
		return TYPE;
	}


	public boolean isResult() {
		return result;
	}


	public void setResult(boolean result) {
		this.result = result;
	}


	@Override
	public String toString() {
		return "JoinPdkRoomRet [result=" + result + "]";
	}
	
	
	
}
