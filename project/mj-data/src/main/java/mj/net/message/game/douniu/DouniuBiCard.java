package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 发送消息给客户端，和指定玩家比牌
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuBiCard extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 14; //比牌消息
	
	/**
	 * 玩家的位置
	 */
	private int index;
	
	public DouniuBiCard(){
		
	}
	
	public DouniuBiCard(int index){
		this.index = index;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		index = in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(getIndex());
	}

	/**
	 * 玩家的位置
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * 玩家的位置
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	
	@Override
	public String toString() {
		return "DouNiuBiCard [index=" + index + ", ]";
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
