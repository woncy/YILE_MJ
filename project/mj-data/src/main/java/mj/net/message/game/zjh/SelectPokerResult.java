package mj.net.message.game.zjh;

import java.io.IOException;
import java.util.Arrays;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 看牌信息
 * @author 13323659953@163.com
 *
 */
public class SelectPokerResult extends AbstractMessage{
	public static final int TYPE = 3;
	public static final int ID = 11;
	
	private int[] shouPaiNum;
	
	private String pokerType;
	
	private int locationIndex;
	
	public SelectPokerResult(){
		
	}
	


	public SelectPokerResult(int[] shouPaiNum, String pokerType, int locationIndex) {
		super();
		this.shouPaiNum = shouPaiNum;
		this.pokerType = pokerType;
		this.locationIndex = locationIndex;
	}



	public int[] getShouPaiNum() {
		return shouPaiNum;
	}

	public void setShouPaiNum(int[] shouPaiNum) {
		this.shouPaiNum = shouPaiNum;
	}

	public String getPokerType() {
		return pokerType;
	}

	public void setPokerType(String pokerType) {
		this.pokerType = pokerType;
	}

	public int getLocationIndex() {
		return locationIndex;
	}



	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}



	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.shouPaiNum = in.readIntArray();
		this.pokerType = in.readString();
		this.locationIndex = in.readInt();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeIntArray(getShouPaiNum());
		out.writeString(getPokerType());
		out.writeInt(getLocationIndex());
		
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}

	@Override
	public int getMessageId() {
		return ID;
	}



	@Override
	public String toString() {
		return "SelectPokerResult [shouPaiNum=" + Arrays.toString(shouPaiNum) + ", pokerType=" + pokerType
				+ ", locationIndex=" + locationIndex + "]";
	}
	
	
}
