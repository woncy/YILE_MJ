package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 扣分信息
 * @author 13323659953@163.com
 *
 */
public class DelScoreResult extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 14;
	
	private int index;
	private int score;
	
	public DelScoreResult() {
		// TODO Auto-generated constructor stub
	}
	
	public DelScoreResult(int index, int score) {
		super();
		this.index = index;
		this.score = score;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.index = in.readInt();
		this.score = in.readInt();
		
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(getIndex());
		out.writeInt(getScore());
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
