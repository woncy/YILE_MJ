package mj.net.message.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class ProxyRoomIF{

	private String roomNo;
	private List<UserIF> users;
	public void decode(Input in) throws IOException, ProtocolException {
		this.roomNo = in.readString();
		int len = in.readInt();
		if(len > -1){
			users = new ArrayList<UserIF>();
			for (int i = 0; i < len; i++) {
				UserIF suserIf = new UserIF();
				suserIf.decode(in);
				users.add(suserIf);
			}
		}
	}
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(roomNo);
		int len = -1;
		if(users!=null){
			len = users.size();
		}
		out.writeInt(len);
		for (int i = 0; i < len; i++) {
			users.get(i).encode(out);
		}
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public List<UserIF> getUsers() {
		return users;
	}
	public void setUsers(List<UserIF> users) {
		this.users = users;
	}
	@Override
	public String toString() {
		return "ProxyRoomIF [roomNo=" + roomNo + ", users=" + users + "]";
	}
	
	
	
	
	
}
