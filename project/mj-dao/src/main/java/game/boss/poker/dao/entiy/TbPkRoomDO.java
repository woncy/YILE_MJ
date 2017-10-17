package game.boss.poker.dao.entiy;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import mj.data.Config;
@Entity(name="tb_pk_room")
public class TbPkRoomDO{

	/**牌局id*/
	@Id
	@GeneratedValue
	private Integer id;
	/**创建用户id*/
	@OneToOne(fetch=FetchType.LAZY)
	private UserDO2 create_user;
	/**用户数*/
	@Column(name="user_max")
	private Integer userMax;
	/**牌局uuid*/
	@Generated(value = { "uuid" })
	private String uuid;
	/**房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同*/
	@Column(name="room_check_id")
	private String roomCheckId;
	@Column(name="start_date")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date startDate;
	@Column(name="end_date")
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private java.util.Date endDate;
	@Column
	private boolean start;
	/**正常结束*/
	@Column
	private boolean end;
	@Column
	private Integer version;
	@Column(name="scene_id")
	private Integer sceneId;
	/**局数*/
	@Column(name="chapter_nums")
	private Integer chapterNums;
	/**配置*/
	@Column
	private String config;
	@Column
	private int initScore;
	
	@OneToMany(mappedBy="room",fetch=FetchType.LAZY)
	private List<TbPkRoomUserDO> roomUsers;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	public int getInitScore() {
		return initScore;
	}
	public void setInitScore(int initScore) {
		this.initScore = initScore;
	}
	public UserDO2 getCreate_user() {
		return create_user;
	}
	public void setCreate_user(UserDO2 create_user) {
		this.create_user = create_user;
	}
	public List<TbPkRoomUserDO> getRoomUsers() {
		return roomUsers;
	}
	public void setRoomUsers(List<TbPkRoomUserDO> roomUsers) {
		this.roomUsers = roomUsers;
	}
	public UserDO2 getUser() {
		return create_user;
	}
	public void setUser(UserDO2 user) {
		this.create_user = user;
	}
	
	public Integer getUserMax() {
		return userMax;
	}
	public void setUserMax(Integer userMax) {
		this.userMax = userMax;
	}
	@Generated(value = { "uuid" })
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getRoomCheckId() {
		return roomCheckId;
	}
	public void setRoomCheckId(String roomCheckId) {
		this.roomCheckId = roomCheckId;
	}
	public java.util.Date getStartDate() {
		return startDate;
	}
	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
	}
	public java.util.Date getEndDate() {
		return endDate;
	}
	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
	}
	public boolean isStart() {
		return start;
	}
	public void setStart(boolean start) {
		this.start = start;
	}
	public boolean isEnd() {
		return end;
	}
	public void setEnd(boolean end) {
		this.end = end;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getSceneId() {
		return sceneId;
	}
	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
	}
	public Integer getChapterNums() {
		return chapterNums;
	}
	public void setChapterNums(Integer chapterNums) {
		this.chapterNums = chapterNums;
	}

	public Config getPokerConfig(){
		String config_str = "";
		Map<String,String> map = new TreeMap<>();
		if(config!=null && !"".equals(config)){
			config_str = config.substring(config.lastIndexOf("{")+1).substring(0, config.lastIndexOf("}")-1);
			String items[] = config_str.split(", ");
			
			for(String str:items){
				String k_v[] = str.split("=");
				if(k_v[0]!=null && !k_v[0].equals(""))
					map.put(k_v[0], k_v[1]);
			}
			return new Config(map);
			
		}else{
			return null;
		}
	}
	
	public void setConfig(Config config){
		this.config = config.getOptions().toString();
	}
	
	public String getConfig() {
		return config;
	}
	public void setConfig(String config) {
		this.config = config;
	}
	@Override
	public String toString() {
		return "TbPkRoomDO [id=" + id + ", create_user=" + create_user + ", userMax=" + userMax + ", uuid=" + uuid
				+ ", roomCheckId=" + roomCheckId + ", startDate=" + startDate + ", endDate=" + endDate + ", start="
				+ start + ", end=" + end + ", version=" + version + ", sceneId=" + sceneId + ", chapterNums="
				+ chapterNums + ", config=" + config + "]";
	}
	
	
	
	
	
	

}
