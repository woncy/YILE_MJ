package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
* @ClassName: DNShowXiaZhu
* @Description: TODO(通知客户端显示下注界面)
* @author 13323659953@163.com   
* @date 2017年11月3日 下午2:53:38
*
 */
public class DNShowXiaZhu extends AbstractMessage{

	private static final int TYPE = 2;
	private static final int ID = 14;
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
		return "DNShowXiaZhu []";
	}
	

}
