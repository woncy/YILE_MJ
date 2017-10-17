package mj.net.message.login;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * Transfer
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class Transfer extends AbstractMessage{
	public static final int TYPE			 = 7;
	public static final int ID				 = 25;
	
	private int srcId ;
	
	private int destId; 
	
	private int gold;
	
	public int getSrcId() {
		return srcId;
	}
	

	public void setSrcId(int srcId) {
		this.srcId = srcId;
	}

	public int getDestId() {
		return destId;
	}

	public void setDestId(int destId) {
		this.destId = destId;
	}
	
	public Transfer(){
		
	}
	
	public Transfer(int srcId , int destId , int gold){
		this.srcId = srcId;
		this.destId = destId;
		this.gold = gold;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		String destIdStr = in.readString();
		if(destIdStr != null && destIdStr.matches("\\d+")){
			destId = Integer.parseInt(destIdStr);
		}
		String goldStr = in.readString();
		if(goldStr != null && goldStr.matches("\\d+")){
			gold = Integer.parseInt(goldStr);		
		}
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(srcId);
		out.writeInt(destId);
		out.writeInt(getGold());
	}

	public int getGold() {
		return gold;
	}
	
	public void setGold(int gold) {
		this.gold = gold;
	}
	
	@Override
	public String toString() {
		return "Transfer [srcId = "+  srcId +", destId = "+ destId +", gold=" + gold + ", ]";
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
