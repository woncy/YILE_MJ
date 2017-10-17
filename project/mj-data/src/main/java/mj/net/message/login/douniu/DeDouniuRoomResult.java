package mj.net.message.login.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 删除房间结果
 * @author 13323659953@163.com
 *
 */
public class DeDouniuRoomResult extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 45;
	
	private boolean result;
	
	public DeDouniuRoomResult() {
	}

	
	public DeDouniuRoomResult(boolean result) {
		super();
		this.result = result;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.result = in.readBoolean();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeBoolean(isResult());
	}

	public boolean isResult() {
		return result;
	}


	public void setResult(boolean result) {
		this.result = result;
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
