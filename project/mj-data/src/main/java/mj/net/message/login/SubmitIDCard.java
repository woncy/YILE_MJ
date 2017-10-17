package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class SubmitIDCard extends AbstractMessage {
	public static final int TYPE = 7;
	public static final int ID = 40;
	private String card;
	
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.card = in.readString();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(card);
	}

	@Override
	public int getMessageId() {
		return ID;
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	@Override
	public String toString() {
		return "SubmitIDCard [card=" + card + "]";
	}
	
	

}
