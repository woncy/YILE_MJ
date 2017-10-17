package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
    * @ClassName: AliPayRet
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
    * @date 2017年6月17日
    *
    */

public class AliPayRet extends AbstractMessage {
	public static final int TYPE = 7;
	public static final int ID = 31;
	private String body;
	
	
	/**
	 * 创建一个新的实例 AliPayRet.
	 *
	 */

	public AliPayRet() {
	}
	   
   public AliPayRet(String body) {
		super();
		this.body = body;
	}
   
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.body = in.readString();
	}

	
	 


	    
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(body);
	}

	
	    
	@Override
	public int getMessageId() {
		return ID;
	}

	
	@Override
	public int getMessageType() {
		return TYPE;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "AliPayRet [body=" + body + "]";
	}
	
	

}

