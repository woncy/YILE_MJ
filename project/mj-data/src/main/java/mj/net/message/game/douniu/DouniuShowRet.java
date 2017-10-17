package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 *  客户端发送给前段牌的类型
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 */
public class DouniuShowRet extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 38; 
	/**
	 * 玩家类型
	 */
	private String result;
	
	public DouniuShowRet(){
		
	}
    	
	public DouniuShowRet(String result) {
		super();
		this.result = result;
	}

	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		result = in.readString();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(getResult());;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	
	
	@Override
	public String toString() {
		return "DouniuShowRet [result=" + result + "]";
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
