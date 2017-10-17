package mj.net.message.login;

import java.util.Arrays;

/**
 * 房間內每一局的信息
 * @author 13323659953@163.com
 *
 */
public class RoomEveryInfo {

	
	private String roomCheckId;
	
	private int curChareptNum;
	
	private int chareptNums;
	
	private String[] userNames;
	
	private int[] userIds;
	
	private int[] Scores;

	public RoomEveryInfo() {
		
	}
	
	
	public RoomEveryInfo(String roomCheckId, int curChareptNum, int chareptNums, String[] userNames, int[] userIds,
			int[] scores) {
		super();
		this.roomCheckId = roomCheckId;
		this.curChareptNum = curChareptNum;
		this.chareptNums = chareptNums;
		this.userNames = userNames;
		this.userIds = userIds;
		Scores = scores;
	}
	
	
	public String getRoomCheckId() {
		return roomCheckId;
	}
	public void setRoomCheckId(String roomCheckId) {
		this.roomCheckId = roomCheckId;
	}
	public int getCurChareptNum() {
		return curChareptNum;
	}
	public void setCurChareptNum(int curChareptNum) {
		this.curChareptNum = curChareptNum;
	}
	public int getChareptNums() {
		return chareptNums;
	}
	public void setChareptNums(int chareptNums) {
		this.chareptNums = chareptNums;
	}
	public String[] getUserNames() {
		return userNames;
	}
	public void setUserNames(String[] userNames) {
		this.userNames = userNames;
	}
	public int[] getUserIds() {
		return userIds;
	}
	public void setUserIds(int[] userIds) {
		this.userIds = userIds;
	}
	public int[] getScores() {
		return Scores;
	}
	public void setScores(int[] scores) {
		Scores = scores;
	}
	@Override
	public String toString() {
		return "RoomEveryInfo [roomCheckId=" + roomCheckId + ", curChareptNum=" + curChareptNum + ", chareptNums="
				+ chareptNums + ", userNames=" + Arrays.toString(userNames) + ", userIds=" + Arrays.toString(userIds)
				+ ", Scores=" + Arrays.toString(Scores) + "]";
	}
	
}
