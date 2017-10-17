package mj.net.message.login.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 创建扎金花房间结果
 * @author 13323659953@163.com
 *
 */
public class CreateZJHRoomResult extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 1;
	
	private boolean result;
	private String roomCheckId;
	
	public CreateZJHRoomResult(){
		
	}
	
	public CreateZJHRoomResult(boolean result, String roomCheckId){
		this.result = result;
		this.roomCheckId = roomCheckId;
	}
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		result = in.readBoolean();
		roomCheckId = in.readString();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeBoolean(getResult());
		out.writeString(getRoomCheckId());
	}

	public boolean getResult() {
		return result;
	}
	
	public void setResult(boolean result) {
		this.result = result;
	}

	public String getRoomCheckId() {
		return roomCheckId;
	}
	
	public void setRoomCheckId(String roomCheckId) {
		this.roomCheckId = roomCheckId;
	}

	
	@Override
	public String toString() {
		return "CreateRoomRet [result=" + result + ",roomCheckId=" + roomCheckId + ", ]";
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
