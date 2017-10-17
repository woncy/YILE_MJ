package  game.boss.poker.dao.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name="tb_pk_chapter")
public class TbPkChapterDO{
	@Id
	@GeneratedValue
	private int id;
	@Column
	private Integer num;
	@ManyToOne
	private TbPkRoomDO room;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public TbPkRoomDO getRoom() {
		return room;
	}
	public void setRoom(TbPkRoomDO room) {
		this.room = room;
	}
	@Override
	public String toString() {
		return "TbPkChapterDO [id=" + id + ", num=" + num + ", room=" + room + "]";
	}
	
	

	
}
