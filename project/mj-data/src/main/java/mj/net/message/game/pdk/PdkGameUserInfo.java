package mj.net.message.game.pdk;

import java.io.IOException;

import com.isnowfox.core.io.Input;
import com.isnowfox.core.io.Output;
import com.isnowfox.core.io.ProtocolException;
import com.isnowfox.core.net.message.AbstractMessage;

public class PdkGameUserInfo extends AbstractMessage{
	public static final int TYPE = 4;
	public static final int ID = 3;
	
	private String headUrl; //头像
	private String nickName;//昵称
	private int selfIndex;	//当前位置  逆时针 0，1，2.。。
	private int socre;	//积分
	private int sex; // 0为男，1为女
	private boolean online; //是否在线
	private int userId; //用户I
	
	private String ip;// 用户当前Ip
	
	/**
	 * 用户经纬度
	 */
	private String longitude;
	private String latitude;
	
	
	
	public PdkGameUserInfo() {
		super();
	}
	public PdkGameUserInfo(String headUrl, String nickName, int selfIndex, int socre, int sex, boolean online,
			int userId, String ip, String longitude, String latitude) {
		super();
		this.headUrl = headUrl;
		this.nickName = nickName;
		this.selfIndex = selfIndex;
		this.socre = socre;
		this.sex = sex;
		this.online = online;
		this.userId = userId;
		this.ip = ip;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	@Override
	public void decode(Input in) throws IOException, ProtocolException {
		this.headUrl = in.readString();
		this.nickName = in.readString();
		this.selfIndex = in.readInt();
		this.socre = in.readInt();
		this.sex = in.readInt();
		this.online = in.readBoolean();
		this.userId = in.readInt();
		this.ip = in.readString();
		this.longitude = in.readString();
		this.latitude = in.readString();
	}
	@Override
	public void encode(Output out) throws IOException, ProtocolException {
		out.writeString(headUrl);
		out.writeString(nickName);
		out.writeInt(selfIndex);
		out.writeInt(socre);
		out.writeInt(sex);
		out.writeBoolean(online);
		out.writeInt(userId);
		out.writeString(ip);
		out.writeString(longitude);
		out.writeString(latitude);
	}
	@Override
	public int getMessageId() {
		return ID;
	}
	@Override
	public int getMessageType() {
		return TYPE;
	}
	public String getHeadUrl() {
		return headUrl;
	}
	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getSelfIndex() {
		return selfIndex;
	}
	public void setSelfIndex(int selfIndex) {
		this.selfIndex = selfIndex;
	}
	public int getSocre() {
		return socre;
	}
	public void setSocre(int socre) {
		this.socre = socre;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	@Override
	public String toString() {
		return "PdkGameUserInfo [headUrl=" + headUrl + ", nickName=" + nickName + ", selfIndex=" + selfIndex
				+ ", socre=" + socre + ", sex=" + sex + ", online=" + online + ", userId=" + userId + ", ip=" + ip
				+ ", longitude=" + longitude + ", latitude=" + latitude + "]";
	}
	
	
}
