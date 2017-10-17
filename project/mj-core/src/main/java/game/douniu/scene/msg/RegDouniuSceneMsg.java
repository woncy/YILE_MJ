package game.douniu.scene.msg;

import com.isnowfox.game.proxy.message.AbstractSessionPxMsg;
import com.isnowfox.game.proxy.message.LogoutPxMsg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class RegDouniuSceneMsg extends AbstractSessionPxMsg{
	 public static final int ID = LogoutPxMsg.ID + 50; //3

	 private short DouniuSceneId;
	 private String DouniuSceneAddress;
	 private int DouniuScenePort;
	
	 public RegDouniuSceneMsg() {
		super(ID);
		
	}

	public RegDouniuSceneMsg( short DouniuSceneId,String DouniuSceneAddress,int DouniuScenePort){
		 	this();
	        this.DouniuSceneId = DouniuSceneId;
	        this.DouniuSceneAddress = DouniuSceneAddress;
	        this.DouniuScenePort = DouniuScenePort;
	} 
	@Override
	public void encode(ByteBuf out) throws Exception {
			out.writeShort(DouniuSceneId);
	        writeString(out, DouniuSceneAddress);
	        out.writeInt(DouniuScenePort);
	}

	@Override
	public void decode(ByteBuf in, ChannelHandlerContext ctx) throws Exception {
		DouniuSceneId = in.readShort();
		DouniuSceneAddress = readString(in);
		DouniuScenePort = in.readInt();
	}

	public short getDouniuSceneId() {
		return DouniuSceneId;
	}

	public void setDouniuSceneId(short douniuSceneId) {
		DouniuSceneId = douniuSceneId;
	}

	public String getDouniuSceneAddress() {
		return DouniuSceneAddress;
	}

	public void setDouniuSceneAddress(String douniuSceneAddress) {
		DouniuSceneAddress = douniuSceneAddress;
	}

	public int getDouniuScenePort() {
		return DouniuScenePort;
	}

	public void setDouniuScenePort(int douniuScenePort) {
		DouniuScenePort = douniuScenePort;
	}

	@Override
	public String toString() {
		return "RegDouniuSceneMsg [DouniuSceneId=" + DouniuSceneId + ", DouniuSceneAddress=" + DouniuSceneAddress + ", DouniuScenePort="
				+ DouniuScenePort + "]";
	}
	
	

}
