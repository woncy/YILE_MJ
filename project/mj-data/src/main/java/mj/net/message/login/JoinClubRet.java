package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class JoinClubRet extends AbstractMessage{
	public static final int TYPE=7;
	public static final int ID=34;
	
	private boolean result;
	private int reasonType;	//当result=false时才会发此消息 -1代表服务器错误，让客户重试一次，0代表没有此俱乐部
	
	public JoinClubRet() {
		super();
	}
	public JoinClubRet(boolean result, int reasonType) {
		this.result = result;
		this.reasonType = reasonType;
	}
	public JoinClubRet(boolean result) {
		this.result = result;
	}
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		result = in.readBoolean();
		if(!result)
			this.reasonType = in.readInt();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeBoolean(result);
		if(!result){
			out.writeInt(reasonType);
		}
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
		return "JoinClubRet [result=" + result + ", reasonType=" + reasonType + "]";
	}
	
}
