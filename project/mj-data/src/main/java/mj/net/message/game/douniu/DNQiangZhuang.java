package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 
* @ClassName: DNQiangZhuang
* @Description: TODO(抢庄的消息)
* @author 13323659953@163.com   
* @date 2017年11月3日 上午11:02:09
*
 */
public class DNQiangZhuang extends AbstractMessage{

	private static final int ID = 10;
	private static final int TYPE = 2;
	private int index;	//抢庄人位置
	private boolean qiang;	//是否抢庄
	
	
	public DNQiangZhuang() {
		super();
	}

	public DNQiangZhuang(int index, boolean qiang) {
		super();
		this.index = index;
		this.qiang = qiang;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.index = in.readInt();
		this.qiang = in.readBoolean();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(index);
		out.writeBoolean(qiang);
	}
	
	
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isQiang() {
		return qiang;
	}

	public void setQiang(boolean qiang) {
		this.qiang = qiang;
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
		return "DNQiangZhuang [index=" + index + ", qiang=" + qiang + "]";
	}
	
	

}
