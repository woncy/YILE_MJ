package mj.net.message.game;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class StaticsResult extends AbstractMessage {
	
	public static final int TYPE			 = 1;
	public static final int ID				 = 26;
	
	private String chatContent;

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		// TODO Auto-generated method stub
//		chatContent = in.readString();
		 
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		// TODO Auto-generated method stub
//		out.writeString(chatContent);
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
		return "chat [chatContent=" + chatContent + "]";
	}

	public String getChatContent() {
		return chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	
}
