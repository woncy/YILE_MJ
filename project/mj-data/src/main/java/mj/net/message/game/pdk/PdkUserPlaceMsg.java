package mj.net.message.game.pdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class PdkUserPlaceMsg extends AbstractMessage{
	public static final int TYPE = 4;
	public static final int ID = 5;
	private List<Integer> shouPais;	//手牌
	private List<Integer> outPais; 	//出过的牌
	
	public PdkUserPlaceMsg() {
		super();
	}
	public PdkUserPlaceMsg(List<Integer> shouPais, List<Integer> outPais) {
		super();
		this.shouPais = shouPais;
		this.outPais = outPais;
	}
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		int shouPaisLength = in.readInt();
		if(shouPaisLength!=-1){
			this.shouPais = new ArrayList<Integer>();
		}
		for (int i = 0; i < shouPaisLength; i++) {
			shouPais.add(in.readInt());
		}
		
		int outPaisLength = in.readInt();
		if(outPaisLength!=-1)
			this.outPais = new ArrayList<Integer>();
		for(int i=0;i<outPaisLength;i++){
			outPais.add(in.readInt());
		}
		
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		int shouPaisLength = -1;
		int outPaisLength = -1;
		if(shouPais!=null){
			shouPaisLength = shouPais.size();
		}
		if(outPais!=null){
			outPaisLength = outPais.size();
		}
		out.writeInt(shouPaisLength);
		for (int i = 0; i < shouPaisLength; i++) {
			out.writeInt(shouPais.get(i));
		}
		out.writeInt(outPaisLength);
		for(int i=0;i<outPaisLength;i++){
			out.writeInt(outPais.get(i));
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
	public List<Integer> getShouPais() {
		return shouPais;
	}
	public void setShouPais(List<Integer> shouPais) {
		this.shouPais = shouPais;
	}
	public List<Integer> getOutPais() {
		return outPais;
	}
	public void setOutPais(List<Integer> outPais) {
		this.outPais = outPais;
	}
	@Override
	public String toString() {
		return "PdkUserPlaceMsg [shouPais=" + shouPais + ", outPais=" + outPais + "]";
	}
	
	
}
