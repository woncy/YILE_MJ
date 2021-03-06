package game.douniu.scene.msg;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ChapterStart2Msg extends AbstractSessionPxMsg {
    public static final int ID = 12;
    
    private int roomId;
    private int num;

    public ChapterStart2Msg() {
        super(ID);
    }

	public ChapterStart2Msg(int id, int roomId, int num) {
		super(id);
		this.roomId = roomId;
		this.num = num;
	}


	@Override
    public void encode(ByteBuf out) throws Exception {
        out.writeInt(roomId);
        out.writeInt(num);
    }

    @Override
    public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
        roomId = in.readInt();
        num =in.readInt();
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

	public void setNum(int chapterNums) {
		this.num = chapterNums;
	}

	@Override
	public String toString() {
		return "DouniuChapterStartMsg [roomId=" + roomId + ", num=" + num + "]";
	}


	
}
