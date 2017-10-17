package game.boss.poker.dao.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity(name="tb_pk_room_user")
public class TbPkRoomUserDO {
	@Id
	@GeneratedValue
	private int id;
	/**房间id*/
	@ManyToOne(fetch=FetchType.LAZY)
	private TbPkRoomDO room;
	/**最终分数，总得分*/
	@Column(name="end_score")
	private Integer endScore;
	@Column
	private Integer locationIndex;
//**赢牌次数*//*
	@Column
	private Integer winCount;
	@ManyToOne(fetch=FetchType.LAZY)
	private UserDO2 user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public TbPkRoomDO getRoom() {
		return room;
	}
	public void setRoom(TbPkRoomDO room) {
		this.room = room;
	}
	public Integer getEndScore() {
		return endScore;
	}
	public void setEndScore(Integer endScore) {
		this.endScore = endScore;
	}
	public UserDO2 getUser() {
		return user;
	}
	public void setUser(UserDO2 user) {
		this.user = user;
	}
	public Integer getLocationIndex() {
		return locationIndex;
	}
	public void setLocationIndex(Integer locationIndex) {
		this.locationIndex = locationIndex;
	}
	public int getWinCount() {
		return winCount;
	}
	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}
	@Override
	public String toString() {
		return "TbPkRoomUserDO [id=" + id + ", room=" + room + ", endScore=" + endScore + ", locationIndex="
				+ locationIndex + ", winCount=" + "winCount" + ", user=" + user + "]";
	}
	

}
