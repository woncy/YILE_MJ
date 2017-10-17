package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * Transfer
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class TransferRet extends AbstractMessage{
	public static final int TYPE			 = 7;
	public static final int ID				 = 26;
	
	private boolean result;
	private String info;
	
	
	public boolean geteResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public TransferRet(){
		
	}
	
	public TransferRet(boolean result,String info){
		this.result = result;
		this.info = info;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		result = in.readBoolean();
		info = in.readString();
		
	}
	

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeBoolean(result);
		out.writeString(info);
	}
	
	@Override
	public String toString() {
		return "TransferRet [result = "+  result +"]";
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
