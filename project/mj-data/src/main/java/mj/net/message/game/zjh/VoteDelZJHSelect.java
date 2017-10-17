package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class VoteDelZJHSelect extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 25;
	
	private int userId;
	private String userName;
	
	public VoteDelZJHSelect(){
		
	}
	
	public VoteDelZJHSelect(int userId, String userName){
		this.userId = userId;
		this.userName = userName;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		userId = in.readInt();
		userName = in.readString();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(getUserId());
		out.writeString(getUserName());
	}

	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	@Override
	public String toString() {
		return "VoteDelZJHSelect [userId=" + userId + ",userName=" + userName + ", ]";
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
