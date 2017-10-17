package game.boss.douniu.msg;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;

import game.boss.msg.RegSessionMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class JoinDouniuRoomMsg  extends AbstractSessionPxMsg{
                    
	public static final int ID = RegSessionMsg.ID + 50;   //5
    private short sessionId;
    private short douniuSceneId;

    private String douniuSceneAddress;
    private int douniuScenePort;

    public JoinDouniuRoomMsg() {
        super(ID);
    }

    @Override
    public void encode(ByteBuf out) throws Exception {        
        out.writeShort(sessionId);
        out.writeShort(douniuSceneId);
        writeString(out, douniuSceneAddress);
        out.writeInt(douniuScenePort);
    }

    @Override
    public void decode(ByteBuf in, ChannelHandlerContext chc) throws Exception {
        sessionId =  in.readShort();
        douniuSceneId =  in.readShort();
        douniuSceneAddress = readString(in);
        douniuScenePort = in.readInt();
    }

    public short getSessionId() {
        return sessionId;
    }

    public void setSessionId(short sessionId) {
        this.sessionId = sessionId;
    }

    public short getDouniuSceneId() {
        return douniuSceneId;
    }

    public void setDouniuSceneId(short douniuSceneId) {
        this.douniuSceneId = douniuSceneId;
    }

    public String getDouniuSceneAddress() {
        return douniuSceneAddress;
    }

    public void setDouniuSceneAddress(String douniuSceneAddress) {
        this.douniuSceneAddress = douniuSceneAddress;
    }

    public int getDouniuScenePort() {
        return douniuScenePort;
    }

    public void setDouniuScenePort(int douniuScenePort) {
        this.douniuScenePort = douniuScenePort;
    }


    @Override
    public String toString() {
        return "JoinDouniuRoomMsg{" +
                "sessionId=" + sessionId +
                ", sceneId=" + douniuSceneId +
                ", sceneAddress='" + douniuSceneAddress + '\'' +
                ", scenePort=" + douniuScenePort +
                '}';
    }
}

