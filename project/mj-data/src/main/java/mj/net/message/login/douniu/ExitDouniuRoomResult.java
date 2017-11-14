package mj.net.message.login.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 退出房间结果
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class ExitDouniuRoomResult extends AbstractMessage{
	public static final int TYPE			 = 2;
	public static final int ID				 = 17;
	
	int userID;
	
	public ExitDouniuRoomResult(){
		
	}
	
	public ExitDouniuRoomResult(int userID) {
		super();
		this.userID = userID;
	}

	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		 userID =in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
	     out.writeInt(userID);
	}

	
	
	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	@Override
	public String toString() {
		return "ExitDouniuRoomResult [userID=" + userID + "]";
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
