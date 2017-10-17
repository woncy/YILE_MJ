package mj.net.message.game.zjh;

import java.io.IOException;
import java.util.ArrayList;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class GameRoomInfoT extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 6;
	private java.util.ArrayList<GameUserinfo2> sceneUser;//玩家信息
	private mj.net.message.game.zjh.ZJHChapterMsg zjhChapter;//一局
	
	private boolean start;//是否开始
	private String roomCheckId;//房间号
	private int ChapterNum;//当前局数
	public  int chapterMax = 0;//总局数
	private int initScore;//初始分
	private int userNum;//玩家数量
	private boolean moShi;//庄家模式
	private int daZhu;//单注
	
	public GameRoomInfoT() {
		
	}

	public GameRoomInfoT(ArrayList<GameUserinfo2> sceneUser, boolean start, String roomCheckId, int ChapterNum,
			 int chapterMax, int initScore, int userNum, boolean moShi, int daZhu,mj.net.message.game.zjh.ZJHChapterMsg zjhChapter) {
		super();
		this.sceneUser = sceneUser;
		this.start = start;
		this.roomCheckId = roomCheckId;
		this.ChapterNum = ChapterNum;
		this.chapterMax = chapterMax;
		this.initScore = initScore;
		this.userNum = userNum;
		this.moShi = moShi;
		this.daZhu = daZhu;
		this.zjhChapter = zjhChapter;
	}


	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		int sceneUserLen = in.readInt();
		if(sceneUserLen == -1){
			sceneUser = null;
		}else{
			sceneUser = new java.util.ArrayList<GameUserinfo2>(sceneUserLen);
			for(int i = 0; i < sceneUserLen; i++){
				GameUserinfo2 sceneUserItem = new GameUserinfo2();
				sceneUserItem.decode(in);
				sceneUser.add(sceneUserItem);
			}
		}
		this.start = in.readBoolean();
		this.roomCheckId = in.readString();
		this.userNum = in.readInt();
		this.ChapterNum = in.readInt();
		this.chapterMax = in.readInt();
		this.daZhu = in.readInt();
		this.initScore = in.readInt();
		this.moShi = in.readBoolean();
		boolean chapterIsNotNull = in.readBoolean();
		if(chapterIsNotNull){
			zjhChapter = new mj.net.message.game.zjh.ZJHChapterMsg();
			zjhChapter.decode(in);
		}else{
			zjhChapter = null;
		}
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		if(sceneUser == null){
			out.writeInt(-1);
		}else{
			java.util.ArrayList<GameUserinfo2> sceneUserList = getSceneUser();
			int sceneUserLen = sceneUserList.size();
			out.writeInt(sceneUserLen);
			for(GameUserinfo2 sceneUserItem: sceneUserList){
				sceneUserItem.encode(out);
			}
		}
		out.writeBoolean(isStart());
		out.writeString(getRoomCheckId());
		out.writeInt(getUserNum());
		out.writeInt(getChapterNum());
		out.writeInt(getChapterMax());
		out.writeInt(getDaZhu());
		out.writeInt(getInitScore());
		out.writeBoolean(isMoShi());
		mj.net.message.game.zjh.ZJHChapterMsg zjhChapter = getZjhChapter();
		if(zjhChapter  == null){
			out.writeBoolean(false);
		}else{
			zjhChapter.encode(out);
		}
		
		
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}
	@Override
	public int getMessageId() {
		return ID;
	}
	
	public void setSceneUser(java.util.ArrayList<GameUserinfo2> sceneUser) {
		this.sceneUser = sceneUser;
	}

	public mj.net.message.game.zjh.ZJHChapterMsg getZjhChapter() {
		return zjhChapter;
	}

	public void setZjhChapter(mj.net.message.game.zjh.ZJHChapterMsg zjhChapter) {
		this.zjhChapter = zjhChapter;
	}

	public java.util.ArrayList<GameUserinfo2> getSceneUser() {
		return sceneUser;
	}
	
	public void addSceneUser(GameUserinfo2 sceneUser) {
		if(this.sceneUser == null){
			this.sceneUser = new java.util.ArrayList<mj.net.message.game.zjh.GameUserinfo2>();
		}
		this.sceneUser.add(sceneUser);
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	public String getRoomCheckId() {
		return roomCheckId;
	}
	public void setRoomCheckId(String roomCheckId) {
		this.roomCheckId = roomCheckId;
	}
	public int getChapterNum() {
		return ChapterNum;
	}
	public void setChapterNums(int ChapterNum) {
		this.ChapterNum = ChapterNum;
	}
	public int getChapterMax() {
		return chapterMax;
	}
	
	public void setChapterMax(int chapterMax) {
		this.chapterMax = chapterMax;
	}
	public int getInitScore() {
		return initScore;
	}
	public void setInitScore(int initScore) {
		this.initScore = initScore;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public boolean isMoShi() {
		return moShi;
	}
	public void setMoShi(boolean moShi) {
		this.moShi = moShi;
	}
	public int getDaZhu() {
		return daZhu;
	}
	public void setDaZhu(int daZhu) {
		this.daZhu = daZhu;
	}

	@Override
	public String toString() {
		return "GameRoomInfoT [sceneUser=" + sceneUser + ", zjhChapter=" + zjhChapter + ", start=" + start
				+ ", roomCheckId=" + roomCheckId + ", ChapterNum=" + ChapterNum + ", chapterMax=" + chapterMax
				+ ", initScore=" + initScore + ", userNum=" + userNum + ", moShi=" + moShi + ", daZhu=" + daZhu + "]";
	}

	
}
