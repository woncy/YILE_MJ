package  game.boss.poker.dao.entiy;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity(name="user")
public class UserDO2 {
	@GeneratedValue
	@Id
	private int id;
	/**昵称*/
	@Column
	private String name;
	@Column(name="open_id")
	private String openId;
	/**用户唯一uuid*/
	@Column
	private String uuid;
	/**用户头像地址*/
	@Column
	private String avatar;
	/**0:女生,1:男生,2:未知*/
	@Column
	private int sex;
	@Column(name="create_date")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date createDate;
	@Column(name="update_date")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date updateDate;
	@Column
	private int version;
	@Column
	private int gold;
	@Column
	private String mobile;
	@Column(name="login_token")
	private String loginToken;
	@Column(name="history_gold")
	private int historyGold;
	/**用户代理级别*/
	@Column
	private int level;
	/**ip*/
	@Column
	private String ip;
	/**ip*/
	@Column
	private double longitude;
	/**ip*/
	@Column
	private double latitude;
	/**密码*/
	@Column
	private String password;
	
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	private List<TbPkRoomUserDO> rooms;
	
	public List<TbPkRoomUserDO> getRooms() {
		return rooms;
	}
	public void setRooms(List<TbPkRoomUserDO> rooms) {
		this.rooms = rooms;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
	public java.util.Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	public java.util.Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getLoginToken() {
		return loginToken;
	}
	public void setLoginToken(String loginToken) {
		this.loginToken = loginToken;
	}
	public int getHistoryGold() {
		return historyGold;
	}
	public void setHistoryGold(int historyGold) {
		this.historyGold = historyGold;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserDO2 [id=" + id + ", name=" + name + ", openId=" + openId + ", uuid=" + uuid + ", avatar=" + avatar
				+ ", sex=" + sex + ", createDate=" + createDate + ", updateDate=" + updateDate + ", version=" + version
				+ ", gold=" + gold + ", mobile=" + mobile + ", loginToken=" + loginToken + ", historyGold="
				+ historyGold + ", level=" + level + ", ip=" + ip + ", longitude=" + longitude + ", latitude="
				+ latitude + ", password=" + password + "]";
	}
	
	
	
	
	

}
