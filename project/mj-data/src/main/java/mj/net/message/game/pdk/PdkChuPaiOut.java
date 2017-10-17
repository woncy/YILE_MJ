package mj.net.message.game.pdk;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 
    * @ClassName: PdkChuPaiOut
    * @Description: 放弃出牌
    * @author 13323659953@163.com
    * @date 2017年7月11日
    *
 */
public class PdkChuPaiOut extends AbstractMessage{

	public static final int TYPE = 4;
	public static final int ID = 13;
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		
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
