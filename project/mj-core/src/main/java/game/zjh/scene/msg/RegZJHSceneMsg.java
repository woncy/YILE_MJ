package game.zjh.scene.msg;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;
import com.isnowfox.game.proxy.message.LogoutPxMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class RegZJHSceneMsg extends AbstractSessionPxMsg{
	 public static final int ID = LogoutPxMsg.ID + 1;

	 private short ZJHsceneId;
	 private String ZJHsceneAddress;
	 private int ZJHscenePort;
	
	 public RegZJHSceneMsg() {
		super(ID);
		
	}

	public RegZJHSceneMsg( short ZJHsceneId,String ZJHsceneAddress,int ZJHscenePort){
		 	this();
	        this.ZJHsceneId = ZJHsceneId;
	        this.ZJHsceneAddress = ZJHsceneAddress;
	        this.ZJHsceneAddress = ZJHsceneAddress;
	} 
	@Override
	public void encode(ByteBuf out) throws Exception {
			out.writeShort(ZJHsceneId);
	        writeString(out, ZJHsceneAddress);
	        out.writeInt(ZJHscenePort);
	}

	@Override
	public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
		ZJHsceneId = in.readShort();
		ZJHsceneAddress = readString(in);
		ZJHscenePort = in.readInt();
	}

	public short getZJHsceneId() {
		return ZJHsceneId;
	}

	public void setZJHsceneId(short zJHsceneId) {
		ZJHsceneId = zJHsceneId;
	}

	public String getZJHsceneAddress() {
		return ZJHsceneAddress;
	}

	public void setZJHsceneAddress(String zJHsceneAddress) {
		ZJHsceneAddress = zJHsceneAddress;
	}

	public int getZJHscenePort() {
		return ZJHscenePort;
	}

	public void setZJHscenePort(int zJHscenePort) {
		ZJHscenePort = zJHscenePort;
	}

	@Override
	public String toString() {
		return "RegZJHSceneMsg [ZJHsceneId=" + ZJHsceneId + ", ZJHsceneAddress=" + ZJHsceneAddress + ", ZJHscenePort="
				+ ZJHscenePort + "]";
	}
	
	

}
