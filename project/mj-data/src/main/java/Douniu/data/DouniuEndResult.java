package Douniu.data;

import java.util.ArrayList;
import java.util.Arrays;

import mj.data.poker.douniu.DouniuPai;

public class DouniuEndResult {
    private  boolean isNiuNiu;  //是否是牛牛
    private  int   indexWin ;   //玩家赢的位置 
    private  boolean isWin;   //判断是否赢了
    private  int   zhuangIndex; //庄家的位置
    private  DouniuUserPaiInfo[] userPaiInfos;
    private ArrayList<DouniuPai> left;    //剩下的
    private boolean isMeiNiu; //是否没牛
    private boolean isNiuNum; //是否是牛的数量
    private boolean isXiaoNiu; //是否是小牛
    private boolean isZhaNiu;  //是否是炸弹牛   
	public DouniuEndResult() {
		super();
	}
	
	/**
	 * 计算牛牛类型的得分
	 * 
	 */
	
	/* public void niuNiuScore( int score){
		 for(int i=0; i< userPaiInfos.length; i++ ){
		      userPaiInfos[i].setFan(score);
		      if(i== indexWin){
		    	  userPaiInfos[i].setScore(score * 3);
		      }else{
		    	  userPaiInfos[i].setScore(-score * 3);
		      }
		 }
	 }*/
	
	
	@Override
	public String toString() {
		return "DouniuEndResult [isNiuNiu=" + isNiuNiu + ", indexWin="
				+ indexWin + ", zhuangIndex=" + zhuangIndex + ", userPaiInfos="
				+ Arrays.toString(userPaiInfos) + ", left=" + left
				+ ", isMeiNiu=" + isMeiNiu + ", isNiuNum=" + isNiuNum
				+ ", isXiaoNiu=" + isXiaoNiu + ", isZhaNiu=" + isZhaNiu + "]";
	}
	
	
	public boolean isWin() {
		return isWin;
	}

	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	public boolean isNiuNiu() {
		return isNiuNiu;
	}
	public void setNiuNiu(boolean isNiuNiu) {
		this.isNiuNiu = isNiuNiu;
	}
	public int getIndexWin() {
		return indexWin;
	}
	public void setIndexWin(int indexWin) {
		this.indexWin = indexWin;
	}
	public int getZhuangIndex() {
		return zhuangIndex;
	}
	public void setZhuangIndex(int zhuangIndex) {
		this.zhuangIndex = zhuangIndex;
	}
	public DouniuUserPaiInfo[] getUserPaiInfos() {
		return userPaiInfos;
	}
	public void setUserPaiInfos(DouniuUserPaiInfo[] userPaiInfos) {
		this.userPaiInfos = userPaiInfos;
	}
	public ArrayList<DouniuPai> getLeft() {
		return left;
	}
	public void setLeft(ArrayList<DouniuPai> left) {
		this.left = left;
	}
	public boolean isMeiNiu() {
		return isMeiNiu;
	}
	public void setMeiNiu(boolean isMeiNiu) {
		this.isMeiNiu = isMeiNiu;
	}
	public boolean isNiuNum() {
		return isNiuNum;
	}
	public void setNiuNum(boolean isNiuNum) {
		this.isNiuNum = isNiuNum;
	}
	public boolean isXiaoNiu() {
		return isXiaoNiu;
	}
	public void setXiaoNiu(boolean isXiaoNiu) {
		this.isXiaoNiu = isXiaoNiu;
	}
	public boolean isZhaNiu() {
		return isZhaNiu;
	}
	public void setZhaNiu(boolean isZhaNiu) {
		this.isZhaNiu = isZhaNiu;
	}

	
    
    
}
