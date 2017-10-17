package mj.net.message.login.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 
    * @ClassName: JoinPdkRoomReady
    * @Description: 加入房间成功后准备此消息
    * @author 13323659953@163.com
    * @date 2017年7月7日
    *
 */
public class JoinDouniuRoomReady extends AbstractMessage{
	public static final int TYPE=5;
	public static final int ID=4;
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
		return "JoinPdkRoomReady [getMessageId()=" + getMessageId() + ", getMessageType()=" + getMessageType() + "]";
	}
	
	
}
