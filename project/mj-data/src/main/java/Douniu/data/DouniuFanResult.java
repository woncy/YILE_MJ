package Douniu.data;

import java.util.Arrays;

import mj.data.poker.douniu.DouniuPai;
import mj.data.poker.douniu.DouniuPaiType;

public class DouniuFanResult {
    private DouniuPai[] niuNum;
    private DouniuPaiType douNiuPaiType;
    private int[] winPai;  //赢的牌
    
    
    
    
	public DouniuFanResult() {
		super();
	}
	
	public DouniuPai[] getNiuNum() {
		return niuNum;
	}
	public void setNiuNum(DouniuPai[] niuNum) {
		this.niuNum = niuNum;
	}
	public DouniuPaiType getDouNiuPaiType() {
		return douNiuPaiType;
	}
	public void setDouNiuPaiType(DouniuPaiType douNiuPaiType) {
		this.douNiuPaiType = douNiuPaiType;
	}
	public int[] getWinPai() {
		return winPai;
	}
	public void setWinPai(int[] winPai) {
		this.winPai = winPai;
	}
	@Override
	public String toString() {
		return "DouniuFanResult [niuNum=" + Arrays.toString(niuNum)
				+ ", douNiuPaiType=" + douNiuPaiType + ", winPai="
				+ Arrays.toString(winPai) + "]";
	}
    
}
