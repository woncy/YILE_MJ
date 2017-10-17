package mj.net.message.login.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 进入房间的消息
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */

public class JoinDouniuRoom extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 2;
	
	private String roomCheckId;
    public int countPeople; //记录进入房间的次数
	public JoinDouniuRoom(){
		
	}
	
	public JoinDouniuRoom(String roomCheckId){
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
		 countPeople++;
		return roomCheckId;
	}
	
	public void setRoomCheckId(String roomCheckId) {
		this.roomCheckId = roomCheckId;
	}

	
	@Override
	public String toString() {
		return "JoinDouniuRoom [roomCheckId=" + roomCheckId + ", ]";
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
