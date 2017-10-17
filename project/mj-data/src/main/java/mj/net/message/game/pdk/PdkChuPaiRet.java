package mj.net.message.game.pdk;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 
    * @ClassName: PdkChuPaiRet
    * @Description: 出牌结果通知
    * @author 13323659953@163.com
    * @date 2017年7月11日
    *
 */
public class PdkChuPaiRet extends AbstractMessage{
	public static final int TYPE = 4;
	public static final int ID = 13;
	private int type;// 表示返回的结果  0表示出牌失败   1表示出牌成功
	private String key;	//当type=0时,  此属性值，代表错误提示
	
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.type = in.readInt();
		this.key = in.readString();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(type);
		out.writeString(key);
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
