package mj.net.message.game.zjh;

import java.io.IOException;
import java.util.Arrays;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 发牌信息
 * @author 13323659953@163.com
 *
 */
public class FaInfo extends AbstractMessage{

	public static final int TYPE = 3;
	public static final int ID = 8;
	
	private int zhuangIndex;
	//剩余玩家数量
	private int userNum;
	//剩余玩家位置
	private int[] userIndex;
	
	public FaInfo(){
		
	}
	
	    
	public FaInfo(int zhuangIndex, int userNum, int[] userIndex) {
		super();
		this.zhuangIndex = zhuangIndex;
		this.userNum = userNum;
		this.userIndex = userIndex;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		zhuangIndex = in.readInt();
		userNum = in.readInt();
		userIndex = in.readIntArray();
	}
	
	   
	    
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(getZhuangIndex());
		out.writeInt(getUserNum());
		out.writeIntArray(getUserIndex());
		
	}
	
	
	    
	public int getZhuangIndex() {
		return zhuangIndex;
	}


	public void setZhuangIndex(int zhuangIndex) {
		this.zhuangIndex = zhuangIndex;
	}


	public int getUserNum() {
		return userNum;
	}


	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}


	public int[] getUserIndex() {
		return userIndex;
	}


	public void setUserIndex(int[] userIndex) {
		this.userIndex = userIndex;
	}


	@Override
	public int getMessageId() {
		return ID;
	}
	
	    
	@Override
	public int getMessageType() {
		return TYPE;
	}


	@Override
	public String toString() {
		return "FaInfo [zhuangIndex=" + zhuangIndex + ", userNum=" + userNum + ", userIndex="
				+ Arrays.toString(userIndex) + "]";
	}



	


	
}
