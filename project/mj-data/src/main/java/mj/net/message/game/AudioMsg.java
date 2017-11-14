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
	private byte[] data;
//	private String data;
	
	
	public AudioMsg() {
		super();
	}

	public AudioMsg(int index, byte[] data) {
		super();
		this.index = index;
		this.data = data;
	}
//	public AudioMsg(int index, String data) {
//		super();
//		this.index = index;
//		this.data = data;
//	}
	

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.index = in.readInt();
//		this.data = in.readString();
		this.data = in.readBytes();
//		int len = in.readInt();
//		if(len >-1){
//			data = new int[len];
//		}
//		for (int s = 0; s < len; s++) {
//			data[s] = in.readInt();
//		}
		
	}


	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(index);
//		out.writeString(data);
		out.writeBytes(data);
//		int len = -1;
//		if(data!=null){
//			len = data.length;
//		}
//		out.writeInt(len);
//		for (int i = 0; i < len; i++) {
//			out.writeInt(data[i]);
//		}
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "AudioMsg [index=" + index + ", data=" + data + "]";
	}
	
}
