package mj.net.message.game.douniu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

import mj.data.MessageUtil;

public class DNChapterInfo extends AbstractMessage{
	private static final int TYPE = 2;
	private static final int ID = 6;
	private int zhuangIndex;//庄家位置
	private int currentIndex;//当前正在操作的用户
	private int currentChapterNum;//当前局数
	private List<SeatUserInfo> seats = new ArrayList<SeatUserInfo>();//
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.zhuangIndex = in.readInt();
		this.currentIndex = in.readInt();
		this.currentChapterNum = in.readInt();
		MessageUtil.decodeList(in, seats, SeatUserInfo.class);
		
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(zhuangIndex);
		out.writeInt(currentIndex);
		out.writeInt(currentChapterNum);
		MessageUtil.encodeList(out, seats);
	}
	@Override
	public String toString() {
		return "DNChapterInfo [zhuangIndex=" + zhuangIndex + ", currentIndex=" + currentIndex + ", currentChapterNum="
				+ currentChapterNum + ", seats=" + seats + "]";
	}
	public int getCurrentChapterNum() {
		return currentChapterNum;
	}
	public void setCurrentChapterNum(int currentChapterNum) {
		this.currentChapterNum = currentChapterNum;
	}
	public int getZhuangIndex() {
		return zhuangIndex;
	}
	public void setZhuangIndex(int zhuangIndex) {
		this.zhuangIndex = zhuangIndex;
	}
	public int getCurrentIndex() {
		return currentIndex;
	}
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
	public List<SeatUserInfo> getSeats() {
		return seats;
	}
	public void setSeats(List<SeatUserInfo> seats) {
		this.seats = seats;
	}
	@Override
	public int getMessageType() {
		// TODO 自动生成的方法存根
		return TYPE;
	}
	@Override
	public int getMessageId() {
		// TODO 自动生成的方法存根
		return ID;
	}
	
	
	
}
