package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 通知下一玩家操作
 * @author 13323659953@163.com
 *
 */
public class OperationNext extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 33;
	
	private  int index;
	private int curScore;
	
	public OperationNext() {
		// TODO Auto-generated constructor stub
	}
	
	public OperationNext(int index, int curScore) {
		super();
		this.index = index;
		this.curScore = curScore;
	}

	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getCurScore() {
		return curScore;
	}
	public void setCurScore(int curScore) {
		this.curScore = curScore;
	}
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.index = in.readInt();
		this.curScore = in.readInt();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(getIndex());
		out.writeInt(getCurScore());
		
	}
	@Override
	public int getMessageId() {
		// TODO Auto-generated method stub
		return ID;
	}
	@Override
	public int getMessageType() {
		// TODO Auto-generated method stub
		return TYPE;
	}
	

}
