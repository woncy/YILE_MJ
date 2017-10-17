package mj.net.message.login.douniu;

import java.io.IOException;
import java.util.Arrays;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class DouniuRoomHistory extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 47;
	
	private String roomCheckId;
	private String startDate;
	private int chapterNums;
	private String[] userNames;
	private int[] scores;
	
	public DouniuRoomHistory(){
		
	}
	
	public DouniuRoomHistory(String roomCheckId, String startDate, int chapterNums, String[] userNames, int[] scores){
		this.roomCheckId = roomCheckId;
		this.startDate = startDate;
		this.chapterNums = chapterNums;
		this.userNames = userNames;
		this.scores = scores;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		roomCheckId = in.readString();
		startDate = in.readString();
		chapterNums = in.readInt();
		userNames = in.readStringArray();
		scores = in.readIntArray();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		out.writeString(getRoomCheckId());
		out.writeString(getStartDate());
		out.writeInt(getChapterNums());
		out.writeStringArray(getUserNames());
		out.writeIntArray(getScores());
	}

	public String getRoomCheckId() {
		return roomCheckId;
	}
	
	public void setRoomCheckId(String roomCheckId) {
		this.roomCheckId = roomCheckId;
	}

	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getChapterNums() {
		return chapterNums;
	}
	
	public void setChapterNums(int chapterNums) {
		this.chapterNums = chapterNums;
	}

	public String[] getUserNames() {
		return userNames;
	}
	
	public void setUserNames(String[] userNames) {
		this.userNames = userNames;
	}

	public int[] getScores() {
		return scores;
	}
	
	public void setScores(int[] scores) {
		this.scores = scores;
	}

	@Override
	public String toString() {
		return "DouniuRoomHistory [roomCheckId=" + roomCheckId + ", startDate="
				+ startDate + ", chapterNums=" + chapterNums + ", userNames="
				+ Arrays.toString(userNames) + ", scores="
				+ Arrays.toString(scores) + "]";
	}

	@Override
	public final int getMessageType() {
		return TYPE;
	}

	@Override
	public final int getMessageId() {
		return ID;
	}
}
