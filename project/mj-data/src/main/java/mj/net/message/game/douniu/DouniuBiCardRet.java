package mj.net.message.game.douniu;

import java.io.IOException;
import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 发送比牌结果信息
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuBiCardRet extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 15;
	
	/**
	 * 发起比牌的人的locationIndex
	 */
	private int srcIndex;
	
	/*
	 * 被比牌的人的locationIndex
	 */
	private int destIndex ;

	/*
	 * 比牌结果
	 */
	private int winIndex ;
	/**
	 * 手牌的结果
	 */
	/**
	 * 庄家赢还是闲家赢
	 */
	
	public DouniuBiCardRet(){
		
	}
	
	public int getSrcIndex() {
		return srcIndex;
	}

	public void setSrcIndex(int srcIndex) {
		this.srcIndex = srcIndex;
	}

	public int getDestIndex() {
		return destIndex;
	}

	public void setDestIndex(int destIndex) {
		this.destIndex = destIndex;
	}

	public int getWinIndex() {
		return winIndex;
	}

	public void setWinIndex(int winIndex) {
		this.winIndex = winIndex;
	}

	public DouniuBiCardRet(int srcIndex, int  destIndex, int winIndex){
		this.srcIndex = srcIndex;
		this.destIndex = destIndex;
		this.winIndex = winIndex;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		srcIndex = in.readInt();
		destIndex = in.readInt();
		winIndex = in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(getSrcIndex());
		out.writeInt(getDestIndex());
		out.writeInt(getWinIndex());
	}
	
	
	@Override
	public String toString() {
		return "DouniuBiCardRet [srcIndex=" + srcIndex + ",destIndex=" 
						+ destIndex + ",winIndex=" + winIndex + ", ]";
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
