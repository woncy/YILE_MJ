package game.boss.douniu;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class DouniuSceneUserInfo {
	 private short sessionId;
	    private int gatewayId;
	    private int userId;

	    public DouniuSceneUserInfo() {
	    }
	    public DouniuSceneUserInfo(short sessionId, int gatewayId, int userId) {
	        this.sessionId = sessionId;
	        this.gatewayId = gatewayId;
	        this.userId = userId;
	    }

	  

	    public void encode(ByteBuf out) throws Exception {
	        out.writeShort(sessionId);
	        out.writeInt(gatewayId);
	        out.writeInt(userId);
	    }

	    public void decode(ByteBuf in, ChannelHandlerContext chc) throws Exception {
	        sessionId = in.readShort();
	        gatewayId = in.readInt();
	        userId = in.readInt();
	    }

	    public short getSessionId() {
	        return sessionId;
	    }

	    public void setSessionId(short sessionId) {
	        this.sessionId = sessionId;
	    }

	    public int getGatewayId() {
	        return gatewayId;
	    }

	    public void setGatewayId(int gatewayId) {
	        this.gatewayId = gatewayId;
	    }

	    public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		@Override
		public String toString() {
			return "DouniuSceneUserInfo [sessionId=" + sessionId
					+ ", gatewayId=" + gatewayId + ", userId=" + userId + "]";
		}

		
}
