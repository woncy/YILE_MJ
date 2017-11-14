package mj.net.message.game.douniu;

import java.io.IOException;
import java.util.ArrayList;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;
import com.sun.tools.classfile.StackMapTable_attribute.chop_frame;

import mj.data.MessageUtil;
import mj.net.message.game.GameUserInfo;

/**
 * 斗牛创建房间同步消息
 * 同步游戏信息
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DNGameRoomInfo extends AbstractMessage{
	public static final int TYPE			 =  2;
	public static final int ID				 =  5;
	private int maxChapterNum;
	private int currentChapterNum;
	private String roomNo;
	private int roomId;
	private int createUserId;	//创建者id
	private ArrayList<GameUserInfo> users = new ArrayList<GameUserInfo>();
	private DNChapterInfo chapterInfo;
	private boolean isBeforeFirstStart;//是不是第一次开始游戏之前
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.maxChapterNum = in.readInt();
		this.currentChapterNum = in.readInt();
		this.roomNo = in.readString();
		this.roomId = in.readInt();
		this.createUserId = in.readInt();
		MessageUtil.decodeList(in, users, GameUserInfo.class);
		this.chapterInfo = new DNChapterInfo();
		chapterInfo.decode(in);
		this.isBeforeFirstStart = in.readBoolean();

	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeInt(maxChapterNum);
		out.writeInt(currentChapterNum);
		out.writeString(roomNo);
		out.writeInt(roomId);
		out.writeInt(createUserId);
		MessageUtil.encodeList(out, users);
		chapterInfo.encode(out);
		out.writeBoolean(isBeforeFirstStart);
	}
	public boolean isBeforeFirstStart() {
		return isBeforeFirstStart;
	}
	public void setBeforeFirstStart(boolean isBeforeFirstStart) {
		this.isBeforeFirstStart = isBeforeFirstStart;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}
	@Override
	public int getMessageId() {
		return ID;
	}
	
	
	public ArrayList<GameUserInfo> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<GameUserInfo> users) {
		this.users = users;
	}
	
	
	public int getMaxChapterNum() {
		return maxChapterNum;
	}
	public void setMaxChapterNum(int maxChapterNum) {
		this.maxChapterNum = maxChapterNum;
	}
	public int getCurrentChapterNum() {
		return currentChapterNum;
	}
	public void setCurrentChapterNum(int currentChapterNum) {
		this.currentChapterNum = currentChapterNum;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public int getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}
	public DNChapterInfo getChapterInfo() {
		return chapterInfo;
	}
	public void setChapterInfo(DNChapterInfo chapterInfo) {
		this.chapterInfo = chapterInfo;
	}
	@Override
	public String toString() {
		return "DNGameRoomInfo [maxChapterNum=" + maxChapterNum + ", currentChapterNum=" + currentChapterNum
				+ ", roomNo=" + roomNo + ", roomId=" + roomId + ", createUserId=" + createUserId + ", users=" + users
				+ ", chapterInfo=" + chapterInfo + ", isBeforeFirstStart=" + isBeforeFirstStart + "]";
	}
	
	

}
