package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class JoinClub extends AbstractMessage{
	public static final int TYPE = 7;
	public static final int ID = 33;
	
	private String clubId;

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.clubId = in.readString();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(clubId);
	}

	@Override
	public int getMessageId() {
		return ID;
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}

	public String getClubId() {
		return clubId;
	}

	public void setClubId(String clubId) {
		this.clubId = clubId;
	}

	@Override
	public String toString() {
		return "JoinClub [clubId=" + clubId + "]";
	}
	
	
	
}
