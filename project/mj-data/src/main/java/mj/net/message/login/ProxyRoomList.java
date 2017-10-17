package mj.net.message.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class ProxyRoomList extends AbstractMessage {

	private static final int TYPE = 7;
	private static final int ID = 43;
	private List<ProxyRoomIF> infos;

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		int len = in.readInt();
		if(len > -1){
			infos = new ArrayList<ProxyRoomIF>();
			for (int i = 0; i < len; i++) {
				ProxyRoomIF roomIF = new ProxyRoomIF();
				roomIF.decode(in);
				infos.add(roomIF);
			}
		}
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		int len = -1;
		if(infos!=null){
			len = infos.size();
		}
		out.writeInt(len);
		for (int i = 0; i < len; i++) {
			infos.get(i).encode(out);
		}
	}

	@Override
	public int getMessageId() {
		return ID;
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}

	public List<ProxyRoomIF> getInfos() {
		return infos;
	}

	public void setInfos(List<ProxyRoomIF> infos) {
		this.infos = infos;
	}

	@Override
	public String toString() {
		return "ProxyRoomList [infos=" + infos + "]";
	}
	
	
	
}
