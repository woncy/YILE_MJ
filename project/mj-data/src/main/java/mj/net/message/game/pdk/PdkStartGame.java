package mj.net.message.game.pdk;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 
* @ClassName: PdkStartGame
* @Description: 开始游戏消息
* @author 13323659953@163.com
* @date 2017年7月10日
*
 */
public class PdkStartGame extends AbstractMessage{
	public static final int TYPE = 4;
	public static final int ID = 10;
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
	
	
}
