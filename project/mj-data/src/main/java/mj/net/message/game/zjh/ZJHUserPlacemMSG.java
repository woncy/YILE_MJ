package mj.net.message.game.zjh;

import java.io.IOException;
import java.util.Arrays;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class ZJHUserPlacemMSG extends AbstractMessage{
	public static final int TYPE			 = 1;
	public static final int ID				 = 20;
	
	private int[] shouPai;
	/**
	 * 牌类型
	 */
	private String pokerType;
	
	public ZJHUserPlacemMSG(){
		
	}
	
	public ZJHUserPlacemMSG(int[] shouPai,String pokerType){
		this.shouPai = shouPai;
		this.pokerType = pokerType;
		
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		shouPai = in.readIntArray();
		pokerType = in.readString();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeIntArray(getShouPai());
		out.writeString(getPokerType());
		
	}

	public int[] getShouPai() {
		return shouPai;
	}
	
	public void setShouPai(int[] shouPai) {
		this.shouPai = shouPai;
	}

	
	public String getPokerType() {
		return pokerType;
	}

	public void setPokerType(String pokerType) {
		this.pokerType = pokerType;
	}

	
	@Override
	public String toString() {
		return "ZJHUserPlacemMSG [shouPai=" + Arrays.toString(shouPai) + ", pokerType=" + pokerType + "]";
	}

	@Override
	public final int getMessageType() {
		return TYPE;
	}

	@Override
	public final int getMessageId() {
		return ID;
	}

}
