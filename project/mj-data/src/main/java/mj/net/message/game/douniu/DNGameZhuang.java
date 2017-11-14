package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 
* @ClassName: DNGameZhuang
* @Description: TODO(通知客户端谁抢到庄了)
* @author 13323659953@163.com   
* @date 2017年11月3日 上午11:32:09
*
 */
public class DNGameZhuang extends AbstractMessage{
	private static final int TYPE = 2;
	private static final int ID = 11;
	private int index;//庄家位置
	
	

	public DNGameZhuang() {
		super();
	}

	public DNGameZhuang(int index) {
		super();
		this.index = index;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.index = in.readInt();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(index);
	}

	@Override
	public int getMessageId() {
		return ID;
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}

	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "DNGameZhuang [index=" + index + "]";
	}
	
	
	

}
