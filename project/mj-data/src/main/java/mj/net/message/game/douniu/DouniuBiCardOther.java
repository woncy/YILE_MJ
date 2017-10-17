package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 将比牌的消息，发送给其他用户，渲染
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuBiCardOther extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 16; // 渲染消息
	
	/**
	 * 比牌的人位置
	 */
	private int indexOne;
	/**
	 * 被比牌的人的位置
	 */
	private int indexTwo;
	
	public DouniuBiCardOther(){
		
	}
	
	public DouniuBiCardOther(int indexOne, int indexTwo){
		this.indexOne = indexOne;
		this.indexTwo = indexTwo;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		indexOne = in.readInt();
		indexTwo = in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeInt(getIndexOne());
		out.writeInt(getIndexTwo());
	}

	/**
	 * 比牌的人位置
	 */
	public int getIndexOne() {
		return indexOne;
	}
	
	/**
	 * 比牌的人位置
	 */
	public void setIndexOne(int indexOne) {
		this.indexOne = indexOne;
	}

	/**
	 * 被比牌的人的位置
	 */
	public int getIndexTwo() {
		return indexTwo;
	}
	
	/**
	 * 被比牌的人的位置
	 */
	public void setIndexTwo(int indexTwo) {
		this.indexTwo = indexTwo;
	}

	
	@Override
	public String toString() {
		return "DouNiuBiCardOther [indexOne=" + indexOne + ",indexTwo=" + indexTwo + ", ]";
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
