package mj.net.message.game.pdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

import mj.data.PdkConfig;
import mj.net.message.login.OptionEntry;

public class PdkGameRoomInfo extends AbstractMessage{
	public static final int TYPE = 4;
	public static final int ID = 2;
	
	private String roomNO; //  房间号
	private int currentChapterNum; //当前局数
	private int maxChapterNum;// 此房间最大局数
	private boolean isGameStart; //游戏是否开始了
	private  PdkGameChapterInfo chapterInfo; //牌局信息
	private List<PdkGameUserInfo> users;	//用户
	private java.util.ArrayList<mj.net.message.login.OptionEntry> options;//房间配置
	
	private PdkConfig config;//此属性是为了和options转换方便,此对象不在消息传输之中
	
	public PdkGameRoomInfo() {
		super();
	}
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.roomNO = in.readString();
		this.currentChapterNum = in.readInt();
		this.maxChapterNum = in.readInt();
		this.isGameStart = in.readBoolean();
		this.chapterInfo = new PdkGameChapterInfo();
		this.chapterInfo.decode(in);
		int usersLength = in.readInt();
		if(usersLength == -1){
			users = null;
		}else{
			this.users = new ArrayList<PdkGameUserInfo>();
			for(int i=0;i<usersLength;i++){
				PdkGameUserInfo user = new PdkGameUserInfo();
				user.decode(in);
				users.add(user);
			}
		}
		int optionsLength = in.readInt();
		if(optionsLength==-1){
			this.options = null;
		}else{
			this.options = new ArrayList<OptionEntry>();
			for(int i=0;i<optionsLength;i++){
				OptionEntry opt = new OptionEntry();
				opt.decode(in);
				options.add(opt);
			}
		}
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(roomNO);
		out.writeInt(currentChapterNum);
		out.writeInt(maxChapterNum);
		out.writeBoolean(isGameStart);
		chapterInfo.encode(out);
		
		int usersLength = -1;
		if(users!=null){
			usersLength = users.size();
		}
		out.writeInt(usersLength);
		for (int i = 0; i < usersLength; i++) {
			users.get(i).encode(out);
		}
		int opsLenth = -1;
		if(options!=null){
			opsLenth = options.size();
		}
		out.writeInt(opsLenth);
		for (int i = 0; i < opsLenth; i++) {
			options.get(i).encode(out);
		}
	}
	@Override
	public int getMessageId() {
		// TODO 自动生成的方法存根
		return ID;
	}
	@Override
	public int getMessageType() {
		// TODO 自动生成的方法存根
		return TYPE;
	}
	
	
	public String getRoomNO() {
		return roomNO;
	}
	public void setRoomNO(String roomNO) {
		this.roomNO = roomNO;
	}
	public int getCurrentChapterNum() {
		return currentChapterNum;
	}
	public void setCurrentChapterNum(int currentChapterNum) {
		this.currentChapterNum = currentChapterNum;
	}
	public int getMaxChapterNum() {
		return maxChapterNum;
	}
	public void setMaxChapterNum(int maxChapterNum) {
		this.maxChapterNum = maxChapterNum;
	}
	public boolean isGameStart() {
		return isGameStart;
	}
	public void setGameStart(boolean isGameStart) {
		this.isGameStart = isGameStart;
	}
	public PdkGameChapterInfo getChapterInfo() {
		return chapterInfo;
	}
	public void setChapterInfo(PdkGameChapterInfo chapterInfo) {
		this.chapterInfo = chapterInfo;
	}
	public List<PdkGameUserInfo> getUsers() {
		return users;
	}
	public void setUsers(List<PdkGameUserInfo> users) {
		this.users = users;
	}
	public java.util.ArrayList<mj.net.message.login.OptionEntry> getOptions() {
		return options;
	}
	public void setOptions(java.util.ArrayList<mj.net.message.login.OptionEntry> options) {
		this.options = options;
		config = new PdkConfig(options);
	}
	public PdkConfig getConfig() {
		return config;
	}
	public void setConfig(PdkConfig config) {
		this.config = config;
		this.options = config.getListOpteions();
	}
	@Override
	public String toString() {
		return "PdkGameRoomInfo [roomNO=" + roomNO + ", currentChapterNum=" + currentChapterNum + ", maxChapterNum="
				+ maxChapterNum + ", isGameStart=" + isGameStart + ", chapterInfo=" + chapterInfo + ", users=" + users
				+ ", options=" + options + ", config=" + config + "]";
	}
	
	
	
	
	
	
	
	
	
	
	
}
