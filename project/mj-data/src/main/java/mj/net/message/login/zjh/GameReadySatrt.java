package mj.net.message.login.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class GameReadySatrt extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 5;
	
	
	public GameReadySatrt(){
		
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
	}

	
	@Override
	public String toString() {
		return "GameReadySatrt [ ]";
	}
	
	@Override
	public  int getMessageType() {
		return TYPE;
	}

	@Override
	public  int getMessageId() {
		return ID;
	}
}
