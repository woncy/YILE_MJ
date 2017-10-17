package Douniu.data;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import com.google.common.collect.ArrayListMultimap;
import mj.data.poker.Poker;
import mj.data.poker.douniu.DouniuPai;
import mj.data.poker.douniu.DouniuPaiType;
import mj.data.poker.Poker;
import mj.data.poker.douniu.DouniuPai;
import mj.data.poker.douniu.DouniuPaiType;
import mj.data.poker.douniu.DouniuPoker;
import mj.net.message.game.UserPlaceMsg;
import mj.net.message.game.douniu.DouniuOnePai;
import mj.net.message.game.douniu.DouniuUserPlaceMsg;
/**
 * 用户的牌信息
 *
 * @author zuoge85@gmail.com on 16/10/17.
 */
public class DouniuUserPlace {
	/**
	 * 手牌
	 */
	private final ArrayListMultimap<DouniuPai, DouniuPai> shouPaiMap = ArrayListMultimap
			.create();

	private final ArrayList<DouniuPai> shouPaiList = new ArrayList<DouniuPai>();
	private int locationIndex;
	private int userId;
	private String userName;
    private int zhuNum ;  //获取下住的数量
    private int totalZhu; //用户总下注数， 在竞技场的时候用到。
	private boolean isOffLine;
	int score;  //玩家当前的分数
    int winCount; //玩家赢的次数
    int userStatus = -1 ; // -1 的时候表示用户位置上没有人, 为 0 的时候,表示有人在等待状态, 1 的时候表示有人在正常游戏状态.2 。表示弃牌
    int qiangZhuangWeight = 0 ;
    /*
    *如果他输掉的话，判断是谁赢了他。
    *（弃牌的人就不用关心是谁赢了）
    *等对应的赢家输了或者是游戏结束的时候看到对应的赢家的牌。
    */
    int winlocationIndex = -1;
    
    
    
	
	public int getWinlocationIndex() {
		return winlocationIndex;
	}

	public void setWinlocationIndex(int winlocationIndex) {
		this.winlocationIndex = winlocationIndex;
	}

	public int getTotalZhu() {
		return totalZhu;
	}

	public void setTotalZhu(int totalZhu) {
		this.totalZhu = totalZhu;
	}

	public int getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}

	public int getQiangZhuangWeight() {
		return qiangZhuangWeight;
	}

	public void setQiangZhuangWeight(int qiangZhuangWeight) {
		this.qiangZhuangWeight = qiangZhuangWeight;
	}

	public boolean isOffLine() {
		return isOffLine;
	}

	public void setOffLine(boolean isOffLine) {
		this.isOffLine = isOffLine;
	}

	public void clear() {
		shouPaiMap.clear();
		shouPaiList.clear();
	}

	public void addShouPai(DouniuPai pai) {
		/*while(true){
			if(!shouPaiList.contains(pai)){*/
				shouPaiMap.put(pai, pai);
				shouPaiList.add(pai);
			/*	break;
			}else{
				
			}
		}*/
		
	}

	public DouniuUserPlaceMsg toMessage(boolean isMy, int shouIndex) {
		DouniuUserPlaceMsg m = new DouniuUserPlaceMsg();
		
       ArrayList<DouniuOnePai> list =new ArrayList<DouniuOnePai>();    
		if (isMy) {
			   for(DouniuPai pai : shouPaiList){
				  DouniuOnePai onePai =new DouniuOnePai();
				  onePai.setPokerNum(pai.getPokerNum().getNum());
				  onePai.setPokerSuit(pai.getPokerType().getSize());
				  onePai.setPokerValue(pai.getPokerValue());
//				  System.out.println("手牌的显示值: "+ pai.getPokerNum().getNum());
//				  System.out.println("牌的信息：" + pai.getPokerValue() );
				  list.add(onePai);	
			   }
				   m.setShouPai(list);
				   m.setShouPaiLen(list.size());
				 for(DouniuOnePai pai : list){
					 System.out.println("遍历：："+ pai.getPokerNum());
					 System.out.println("牌的花色："+ pai.getPokerSuit());
				 }
				   System.out.println("手牌长度： "+ shouPaiList.size());
		}
		
		return m;
	}

	/**
     * 减去比牌分数
     *@param curScore
     *@return
     * 2017年7月11日
     *//*
    public int subScore(int curScore){
    	if(score < curScore){
    		score -= curScore;
    		getScore -= curScore;
    	}else{ 
    		score -= score;
    		getScore -= score;
    		return score;
    	}
    	return curScore;
    }
    *//**
     * 结束之后加分
     *@param sunScore
     *@return
     * 2017年7月12日
     */
    public void endAddScore(int sumScore){
    	score += sumScore;
    }
    
    public void addScore(int score){
		this.score -= score;
	}	
    
	/**
     * 获取手牌在扑克牌中的位置
     *@param pokers
     *@return
     *@return
     */
    @SuppressWarnings("null")
	public static int[]  getShouPaiNum(ArrayList<Poker> pokers){
    	Collections.sort(pokers);
    	int[] pokerIndexNum = new int[5]; 
    	for (int i = 0; i < pokers.size(); i++) {
			int indexByPoker = Poker.getIndexByPoker(pokers.get(i));
			pokerIndexNum[i] = indexByPoker;
		}
    	return pokerIndexNum;
    	
    }
	 /**
     * 比牌
     *@return
     * 2017年7月10日
     */
    public static int comparePoker(ArrayList<DouniuPai> locationPoker, ArrayList<DouniuPai> indexPoker){
    	Collections.sort(locationPoker);
    	Collections.sort(indexPoker);
    	int compareTo = 0;
    	if(locationPoker.size() != indexPoker.size()){
    		throw new RuntimeException("严重错误！ 双方牌不一致");
    	}
    	for (int i = 0; i < 3; i++) {
    		DouniuPai LocationPoker = (DouniuPai) locationPoker.get(i);
    		DouniuPai poker = (DouniuPai) indexPoker.get(i);
    		  compareTo = LocationPoker.compareTo(poker);
		}
    	return compareTo;
    }
    
	public void changeFa(DouniuPai pai) {
		addShouPai(pai);
	}

	public int getLocationIndex() {
		return locationIndex;
	}

	public ArrayListMultimap<DouniuPai, DouniuPai> getShouPaiMap() {
		return shouPaiMap;
	}

	public ArrayList<DouniuPai> getShouPaiList() {
		return shouPaiList;
	}

	public int getScore() {
		return score;
	} 

	public void setScore(int score) {
		this.score += score;
	}

	public void addWinCount(){
		winCount++;
	}
	public int getWinCount() {
		return winCount;
	}

	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}

	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}

	private int getShouPaiCount(DouniuPai pai) {
		return shouPaiMap.get(pai).size();
	}

	public boolean checkShouPai(DouniuPai pai) {
		return shouPaiMap.containsKey(pai);
	}

	public boolean existShouPai(DouniuPai[] pai) {
		if (pai == null) {
			return false;
		}
		for (DouniuPai p : pai) {
			if (shouPaiMap.containsKey(p)) {
				return true;
			}
		}
		return false;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getZhuNum() {
		return zhuNum;
	}

	public void setZhuNum(int zhuNum) {
		this.zhuNum = zhuNum;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "DouniuUserPlace{" +  ", shouPaiMap="
				+ shouPaiMap + ", shouPaiList=" + shouPaiList
				+ ", locationIndex=" + locationIndex + ", userId=" + userId
				+ ", userName='" + userName + '\'' + '}';
	}

}
