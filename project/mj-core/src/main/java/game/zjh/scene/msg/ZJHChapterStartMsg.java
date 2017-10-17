package game.zjh.scene.msg;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

import game.scene.msg.RoomEndMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ZJHChapterStartMsg extends AbstractSessionPxMsg {
    public static final int ID = CheckDelZJHRoomMsg.ID + 1;

    private int roomId;


    public ZJHChapterStartMsg() {
        super(ID);
    }


    @Override
    public void encode(ByteBuf out) throws Exception {
        out.writeInt(roomId);

    }

    @Override
    public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
        roomId = in.readInt();
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "ZJHChapterStartMsg{" +
                "roomId=" + roomId +
                '}';
    }

}
