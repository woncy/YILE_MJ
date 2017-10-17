package mj.net.message.game.zjh;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

/**
 * @author 13323659953@163.com
 *
 */
public class GameUserinfo2 extends AbstractMessage{
	public static final int TYPE			 = 3;
	public static final int ID				 = 32;
	
	private String userName;
	private String avatar;
	/**
	 * 0:女生,1:男生,2:未知
	 */
	private int sex;
	private int score;
	private int locationIndex;
	private int userId;
	private boolean online;
	private String ip;
	
	public GameUserinfo2(){
		
	}
	
	public GameUserinfo2(String userName, String avatar, int sex, int score, int locationIndex, int userId, boolean online, String ip){
		this.userName = userName;
		this.avatar = avatar;
		this.sex = sex;
		this.score = score;
		this.locationIndex = locationIndex;
		this.userId = userId;
		this.online = online;
		this.ip = ip;
		
	}
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		userName = in.readString();
		avatar = in.readString();
		sex = in.readInt();
		score = Integer.parseInt(in.readString());
		locationIndex = in.readInt();
		userId = in.readInt();
		online = in.readBoolean();
		ip = in.readString();
		
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(getUserName());
		out.writeString(getAvatar());
		out.writeInt(getSex());
		out.writeString(getScore()+"");
		out.writeInt(getLocationIndex());
		out.writeInt(getUserId());
		out.writeBoolean(getOnline());
		out.writeString(getIp());
	}	
	
	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}


	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean getOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public static int getType() {
		return TYPE;
	}

	@Override
	public int getMessageType() {
		return TYPE;
	}
	@Override
	public int getMessageId() {
		return ID;
	}

	@Override
	public String toString() {
		return "GameUserinfo2 [userName=" + userName + ", avatar=" + avatar + ", sex=" + sex + ", score=" + score + ", locationIndex=" + locationIndex + ", userId=" + userId + ", online=" + online
				+ ", ip=" + ip + "]";
	}
	
}
