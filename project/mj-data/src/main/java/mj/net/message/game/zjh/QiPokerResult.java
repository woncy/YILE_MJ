package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 弃牌结果
 * @author 13323659953@163.com
 *
 */
public class QiPokerResult extends AbstractMessage{
	public static final int TYPE = 3;
	public static final int ID = 31;
	
	private int qiIndex;

	public QiPokerResult() {
	}

	public QiPokerResult(int qiIndex) {
		super();
		this.qiIndex = qiIndex;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.qiIndex = in.readInt();
		
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(getQiIndex());
		
	}
	
	public int getQiIndex() {
		return qiIndex;
	}
	public void setQiIndex(int qiIndex) {
		this.qiIndex = qiIndex;
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
	}}
