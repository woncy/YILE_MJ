package game.douniu.scene.msg;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class DouniuRecordUserRoomMsg {
	private int roomId;
	private int userId;
	private int endScore;
	private int winCount;
	
	public DouniuRecordUserRoomMsg() {
	}
	public void decode(ByteBuf in, ChannelHandlerContext arg1) throws Exception {
		roomId = in.readInt();
		userId = in.readInt();
		endScore = in.readInt();
		winCount = in.readInt();
	}
	public void encode(ByteBuf out) throws Exception {
		out.writeInt(roomId);
		out.writeInt(userId);
		out.writeInt(endScore);
		out.writeInt(winCount);
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getEndScore() {
		return endScore;
	}
	public void setEndScore(int endScore) {
		this.endScore = endScore;
	}
	public int getWinCount() {
		return winCount;
	}
	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}
	
}
