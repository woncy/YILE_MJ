package mj.net.message.login.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class JoinZJHRoom extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 3;
	
	private String roomCheckId;
	
	public JoinZJHRoom(){
		
	}
	
	public JoinZJHRoom(String roomCheckId){
		this.roomCheckId = roomCheckId;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		roomCheckId = in.readString();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(getRoomCheckId());
	}

	public String getRoomCheckId() {
		return roomCheckId;
	}
	
	public void setRoomCheckId(String roomCheckId) {
		this.roomCheckId = roomCheckId;
	}

	
	@Override
	public String toString() {
		return "JoinRoom [roomCheckId=" + roomCheckId + ", ]";
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
