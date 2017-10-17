package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * pong
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class Pong extends AbstractMessage{
	public static final int TYPE			 = 7;
	public static final int ID				 = 15;
	
	private String time;
//	private int test;
	
	public Pong(){
		
	}
	
	public Pong(String time){
		this.time = time;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		time = in.readString();
//		this.test = in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(getTime());
//		out.writeInt(test);
	}

//	public int getTest() {
////		return test;
//	}
//
//	public void setTest(int test) {
//		this.test = test;
//	}

	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}

	
	@Override
	public String toString() {
		return "Pong [time=" + time + ", ]";
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
