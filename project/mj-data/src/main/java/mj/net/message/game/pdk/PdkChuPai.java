package mj.net.message.game.pdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class PdkChuPai extends AbstractMessage{
	public static final int TYPE = 4;
	public static final int ID = 12;
	private List<Integer> pais;

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		pais = new ArrayList<Integer>();
		for (int i = 0; i < in.readInt(); i++) {
			pais.add(in.readInt());
		}
	}

	
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		if(pais==null){
			out.writeInt(-1);
		}else{
			int len = pais.size();
			out.writeInt(len);
			for (int i = 0; i < len; i++) {
				out.writeInt(pais.get(i));
			}
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

}
