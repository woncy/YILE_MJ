package game.douniu.scene.msg;

import java.util.ArrayList;
import java.util.List;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ChapterEnd2Msg extends AbstractSessionPxMsg {
	public static final int ID = 11;
	private int roomId;
	private int num;
	private int zhuangIndex;
	private int zhuangUserId;
	List<ChapterUserMsg> chapterUserMsgs;

	public ChapterEnd2Msg() {
		super(ID);
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	

	public int getZhuangIndex() {
		return zhuangIndex;
	}

	public void setZhuangIndex(int zhuangIndex) {
		this.zhuangIndex = zhuangIndex;
	}

	public int getZhuangUserId() {
		return zhuangUserId;
	}

	public void setZhuangUserId(int zhuangUserId) {
		this.zhuangUserId = zhuangUserId;
	}

	public List<ChapterUserMsg> getChapterUserMsgs() {
		return chapterUserMsgs;
	}

	public void setChapterUserMsgs(List<ChapterUserMsg> chapterUserMsgs) {
		this.chapterUserMsgs = chapterUserMsgs;
	}

	@Override
	public String toString() {
		return "DouniuChapterEndMsg [roomId=" + roomId + ", num=" + num + ", zhuangIndex=" + zhuangIndex
				+ ", zhuangUserId=" + zhuangUserId + ", chapterUserMsgs=" + chapterUserMsgs + "]";
	}

	@Override
	public void encode(ByteBuf out) throws Exception {
		out.writeInt(roomId);
		out.writeInt(num);
		out.writeInt(zhuangIndex);
		out.writeInt(zhuangUserId);
		int len = -1;
		if (chapterUserMsgs != null) {
			len = chapterUserMsgs.size();
		}
		out.writeInt(len);
		for (int i = 0; i < len; i++) {
			ChapterUserMsg userMsg = chapterUserMsgs.get(i);
			userMsg.encode(out);
		}
	}

	@Override
	public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
		roomId = in.readInt();
		num = in.readInt();
		this.zhuangIndex = in.readInt();
		this.zhuangUserId = in.readInt();
		int len = in.readInt();
		if (len == -1) {
			chapterUserMsgs = null;
		} else {
			this.chapterUserMsgs = new ArrayList<ChapterUserMsg>();
		}
		for (int i = 0; i < len; i++) {
			ChapterUserMsg userMsg = new ChapterUserMsg();
			userMsg.decode(in);
			chapterUserMsgs.add(userMsg);
		}
	}

}
