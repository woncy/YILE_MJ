package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
    * @ClassName: WXShow
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
    * @date 2017年6月19日
    *
    */

public class WXShow extends AbstractMessage{

	public static final int TYPE = 7 ;
	public static final int ID = 32 ;
	
	    
	@Override
	public void decode(Input arg0) throws IOException, ProtocolException {
	}
	
	   
	    
	@Override
	public void encode(Output arg0) throws IOException, ProtocolException {
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
		return "WXShow [getMessageId()=" + getMessageId() + ", getMessageType()=" + getMessageType() + "]";
	}
	
	
	
	
	
	
	
	
	
	
}

