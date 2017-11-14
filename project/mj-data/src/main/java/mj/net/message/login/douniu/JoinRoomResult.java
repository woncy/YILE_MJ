package mj.net.message.login.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 进入房间结果信息
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class JoinRoomResult extends AbstractMessage{
	public static final int TYPE			 = 2;
	public static final int ID				 = 3;
	
	private boolean result;
	private String reason;//DN //MJ
	
	
	
	public JoinRoomResult() {
		super();
	}



	public JoinRoomResult(boolean result){
		this.result = result;
		this.reason = "";
	}
	
	
	
	public JoinRoomResult(boolean result, String reason) {
		super();
		this.result = result;
		this.reason = reason;
	}

	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		result = in.readBoolean();
		this.reason = in.readString();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeBoolean(this.result);
		out.writeString(this.reason);
	}

	public boolean getResult() {
		return result;
	}
	
	public void setResult(boolean result) {
		this.result = result;
	}
	
	

	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "JoinDouniuRoomResult [result=" + result + ", ]";
	}
	
	@Override
	public final int getMessageType() {
		return TYPE;
	}

	@Override
	public final int getMessageId() {
		return ID;
	}
}
