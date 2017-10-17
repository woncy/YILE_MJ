package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 发送服务器下注的倍数
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuSendMsgShu extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 26;
	
	/**
	 * 下注，跟注，弃牌操作类型
	 */
	private String opt;
	/**
	 * 下注的倍数
	 */
	private int value;
	
	public DouniuSendMsgShu(){
		
	}
	
	public DouniuSendMsgShu(String opt, int value){
		this.opt = opt;
		this.value = value;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		opt = in.readString();
		value = in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(getOpt());
		out.writeInt(getValue());
	}

	/**
	 * 下注，跟注，弃牌操作类型
	 */
	public String getOpt() {
		return opt;
	}
	
	/**
	 * 下注，跟注，弃牌操作类型
	 */
	public void setOpt(String opt) {
		this.opt = opt;
	}

	/**
	 * 下注的倍数
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * 下注的倍数
	 */
	public void setValue(int value) {
		this.value = value;
	}

	
	@Override
	public String toString() {
		return "DouNiuSendMsgZhu [opt=" + opt + ",value=" + value + ", ]";
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
