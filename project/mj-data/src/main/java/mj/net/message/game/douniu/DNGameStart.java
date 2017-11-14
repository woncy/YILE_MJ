package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class DNGameStart extends AbstractMessage{

	private static final int TYPE = 2;
	private static final int ID = 8;
	boolean showQiangZhuang = true;
	
	
	
	public boolean isShowQiangZhuang() {
		return showQiangZhuang;
	}

	public void setShowQiangZhuang(boolean showQiangZhuang) {
		this.showQiangZhuang = showQiangZhuang;
	}

	public DNGameStart() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public DNGameStart(boolean showQiangZhuang) {
		super();
		this.showQiangZhuang = showQiangZhuang;
	}

	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.showQiangZhuang = in.readBoolean();
	}

	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeBoolean(this.showQiangZhuang);
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}

	@Override
	public int getMessageId() {
		return ID;
	}

	@Override
	public String toString() {
		return "DNGameStart [showQiangZhuang=" + showQiangZhuang + "]";
	}
	
	

}
