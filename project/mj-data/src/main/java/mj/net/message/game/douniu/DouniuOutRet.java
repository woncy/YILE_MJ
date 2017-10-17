package mj.net.message.game.douniu;

import java.io.IOException;
import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 对指定对应玩家说话回复
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuOutRet extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 19;
	
	private boolean  isXiaZhu =  false;
	
	private boolean  isCompare = false;
	
	public DouniuOutRet(){
		
	}
	
	public DouniuOutRet(boolean isXiaZhu, boolean isCompare){
		this.isXiaZhu = isXiaZhu;
		this.isCompare = isCompare;
	}
	
	public boolean isXiaZhu() {
		return isXiaZhu;
	}

	public void setXiaZhu(boolean isXiaZhu) {
		this.isXiaZhu = isXiaZhu;
	}

	public boolean isCompare() {
		return isCompare;
	}

	public void setCompare(boolean isCompare) {
		this.isCompare = isCompare;
	}

	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		isXiaZhu = in.readBoolean();
		isCompare = in.readBoolean();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeBoolean(isXiaZhu());
		out.writeBoolean(isCompare());
	}

	@Override
	public String toString() {
		return "DouniuOutRet [isXiaZhu=" + isXiaZhu + ", isCompare=" + isCompare + "]";
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
