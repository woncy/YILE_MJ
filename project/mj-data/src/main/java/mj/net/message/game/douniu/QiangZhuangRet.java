package mj.net.message.game.douniu;

import java.io.IOException;
import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class QiangZhuangRet extends AbstractMessage {
	public static final int TYPE			 = 5;
	public static final int ID				 = 35;

	private int locationIndex ; 
	
	
	public QiangZhuangRet() {
		super();
	}

	public QiangZhuangRet(int index){
		locationIndex = index;
	}
	
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
	
		locationIndex = in.readInt();

	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		
		out.writeInt(locationIndex);

	}
   
	
	public int getLocationIndex() {
		return locationIndex;
	}
	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	@Override
	public int getMessageId() {
	
		return ID;
	}

	
	@Override
	public String toString() {
		return "QiangZhuangRet [locationIndex=" + locationIndex + "]";
	}

	@Override
	public int getMessageType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

}
