package mj.net.message.login.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 *  斗牛退出房间
 *  
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class ExitDouniuRoom extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 6;
	
	String roomId; 
	
	public ExitDouniuRoom(){
		
	}
	
	public ExitDouniuRoom(String roomId) {
		super();
		this.roomId = roomId;
	}

	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		roomId= in.readString();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(roomId);
	}

	
	
	
	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	@Override
	public final int getMessageType() {
		return TYPE;
	}

	@Override
	public String toString() {
		return "ExitDouniuRoom [roomId=" + roomId + "]";
	}

	@Override
	public final int getMessageId() {
		return ID;
	}
}
