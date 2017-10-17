package game.douniu.scene.msg;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class RoomOverMsg extends AbstractSessionPxMsg {
    public static final int ID = DouniuChapterStartMsg.ID + 1;

    private int roomId;
    private int crateUserId;

    public RoomOverMsg() {
        super(ID);
    }


    @Override
    public void encode(ByteBuf out) throws Exception {
        out.writeInt(roomId);
        out.writeInt(crateUserId);
    }

    @Override
    public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
        roomId = in.readInt();
        crateUserId = in.readInt();
    }

    public int getCrateUserId() {
        return crateUserId;
    }

    public void setCrateUserId(int crateUserId) {
        this.crateUserId = crateUserId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "RoomOverMsg{" +
                "roomId=" + roomId +
                ", crateUserId=" + crateUserId +
                '}';
    }
}

