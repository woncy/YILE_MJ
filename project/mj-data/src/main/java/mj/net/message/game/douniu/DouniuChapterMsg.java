package mj.net.message.game.douniu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * 一局斗牛的信息
 * 
 * <b>生成器生成代码，请勿修改，扩展请继承</b>
 * @author isnowfox消息生成器
 */
public class DouniuChapterMsg extends AbstractMessage{
	public static final int TYPE			 = 5;
	public static final int ID				 = 20;
	
	private List<DouniuUserPlaceMsg> userPlace;
	/**
	 * 当前操作用户
	 */
	private int currentIndex;
	/**
	 * 局数, 0开始
	 */
	private int chapterNums;
	/**
	 * 局数, 0开始
	 */
	private int chapterNumsMax;
	/**
	 * 圈index 0 A 1B 2C 3D 4E 5F 6G 逆时针顺序
	 */
	private int quanIndex;
	/**
	 * 庄index 0 A 1B 2C 3D 4E 5F 6G 逆时针顺序
	 */
	private int zhuangIndex;
	//private DouniuFaPai douniuFaPai;
	//private DouniuOut douniuOut;
	//private mj.net.message.game.douniu.SyncDouniuTime syncDouniuTime;
	//private mj.net.message.game.douniu.DouniuGameChapterEnd douniugameChapterEnd;
	
	public DouniuChapterMsg(){
		
	}
	
	public DouniuChapterMsg(List<DouniuUserPlaceMsg> userPlace,
			int currentIndex, int chapterNums, int chapterNumsMax,
			int quanIndex, int zhuangIndex, DouniuFaPai douniuFaPai,
			DouniuGameChapterEnd douniugameChapterEnd) {
		super();
		this.userPlace = userPlace;
		this.currentIndex = currentIndex;
		this.chapterNums = chapterNums;
		this.chapterNumsMax = chapterNumsMax;
		this.quanIndex = quanIndex;
		this.zhuangIndex = zhuangIndex;
	//	this.douniuFaPai = douniuFaPai;
	//	this.douniugameChapterEnd = douniugameChapterEnd;
	}




	@Override
	public void decode(Input in)  throws IOException, ProtocolException {
		
		int userPlaceLen = in.readInt();
		if(userPlaceLen == -1){
			userPlace = null;
		}else{
			userPlace = new java.util.ArrayList<mj.net.message.game.douniu.DouniuUserPlaceMsg>(userPlaceLen);
			for(int i = 0; i < userPlaceLen; i++){
				DouniuUserPlaceMsg userPlaceItem = new DouniuUserPlaceMsg();
				userPlaceItem.decode(in);
				userPlace.add(userPlaceItem);
			}
		}
		currentIndex = in.readInt();
		chapterNums = in.readInt();
		chapterNumsMax = in.readInt();
		quanIndex = in.readInt();
		zhuangIndex = in.readInt();
		
		/*boolean douniuFaPaiIsNotNull = in.readBoolean();
		if(douniuFaPaiIsNotNull){
			douniuFaPai = new DouniuFaPai();
			douniuFaPai.decode(in);
		}else{
			douniuFaPai = null;
		}*/
		
	/*	boolean douniuOutIsNotNull = in.readBoolean();
		if(douniuOutIsNotNull){
			douniuOut = new DouniuOut();
			douniuOut.decode(in);
		}else{
			douniuOut = null;
		}*/
		
		/*boolean syncDouniuTimeIsNotNull = in.readBoolean();
		if(syncDouniuTimeIsNotNull){
			syncDouniuTime = new mj.net.message.game.douniu.SyncDouniuTime();
			syncDouniuTime.decode(in);
		}else{
			syncDouniuTime = null;
		}*/
		
		/*boolean douniugameChapterEndIsNotNull = in.readBoolean();
		if(douniugameChapterEndIsNotNull){
			douniugameChapterEnd = new mj.net.message.game.douniu.DouniuGameChapterEnd();
			douniugameChapterEnd.decode(in);
		}else{
			douniugameChapterEnd = null;
		}*/
	}

	@Override
	public void encode(Output out)  throws IOException, ProtocolException {
		
		if(userPlace == null){
			out.writeInt(-1);
		}else{
			List<mj.net.message.game.douniu.DouniuUserPlaceMsg> userPlaceList = getUserPlace();
			int userPlaceLen = userPlaceList.size();
			out.writeInt(userPlaceLen);
			for(DouniuUserPlaceMsg userPlaceItem: userPlaceList){
				userPlaceItem.encode(out);
			}
		}
		out.writeInt(getCurrentIndex());
		out.writeInt(getChapterNums());
		out.writeInt(getChapterNumsMax());
		out.writeInt(getQuanIndex());
		out.writeInt(getZhuangIndex());
	/*	DouniuFaPai douniuFaPaiItem = getDouniuFaPai();
		if(douniuFaPaiItem  == null){
			out.writeBoolean(false);
		}else{
			out.writeBoolean(true);
			douniuFaPaiItem.encode(out);
		}*/
	/*	DouniuOut douniuOutItem = getDouniuOut();
		if(douniuOutItem  == null){
			out.writeBoolean(false);
		}else{
			out.writeBoolean(true);
			douniuOutItem.encode(out);
		}*/
	/*	mj.net.message.game.douniu.SyncDouniuTime syncDouniuTimeItem = getSyncDouniuTime();
		if(syncDouniuTimeItem  == null){
			out.writeBoolean(false);
		}else{
			out.writeBoolean(true);
			syncDouniuTimeItem.encode(out);
		}*/
		/*mj.net.message.game.douniu.DouniuGameChapterEnd douniugameChapterEndItem = getDouniugameChapterEnd();
		if(douniugameChapterEndItem  == null){
			out.writeBoolean(false);
		}else{
			out.writeBoolean(true);
			douniugameChapterEndItem.encode(out);
		}*/
	}

	public List<mj.net.message.game.douniu.DouniuUserPlaceMsg> getUserPlace() {
		return userPlace;
	}
	
	public void setUserPlace(List<mj.net.message.game.douniu.DouniuUserPlaceMsg> userPlace) {
		this.userPlace = userPlace;
	}

	/**
	 * 当前操作用户
	 */
	public int getCurrentIndex() {
		return currentIndex;
	}
	
	/**
	 * 当前操作用户
	 */
	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	/**
	 * 局数, 0开始
	 */
	public int getChapterNums() {
		return chapterNums;
	}
	
	/**
	 * 局数, 0开始
	 */
	public void setChapterNums(int chapterNums) {
		this.chapterNums = chapterNums;
	}

	/**
	 * 局数, 0开始
	 */
	public int getChapterNumsMax() {
		return chapterNumsMax;
	}
	
	/**
	 * 局数, 0开始
	 */
	public void setChapterNumsMax(int chapterNumsMax) {
		this.chapterNumsMax = chapterNumsMax;
	}

	/**
	 * 圈index 0 A 1B 2C 3D 4E 5F 6G 逆时针顺序
	 */
	public int getQuanIndex() {
		return quanIndex;
	}
	
	/**
	 * 圈index 0 A 1B 2C 3D 4E 5F 6G 逆时针顺序
	 */
	public void setQuanIndex(int quanIndex) {
		this.quanIndex = quanIndex;
	}

	/**
	 * 庄index 0 A 1B 2C 3D 4E 5F 6G 逆时针顺序
	 */
	public int getZhuangIndex() {
		return zhuangIndex;
	}
	
	/**
	 * 庄index 0 A 1B 2C 3D 4E 5F 6G 逆时针顺序
	 */
	public void setZhuangIndex(int zhuangIndex) {
		this.zhuangIndex = zhuangIndex;
	}

	/*public DouniuFaPai getDouniuFaPai() {
		return douniuFaPai;
	}
	
	public void setDouniuFaPai(DouniuFaPai douniuFaPai) {
		this.douniuFaPai = douniuFaPai;
	}*/

	/*public DouniuOut getDouniuOut() {
		return douniuOut;
	}
	
	public void setDouniuOut(DouniuOut douniuOut) {
		this.douniuOut = douniuOut;
	}*/

	/*public mj.net.message.game.douniu.SyncDouniuTime getSyncDouniuTime() {
		return syncDouniuTime;
	}
	
	public void setSyncDouniuTime(mj.net.message.game.douniu.SyncDouniuTime syncDouniuTime) {
		this.syncDouniuTime = syncDouniuTime;
	}*/

	/*public mj.net.message.game.douniu.DouniuGameChapterEnd getDouniugameChapterEnd() {
		return douniugameChapterEnd;
	}
	
	public void setDouniugameChapterEnd(mj.net.message.game.douniu.DouniuGameChapterEnd douniugameChapterEnd) {
		this.douniugameChapterEnd = douniugameChapterEnd;
	}*/
	
	public void addUserPlace(DouniuUserPlaceMsg userPlace1) {
		if(this.userPlace == null){
			this.userPlace = new java.util.ArrayList<mj.net.message.game.douniu.DouniuUserPlaceMsg>();
		}
	     userPlace.add(userPlace1);
		 System.out.println("+用户的次数++++"+ userPlace.size());
	}
	

	@Override
	public String toString() {
		return "DouniuChapterMsg [userPlace=" + userPlace + ", currentIndex="
				+ currentIndex + ", chapterNums=" + chapterNums
				+ ", chapterNumsMax=" + chapterNumsMax + ", quanIndex="
				+ quanIndex + ", zhuangIndex=" + zhuangIndex +
				 "]";
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
