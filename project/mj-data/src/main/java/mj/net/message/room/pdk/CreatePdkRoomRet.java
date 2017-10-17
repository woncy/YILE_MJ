
package mj.net.message.room.pdk;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 
    * @ClassName: CreatePdkRoomRet
    * @Description: 创建房间的消息返回
    * @author 13323659953@163.com
    * @date 2017年6月30日
    *
 */
public class CreatePdkRoomRet extends AbstractMessage{
	
	public static final int TYPE=4;
	public static final int ID=1;
	
	private boolean result;
	private String roomCheckId;
	
	public CreatePdkRoomRet() {
		super();
	}
	
	public CreatePdkRoomRet(boolean result, String roomCheckId) {
		super();
		this.result = result;
		this.roomCheckId = roomCheckId;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.result = in.readBoolean();
		this.roomCheckId = in.readString();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeBoolean(result);
		out.writeString(this.roomCheckId);
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}
	public int getMessageId() {
		return ID;
	}

	public boolean isResult() {
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
		return "CreatePdkRoomRet [result=" + result + ", roomCheckId=" + roomCheckId + "]";
	}
	
	

}
