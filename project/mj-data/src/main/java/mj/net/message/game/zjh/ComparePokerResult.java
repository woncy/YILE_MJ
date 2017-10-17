package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 返回比牌结果
 * @author 13323659953@163.com
 *
 */
public class ComparePokerResult extends  AbstractMessage{
	public static final int TYPE = 3;
	public static final int ID = 16;
	
	private int winIndex;
	
	private int loseIndex;
	
	public ComparePokerResult() {
		
	}
	
	
	public ComparePokerResult(int winIndex, int loseIndex) {
		super();
		this.winIndex = winIndex;
		this.loseIndex = loseIndex;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.winIndex = in.readInt();
		this.loseIndex = in.readInt();
		
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(getWinIndex());
		out.writeInt(getLoseIndex());
	}

	
	public int getWinIndex() {
		return winIndex;
	}


	public void setWinIndex(int winIndex) {
		this.winIndex = winIndex;
	}


	public int getLoseIndex() {
		return loseIndex;
	}


	public void setLoseIndex(int loseIndex) {
		this.loseIndex = loseIndex;
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
