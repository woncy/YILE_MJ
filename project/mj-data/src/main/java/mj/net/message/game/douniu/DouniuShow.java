package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 前段发送给客户端计算类型
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 */
public class DouniuShow extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 37; 

	
	public DouniuShow(){
		
	}
	
	public DouniuShow(int index){
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
	}
	
	@Override
	public String toString() {
		return "DouniuShow []";
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
