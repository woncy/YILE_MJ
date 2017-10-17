package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class ProxyRoom extends AbstractMessage{

	private static final int TYPE = 7;
	private static final int ID = 41;
	private String roomNo;
	
	

	public ProxyRoom() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProxyRoom(String roomNo) {
		super();
		this.roomNo = roomNo; 
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.roomNo = in.readString();
		
	}	

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(roomNo);
		
	}

	@Override
	public int getMessageId() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public int getMessageType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	@Override
	public String toString() {
		return "ProxyRoom [roomNo=" + roomNo + "]";
	}
	
	
	
}
