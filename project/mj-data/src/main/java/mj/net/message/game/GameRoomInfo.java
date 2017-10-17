package mj.net.message.game;

import java.io.IOException;
import java.util.ArrayList;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;

import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 同步游戏信息
 * 0东 1南 2西 3北 逆时针顺序
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class GameRoomInfo extends AbstractMessage{
	public static final int TYPE			 = 1;
	public static final int ID				 = 7;
	
	private java.util.ArrayList<mj.net.message.game.GameUserInfo> sceneUser;
	private boolean start;
	private String roomCheckId;
	private int leftChapterNums;
	private int createUserId;
	private mj.net.message.game.MajiangChapterMsg chapter;
	
	public  int chapterMax = 0;//局数
    public  boolean isHuiEr = false;//带混
    public  boolean isGangDaiPao = false;//杠带跑 ``
    public  boolean isDaiZiPai = false;//带字牌 ``
    public  boolean isQiDuiFanBei = false;//7对翻倍 7对胡本来就是有的 ``
    public  boolean isZhuangJiaDi = false;//庄加底 ``
    public  boolean isGangKaiFan = false;//杠开翻倍 ``
    public  int xuanPaoCount = 0 ;//选跑局数 4局 每局``
    public  boolean isFangPao = false;//点炮胡  `
    public  boolean  currentChapterPao = false; 
    public int user_num;
    private int state;
    
    
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getChapterMax() {
		return chapterMax;
	}

	public void setChapterMax(int chapterMax) {
		this.chapterMax = chapterMax;
	}

	public boolean isHuiEr() {
		return isHuiEr;
	}

	public void setHuiEr(boolean isHuiEr) {
		this.isHuiEr = isHuiEr;
	}

	public boolean isGangDaiPao() {
		return isGangDaiPao;
	}

	public void setGangDaiPao(boolean isGangDaiPao) {
		this.isGangDaiPao = isGangDaiPao;
	}

	public boolean isDaiZiPai() {
		return isDaiZiPai;
	}

	public void setDaiZiPai(boolean isDaiZiPai) {
		this.isDaiZiPai = isDaiZiPai;
	}

	public boolean isQiDuiFanBei() {
		return isQiDuiFanBei;
	}

	public void setQiDuiFanBei(boolean isQiDuiFanBei) {
		this.isQiDuiFanBei = isQiDuiFanBei;
	}

	public boolean isZhuangJiaDi() {
		return isZhuangJiaDi;
	}

	public void setZhuangJiaDi(boolean isZhuangJiaDi) {
		this.isZhuangJiaDi = isZhuangJiaDi;
	}

	public boolean isGangKaiFan() {
		return isGangKaiFan;
	}

	public void setGangKaiFan(boolean isGangKaiFan) {
		this.isGangKaiFan = isGangKaiFan;
	}

	public int getXuanPaoCount() {
		return xuanPaoCount;
	}

	public void setXuanPaoCount(int xuanPaoCount) {
		this.xuanPaoCount = xuanPaoCount;
	}

	public boolean isFangPao() {
		return isFangPao;
	}

	public void setFangPao(boolean isFangPao) {
		this.isFangPao = isFangPao;
	}

	public GameRoomInfo(){
		
	}
	
	
	
	public GameRoomInfo(ArrayList<GameUserInfo> sceneUser, boolean start, String roomCheckId, int leftChapterNums,
			int createUserId, MajiangChapterMsg chapter, int chapterMax, boolean isHuiEr, boolean isGangDaiPao,
			boolean isDaiZiPai, boolean isQiDuiFanBei, boolean isZhuangJiaDi, boolean isGangKaiFan, int xuanPaoCount,
			boolean isFangPao, boolean currentChapterPao, int user_num, int state) {
		super();
		this.sceneUser = sceneUser;
		this.start = start;
		this.roomCheckId = roomCheckId;
		this.leftChapterNums = leftChapterNums;
		this.createUserId = createUserId;
		this.chapter = chapter;
		this.chapterMax = chapterMax;
		this.isHuiEr = isHuiEr;
		this.isGangDaiPao = isGangDaiPao;
		this.isDaiZiPai = isDaiZiPai;
		this.isQiDuiFanBei = isQiDuiFanBei;
		this.isZhuangJiaDi = isZhuangJiaDi;
		this.isGangKaiFan = isGangKaiFan;
		this.xuanPaoCount = xuanPaoCount;
		this.isFangPao = isFangPao;
		this.currentChapterPao = currentChapterPao;
		this.user_num = user_num;
		this.state = state;
	}

	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		
		int sceneUserLen = in.readInt();
		if(sceneUserLen == -1){
			sceneUser = null;
		}else{
			sceneUser = new java.util.ArrayList<mj.net.message.game.GameUserInfo>(sceneUserLen);
			for(int i = 0; i < sceneUserLen; i++){
				GameUserInfo sceneUserItem = new GameUserInfo();
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
			chapter = new mj.net.message.game.MajiangChapterMsg();
			chapter.decode(in);
		}else{
			chapter = null;
		}
		this.user_num = in.readInt();
		
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		
		if(sceneUser == null){
			out.writeInt(-1);
		}else{
			java.util.ArrayList<mj.net.message.game.GameUserInfo> sceneUserList = getSceneUser();
			int sceneUserLen = sceneUserList.size();
			out.writeInt(sceneUserLen);
			for(GameUserInfo sceneUserItem: sceneUserList){
				sceneUserItem.encode(out);
			}
		}
		out.writeBoolean(getStart());
		out.writeString(getRoomCheckId());
		out.writeInt(getLeftChapterNums());
		out.writeInt(getCreateUserId());
		mj.net.message.game.MajiangChapterMsg chapterItem = getChapter();
		if(chapterItem  == null){
			out.writeBoolean(false);
		}else{
			out.writeBoolean(true);
			chapterItem.encode(out);
		}
		out.writeInt(chapterMax);
		out.writeBoolean(isHuiEr);
		out.writeBoolean(isGangDaiPao);
		out.writeBoolean(isDaiZiPai);
		out.writeBoolean(isQiDuiFanBei);
		out.writeBoolean(isZhuangJiaDi);
		out.writeBoolean(isGangKaiFan);
		out.writeInt(xuanPaoCount);
		out.writeBoolean(isFangPao);
		out.writeBoolean(currentChapterPao);
		out.writeInt(user_num);
		out.writeInt(state);
		
	}
	

	public int getUser_num() {
		return user_num;
	}

	public void setUser_num(int user_num) {
		this.user_num = user_num;
	}

	public boolean isCurrentChapterPao() {
		return currentChapterPao;
	}

	public void setCurrentChapterPao(boolean currentChapterPao) {
		this.currentChapterPao = currentChapterPao;
	}

	public java.util.ArrayList<mj.net.message.game.GameUserInfo> getSceneUser() {
		return sceneUser;
	}
	
	public void setSceneUser(java.util.ArrayList<mj.net.message.game.GameUserInfo> sceneUser) {
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

	public int getLeftChapterNums() {
		return leftChapterNums;
	}
	
	public void setLeftChapterNums(int leftChapterNums) {
		this.leftChapterNums = leftChapterNums;
	}

	public int getCreateUserId() {
		return createUserId;
	}
	
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
	}

	public mj.net.message.game.MajiangChapterMsg getChapter() {
		return chapter;
	}
	
	public void setChapter(mj.net.message.game.MajiangChapterMsg chapter) {
		this.chapter = chapter;
	}

	
	public void addSceneUser(GameUserInfo sceneUser) {
		if(this.sceneUser == null){
			this.sceneUser = new java.util.ArrayList<mj.net.message.game.GameUserInfo>();
		}
		this.sceneUser.add(sceneUser);
	}
	
	@Override
	public String toString() {
		return "GameRoomInfo [sceneUser=" + sceneUser + ",start=" + start + ",roomCheckId=" + roomCheckId + ",leftChapterNums=" + leftChapterNums + ",createUserId=" + createUserId + ",chapter=" + chapter + ", ]";
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
