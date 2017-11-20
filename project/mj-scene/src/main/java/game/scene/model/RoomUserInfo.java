package game.scene.model;

public class RoomUserInfo {
	private int locationIndex;
	private int zimo;		//自摸次数
	private int fangpao;	//放炮次数
	private int jiepao;	//接炮次数
	private int angang;	//暗杠次数
	private int minggang;	//明杠次数
	private int score = 0  ;
	public RoomUserInfo() {
	}
	public int getLocationIndex() {
		return locationIndex;
	}
	public int getZimo() {
		return zimo;
	}
	public int getFangpao() {
		return fangpao;
	}
	public int getJiepao() {
		return jiepao;
	}
	public int getAngang() {
		return angang;
	}
	public int getMinggang() {
		return minggang;
	}
	public int getScore() {
		return score;
	}
	public void addLocationIndex(){
		this.locationIndex++;
	}
	public void addZimo(){
		this.zimo++;
	}
	public void addFangpao(){
		this.fangpao++;
	}
	public void addJiepao(){
		this.jiepao++;
	}
	public void addAngang(int anGang){
		this.angang+=anGang;
	}
	public void addMinggang(int mingGang){
		this.minggang+=mingGang;
	}
	public void addScore(int score){
		this.score+=score;
	}
	
}
