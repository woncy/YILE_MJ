package mj.net.message.game.douniu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class RoomResult extends AbstractMessage {
	private static final int TYPE = 2;
	private static final int ID = 16;
	private List<UserRoomResult> results;
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		int len = in.readInt();
		if(len>-1){
			results = new ArrayList<UserRoomResult>();
		}
		for (int i = 0; i < len; i++) {
			UserRoomResult result = new UserRoomResult();
			result.decode(in);
			results.add(result);
			
		}
		
	}
	
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		if(results==null){
			out.writeInt(-1);
		}else{
			out.writeInt(results.size());
			for (int i = 0; i < results.size(); i++) {
				results.get(i).encode(out);
			}
		}
		
	}

	@Override
	public String toString() {
		return "RoomResult [results=" + results + "]";
	}

	public List<UserRoomResult> getResults() {
		return results;
	}

	public void setResults(List<UserRoomResult> results) {
		this.results = results;
	}

	@Override
	public int getMessageType() {
		// TODO 自动生成的方法存根
		return TYPE;
	}

	@Override
	public int getMessageId() {
		// TODO 自动生成的方法存根
		return ID;
	}

}
