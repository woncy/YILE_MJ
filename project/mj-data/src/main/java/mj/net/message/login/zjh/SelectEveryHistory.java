package mj.net.message.login.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 查询房间战绩详情（每一局战绩）
 * @author 13323659953@163.com
 *
 */
public class SelectEveryHistory extends AbstractMessage{
	public static final int TYPE = 3;
	public static final int ID = 36;
	private String roomCheckId;
	public SelectEveryHistory() {
	}
	
	
	public SelectEveryHistory(String roomCheckId) {
		super();
		this.roomCheckId = roomCheckId;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.roomCheckId = in.readString();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(getRoomCheckId());
	}
	
	public String getRoomCheckId() {
		return roomCheckId;
	}


	public void setRoomCheckId(String roomCheckId) {
		this.roomCheckId = roomCheckId;
	}


	@Override
	public int getMessageId() {
		return ID;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}

}
