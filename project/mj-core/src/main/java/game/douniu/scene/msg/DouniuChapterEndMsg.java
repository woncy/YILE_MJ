package game.douniu.scene.msg;

import java.util.ArrayList;
import java.util.List;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class DouniuChapterEndMsg extends AbstractSessionPxMsg {
	public static final int ID = CheckOffLineDouniuRoom.ID + 1;

	private int roomId;
	private int num;
	List<ChapterUserMsg> chapterUserMsgs;

	public DouniuChapterEndMsg() {
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

	public List<ChapterUserMsg> getChapterUserMsgs() {
		return chapterUserMsgs;
	}

	public void setChapterUserMsgs(List<ChapterUserMsg> chapterUserMsgs) {
		this.chapterUserMsgs = chapterUserMsgs;
	}

	@Override
	public String toString() {
		return "DouniuChapterEndMsg [roomId=" + roomId + ", num=" + num + ", chapterUserMsgs=" + chapterUserMsgs + "]";
	}

	@Override
	public void encode(ByteBuf out) throws Exception {
		out.writeInt(roomId);
		out.writeInt(num);
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
