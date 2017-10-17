package mj.net.message.room.pdk;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class JoinPdkRoom extends AbstractMessage{
	public static final int TYPE = 4;
	public static final int ID = 6;
	private String room_no;
	
	public JoinPdkRoom() {
		super();
	}
	
	
	public JoinPdkRoom(String room_no) {
		super();
		this.room_no = room_no;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.room_no = in.readString();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(room_no);
	}
	@Override
	public int getMessageId() {
		return ID;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}


	public String getRoom_no() {
		return room_no;
	}


	public void setRoom_no(String room_no) {
		this.room_no = room_no;
	}


	@Override
	public String toString() {
		return "JoinPdkRoom [room_no=" + room_no + "]";
	}
	
	
}
