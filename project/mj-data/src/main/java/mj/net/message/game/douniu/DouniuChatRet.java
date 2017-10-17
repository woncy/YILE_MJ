package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class DouniuChatRet extends AbstractMessage {
	
	public static final int TYPE			 = 5;
	public static final int ID				 = 51;
	
	private int userindex; 
	private String chatContent ;
	private int index;
	

	public int getUserindex() {
		return userindex;
	}

	public void setUserindex(int userindex) {
		this.userindex = userindex;
	}

	public String getChatContent() {
		return chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	public static int getType() {
		return TYPE;
	}

	public static int getId() {
		return ID;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		// TODO Auto-generated method stub
		this.userindex = in.readInt();
		this.chatContent = in.readString();
		this.index = in.readInt();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		// TODO Auto-generated method stub
		out.writeInt(this.userindex);
		out.writeString(this.chatContent);
		out.writeInt(index);
		
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
	
	@Override
	public String toString() {
		return "ChatRet [chatContent=" + chatContent + "]";
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	

}
