package mj.net.message.game;

import java.io.IOException;
import java.util.Arrays;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class AudioMsg extends AbstractMessage{

	private static final int TYPE = 1;
	private static final int ID = 38;
	private int index;
	private int[] data;
	private String uuid;
	private int inde;//位置
	private boolean isEnd;
	
	
	public AudioMsg() {
		super();
	}


	public AudioMsg(int index, int[] data, String uuid, int inde, boolean isEnd) {
		super();
		this.index = index;
		this.data = data;
		this.uuid = uuid;
		this.inde = inde;
		this.isEnd = isEnd;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.index = in.readInt();
		int len = in.readInt();
		if(len >-1){
			data = new int[len];
		}
		for (int s = 0; s < len; s++) {
			data[s] = in.readInt();
		}
		this.uuid = in.readString();
		this.inde = in.readInt();
		this.isEnd = in.readBoolean();
	}

	

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(index);
		int len = -1;
		if(data!=null){
			len = data.length;
		}
		out.writeInt(len);
		for (int i = 0; i < len; i++) {
			out.writeInt(data[i]);
		}
		out.writeString(uuid);
		out.writeInt(inde);
		out.writeBoolean(isEnd);
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}

	@Override
	public int getMessageId() {
		return ID;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int[] getData() {
		return data;
	}

	public void setData(int[] data) {
		this.data = data;
	}

	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getInde() {
		return inde;
	}
	
	public void setInde(int inde) {
		this.inde = inde;
	}
	

	public boolean isEnd() {
		return isEnd;
	}


	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}


	@Override
	public String toString() {
		return "AudioMsg [index=" + index + ", data=" + Arrays.toString(data) + ", uuid=" + uuid + ", inde=" + inde
				+ ", isEnd=" + isEnd + "]";
	}

	
}
