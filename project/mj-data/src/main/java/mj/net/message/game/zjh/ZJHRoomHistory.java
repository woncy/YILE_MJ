package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;
/**
 * 房间战绩
 * @author 13323659953@163.com
 *
 */
public class ZJHRoomHistory extends  AbstractMessage{
	public static final int TYPE = 3;
	public static final int ID = 28;
	private int userId;
	private int score;//得分
	private String userName;
	private int winCount;
	private String roomCheckId;
	private int standardCount;//房间标准局数
	private int factCount;//实际局数
	private String time;
	
	public ZJHRoomHistory() {
	}
	
	
	public ZJHRoomHistory(int userId, int score, String userName, int winCount, String roomCheckId, int standardCount,
			int factCount, String time) {
		super();
		this.userId = userId;
		this.score = score;
		this.userName = userName;
		this.winCount = winCount;
		this.roomCheckId = roomCheckId;
		this.standardCount = standardCount;
		this.factCount = factCount;
		this.time = time;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.userId = in.readInt();
		this.score = in.readInt();
		this.userName = in.readString();
		this.winCount = in.readInt();
		this.roomCheckId = in.readString();
		this.standardCount = in.readInt();
		this.factCount = in.readInt();
		this.time = in.readString();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(getUserId());
		out.writeInt(getScore());
		out.writeString(getUserName());
		out.writeInt(getWinCount());
		out.writeString(getroomCheckId());
		out.writeInt(getStandardCount());
		out.writeInt(getFactCount());
		out.writeString(getTime());
	}
	
	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public int getWinCount() {
		return winCount;
	}


	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}


	public String getroomCheckId() {
		return roomCheckId;
	}


	public void setroomCheckId(String roomCheckId) {
		this.roomCheckId = roomCheckId;
	}


	public int getStandardCount() {
		return standardCount;
	}


	public void setStandardCount(int standardCount) {
		this.standardCount = standardCount;
	}


	public int getFactCount() {
		return factCount;
	}


	public void setFactCount(int factCount) {
		this.factCount = factCount;
	}


	public String getTime() {
		return time;
	}


	public void setTime(String time) {
		this.time = time;
	}


	@Override
	public int getMessageId() {
		return ID;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}
	

}
