package mj.net.message.game.pdk;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 
    * @ClassName: PdkChuPaiReady
    * @Description:  告诉客户端该谁出牌了
    * @author 13323659953@163.com
    * @date 2017年7月11日
    *
 */
public class PdkChuPaiReady extends AbstractMessage{
	public static final int TYPE = 4;
	public static final int ID = 11;
	private int chuPaiUserIndex;
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.chuPaiUserIndex = in.readInt();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(chuPaiUserIndex);
	}
	@Override
	public int getMessageId() {
		return ID;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}
	public int getChuPaiUserIndex() {
		return chuPaiUserIndex;
	}
	public void setChuPaiUserIndex(int chuPaiUserIndex) {
		this.chuPaiUserIndex = chuPaiUserIndex;
	}
	
	
}
