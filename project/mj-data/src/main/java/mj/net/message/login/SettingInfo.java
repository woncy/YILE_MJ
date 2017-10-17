package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * Transfer
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class SettingInfo extends AbstractMessage{
	public static final int TYPE			 = 1;
	public static final int ID				 = 26;
	
	String roomId  ; 
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public SettingInfo(){
		
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		roomId = in.readString();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(roomId);
	}
	
	@Override
	public String toString() {
		return "getSetting [roomId = " + roomId  + "]";
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
