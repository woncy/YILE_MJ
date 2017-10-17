package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * @author 13323659953@163.com
 *
 */
public class ZJHHistory extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 27;
	
	public ZJHHistory() {
	
	}
	
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
		return "ZJHHistory []";
	}
	
}
