package mj.net.message.game.douniu;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 斗牛创建房间同步消息
 * 同步游戏信息
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuGameRoomInfo extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 24;
	
	private java.util.ArrayList<mj.net.message.game.douniu.DouniuGameUserInfo> sceneUser;
	private boolean start;
	private String roomCheckId;
	/**
	 * dangqian局数
	 */
	private int leftChapterNums;
	private int createUserId;
	private mj.net.message.game.douniu.DouniuChapterMsg chapter;
	/**
	 * 局数
	 */
	private int chapterMax;
	
	/**
	 * 初始分
	 */
	private int initScore;
	/**
	 * 玩家的数量
	 */
	private int userNum;
	/**
	 * 是否是抢庄模式
	 */
	private boolean moShi;
	/**
	 * 牛牛倍数
	 */
	private int niuNum;
	
	public DouniuGameRoomInfo(){
		
	}
	
	public DouniuGameRoomInfo(java.util.ArrayList<mj.net.message.game.douniu.DouniuGameUserInfo> sceneUser, boolean start, String roomCheckId, int leftChapterNums, int createUserId, mj.net.message.game.douniu.DouniuChapterMsg chapter, int chapterMax, String isType, int initScore, int userNum, boolean moShi, int niuNum){
		this.sceneUser = sceneUser;
		this.start = start;
		this.roomCheckId = roomCheckId;
		this.leftChapterNums = leftChapterNums;
		this.createUserId = createUserId;
		this.chapter = chapter;
		this.chapterMax = chapterMax;
		this.initScore = initScore;
		this.userNum = userNum;
		this.moShi = moShi;
		this.niuNum = niuNum;
	}
	
	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		
		int sceneUserLen = in.readInt();
		if(sceneUserLen == -1){
			sceneUser = null;
		}else{
			sceneUser = new java.util.ArrayList<mj.net.message.game.douniu.DouniuGameUserInfo>(sceneUserLen);
			for(int i = 0; i < sceneUserLen; i++){
				DouniuGameUserInfo sceneUserItem = new DouniuGameUserInfo();
				sceneUserItem.decode(in);
				sceneUser.add(sceneUserItem);
			}
		}
		start = in.readBoolean();
		roomCheckId = in.readString();
		leftChapterNums = in.readInt();
		createUserId = in.readInt();
		
		boolean chapterIsNotNull = in.readBoolean();
		if(chapterIsNotNull){
			chapter = new mj.net.message.game.douniu.DouniuChapterMsg();
			chapter.decode(in);
		}else{
			chapter = null;
		}
		chapterMax = in.readInt();
		initScore = in.readInt();
		userNum = in.readInt();
		moShi = in.readBoolean();
		niuNum = in.readInt();
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		
		if(sceneUser == null){
			out.writeInt(-1);
		}else{
			java.util.ArrayList<mj.net.message.game.douniu.DouniuGameUserInfo> sceneUserList = getSceneUser();
			int sceneUserLen = sceneUserList.size();
			out.writeInt(sceneUserLen);
			for(DouniuGameUserInfo sceneUserItem: sceneUserList){
				sceneUserItem.encode(out);
			}
		}
		out.writeBoolean(getStart());
		out.writeString(getRoomCheckId());
		out.writeInt(getLeftChapterNums());
		out.writeInt(getCreateUserId());
		mj.net.message.game.douniu.DouniuChapterMsg chapterItem = getChapter();
		if(chapterItem  == null){
			out.writeBoolean(false);
		}else{
			out.writeBoolean(true);
			chapterItem.encode(out);
		}
		out.writeInt(getChapterMax());
		out.writeInt(getInitScore());
		out.writeInt(getUserNum());
		out.writeBoolean(getMoShi());
		out.writeInt(getNiuNum());
	} 

	public java.util.ArrayList<mj.net.message.game.douniu.DouniuGameUserInfo> getSceneUser() {
		return sceneUser;
	}
	
	public void setSceneUser(java.util.ArrayList<mj.net.message.game.douniu.DouniuGameUserInfo> sceneUser) {
		this.sceneUser = sceneUser;
	}

	public boolean getStart() {
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

	/**
	 * 剩余局数
	 */
	public int getLeftChapterNums() {
		return leftChapterNums;
	}
	/**
	 * 剩余局数
	 */
	public void setLeftChapterNums(int leftChapterNums) {
		this.leftChapterNums = leftChapterNums;
	}

	public int getCreateUserId() {
		return createUserId;
	}
	
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public mj.net.message.game.douniu.DouniuChapterMsg getChapter() {
		return chapter;
	}
	
	public void setChapter(mj.net.message.game.douniu.DouniuChapterMsg chapter) {
		this.chapter = chapter;
	}

	/**
	 * 局数
	 */
	public int getChapterMax() {
		return chapterMax;
	}
	
	/**
	 * 局数
	 */
	public void setChapterMax(int chapterMax) {
		this.chapterMax = chapterMax;
	}


	/**
	 * 初始分
	 */
	public int getInitScore() {
		return initScore;
	}
	
	/**
	 * 初始分
	 */
	public void setInitScore(int initScore) {
		this.initScore = initScore;
	}

	/**
	 * 玩家的数量
	 */
	public int getUserNum() {
		return userNum;
	}
	
	/**
	 * 玩家的数量
	 */
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	/**
	 * 是否是庄家模式
	 */
	public boolean getMoShi() {
		return moShi;
	}
	
	/**
	 * 是否是庄家模式
	 */
	public void setMoShi(boolean moShi) {
		this.moShi = moShi;
	}

	/**
	 * 牛牛倍数
	 */
	public int getNiuNum() {
		return niuNum;
	}
	
	/**
	 * 牛牛倍数
	 */
	public void setNiuNum(int niuNum) {
		this.niuNum = niuNum;
	}

	
	public void addSceneUser(DouniuGameUserInfo sceneUser) {
		if(this.sceneUser == null){
			this.sceneUser = new java.util.ArrayList<mj.net.message.game.douniu.DouniuGameUserInfo>();
		}
		this.sceneUser.add(sceneUser);
	}
	
	@Override
	public String toString() {
		return "DouniuGameRoomInfo [sceneUser=" + sceneUser + ",start=" + start + ",roomCheckId=" + roomCheckId + ",leftChapterNums=" + leftChapterNums + ",createUserId=" + createUserId + ",chapter=" + chapter + ",chapterMax=" + chapterMax  + ",initScore=" + initScore + ",userNum=" + userNum + ",moShi=" + moShi + ",niuNum=" + niuNum + ", ]";
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
