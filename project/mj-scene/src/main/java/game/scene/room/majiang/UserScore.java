package game.scene.room.majiang;
/**
 *  郑州麻将
 * @author pc
 *
 */
public class UserScore {
	
	
	private int index;
	private int difen;
	private int paofen;
	
	private int fanbeiCount;
	
	private int gangCount;
	private int huScore;
	private int gangScore;
	
	

	public int getHuScore() {
		return huScore;
	}

	public int jisuanHuScore() {
		return (difen+paofen)*(1<<fanbeiCount);
	}
	
	public void setHuScore(int huScore){
		this.huScore = huScore;
	}

	public int getGangScore() {
		return gangScore;
	}

	public void setGangScore(int gangScore) {
		this.gangScore = gangScore;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getDifen() {
		return difen;
	}

	public void setDifen(int difen) {
		this.difen = difen;
	}

	public int getPaofen() {
		return paofen;
	}

	public void setPaofen(int paofen) {
		this.paofen = paofen;
	}

	public int getFanbeiCount() {
		return fanbeiCount;
	}

	public void setFanbeiCount(int fanbeiCount) {
		this.fanbeiCount = fanbeiCount;
	}

	public int getGangCount() {
		return gangCount;
	}

	public void setGangCount(int gangCount) {
		this.gangCount = gangCount;
	}

	

	
	
}
