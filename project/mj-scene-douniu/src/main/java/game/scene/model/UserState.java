package game.scene.model;

import mj.net.message.game.douniu.UserRoomResult;

public class UserState {
	private boolean ready;
	private boolean qiangZhuang;
	
	private int winCount;
	private int faildCount;
	private int niuniuCount;
	private int zhadanCount;
	private int wuHuaNiuCount;
	private int siHuaNiuCount;
	private int wuXiaoNiuCount;
	private int score;
	private boolean isCommitDelRoom = false;
	
	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}

	public boolean isQiangZhuang() {
		return qiangZhuang;
	}

	public void setQiangZhuang(boolean qiangZhuang) {
		this.qiangZhuang = qiangZhuang;
	}
	

	

	public boolean isCommitDelRoom() {
		return isCommitDelRoom;
	}

	public void setCommitDelRoom(boolean isCommitDelRoom) {
		this.isCommitDelRoom = isCommitDelRoom;
	}

	public int getWinCount() {
		return winCount;
	}
	public void addWinCount(){
		this.winCount++;
	}

	public int getFaildCount() {
		return faildCount;
	}
	public void addFailCount(){
		this.faildCount++;
	}

	public int getNiuniuCount() {
		return niuniuCount;
	}
	public void addNiuNiuCount(){
		this.niuniuCount++;
	}

	public int getZhadanCount() {
		return zhadanCount;
	}
	public void addZhaDanCount(){
		this.zhadanCount++;
	}

	public int getWuHuaNiuCount() {
		return wuHuaNiuCount;
	}
	public void addWuHuaNiuCount(){
		this.wuHuaNiuCount++;
	}

	public int getSiHuaNiuCount() {
		return siHuaNiuCount;
	}
	public void addSiHuaNiuCount(){
		this.siHuaNiuCount++;
	}

	public int getWuXiaoNiuCount() {
		return wuXiaoNiuCount;
	}
	public void addWuXiaoNiuCount(){
		this.wuXiaoNiuCount++;
	}

	public int getScore() {
		return score;
	}
	
	public void addScore(int score){
		if(score>0){
			winCount++;
		}else if(score<0){
			faildCount++;
		}
		this.score += score;
	}
	
	public UserRoomResult toMessage(){
		UserRoomResult result = new UserRoomResult();
		result.setFaildCount(faildCount);
		result.setWinCount(winCount);
		result.setNiuniuCount(niuniuCount);
		result.setScore(score);
		result.setSiHuaNiuCount(siHuaNiuCount);
		result.setWuHuaNiuCount(wuHuaNiuCount);
		result.setWuXiaoNiuCount(wuXiaoNiuCount);
		result.setZhadanCount(zhadanCount);
		return result;
	}
	
	
	
	
}
