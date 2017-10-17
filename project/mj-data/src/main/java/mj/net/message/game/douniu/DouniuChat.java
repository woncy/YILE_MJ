package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class DouniuChat extends AbstractMessage {
	
	public static final int TYPE			 = 5;
	public static final int ID				 = 50;
	
	
	private int index; // 1: 文字, 2 : 表情， 3 ： 语音。
	
	private String chatContent;

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		// TODO Auto-generated method stub
		chatContent = in.readString();
		index = in.readInt();
		 
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		// TODO Auto-generated method stub
		out.writeString(chatContent);
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
	
	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "DouniuChat [index=" + index + ", chatContent=" + chatContent
				+ "]";
	}

	public String getChatContent() {
		return chatContent;
	}

	public void setChatContent(String chatContent) {
		this.chatContent = chatContent;
	}

	
}
