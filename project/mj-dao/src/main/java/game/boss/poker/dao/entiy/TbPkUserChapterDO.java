package  game.boss.poker.dao.entiy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name="tb_pk_user_chapter")
public class TbPkUserChapterDO {

	@Id
	@GeneratedValue
	private int id;
	@Column
	private String pais;
	@Column
	private Integer score;
	@ManyToOne
	private UserDO2 user;
	@ManyToOne
	private TbPkChapterDO chapter;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public UserDO2 getUser() {
		return user;
	}
	public void setUser(UserDO2 user) {
		this.user = user;
	}
	public TbPkChapterDO getChapter() {
		return chapter;
	}
	public void setChapter(TbPkChapterDO chapter) {
		this.chapter = chapter;
	}
	
	
	
	
}
