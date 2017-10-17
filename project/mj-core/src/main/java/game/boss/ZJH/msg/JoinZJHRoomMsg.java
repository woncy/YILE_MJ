package game.boss.ZJH.msg;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

import game.boss.msg.RegSessionMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class JoinZJHRoomMsg  extends AbstractSessionPxMsg{

	public static final int ID = 20;

    private short sessionId;
    private short ZJHsceneId;

    private String ZJHsceneAddress;
    private int ZJHscenePort;

    public JoinZJHRoomMsg() {
        super(ID);
    }

    @Override
    public void encode(ByteBuf out) throws Exception {
        out.writeShort(sessionId);
        out.writeShort(ZJHsceneId);
        writeString(out, ZJHsceneAddress);
        out.writeInt(ZJHscenePort);
    }

    @Override
    public void decode(ByteBuf in, ChannelHandlerContext chc) throws Exception {
        sessionId =  in.readShort();
        ZJHsceneId =  in.readShort();
        ZJHsceneAddress = readString(in);
        ZJHscenePort = in.readInt();
    }

    public short getSessionId() {
        return sessionId;
    }

    public void setSessionId(short sessionId) {
        this.sessionId = sessionId;
    }

    public short getZJHSceneId() {
        return ZJHsceneId;
    }

    public void setZJHSceneId(short ZJHsceneId) {
        this.ZJHsceneId = ZJHsceneId;
    }

    public String getZJHSceneAddress() {
        return ZJHsceneAddress;
    }

    public void setZJHSceneAddress(String ZJHsceneAddress) {
        this.ZJHsceneAddress = ZJHsceneAddress;
    }

    public int getZJHScenePort() {
        return ZJHscenePort;
    }

    public void setZJHScenePort(int ZJHscenePort) {
        this.ZJHscenePort = ZJHscenePort;
    }


    @Override
    public String toString() {
        return "JoinRoomMsg{" +
                "sessionId=" + sessionId +
                ", sceneId=" + ZJHsceneId +
                ", sceneAddress='" + ZJHsceneAddress + '\'' +
                ", scenePort=" + ZJHscenePort +
                '}';
    }
}
