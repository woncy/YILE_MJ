package mj.data;

import java.util.ArrayList;
import java.util.Collections;

import mj.data.poker.Poker;
import mj.data.poker.zjh.ZJHPai;
import mj.data.poker.zjh.ZJHPaiType;
import mj.data.poker.zjh.ZJHPoker;

/**
 * 玩家牌信息
 * @author 13323659953@163.com
 *
 */
/**
 * @author 13323659953@163.com
 *
 */
public class ZJHUserPlace {

	private String pokerType;
	
	private  int locationIndex;
	
	private boolean offLine;
	
	private int userId;
	
	private String userName;
	
	//private boolean isChuJu;
	
	private boolean isKan;
	
	private int zhu;//总下注数
	
	private boolean isQi;
	
	private int score;
	
	private boolean isShu;
	
	private int winCount;
	
	private int loseCount;
	private int getScore;//得分
	 /**
     * 手牌
     */
	
//    private final ArrayListMultimap<PokerColorType, PokerNum> shouPai = ArrayListMultimap.create();
//    private final ArrayListMultimap<Integer, PokerNum> shouPaiMap = ArrayListMultimap.create();
    private final ArrayList<Poker> shouPaiList = new ArrayList<>();
    
    private ArrayList<ZJHPai> allPai;
    
    private ArrayList<Integer> scores;
	
	/**
	 * 添加手牌
	 *@param poker
	 *@return
	 * 2017年7月4日
	 */
    public void addShouPai(Poker poker) {
        if (shouPaiList.size() > 3) {
            throw new RuntimeException("严重错误");
        }
//        shouPai.put(poker.getPokerType(), poker.getPokerNum());
//        shouPaiMap.put(poker.getIndexByPoker(poker), poker.getPokerNum());
        shouPaiList.add(poker);
        getScore -=5;
        
    }
    public void addZJHPai() {
    	Collections.sort(shouPaiList);
    	ZJHPai pai = new ZJHPai(ZJHPaiType.getPaiType(shouPaiList), shouPaiList,getScore);
    	if(allPai == null){
    		allPai = new ArrayList<ZJHPai>();
    	}
    	allPai.add(pai);
		
	}
    
    /**
     * 跟注操作
     *@param maxZhu 选择跟注分值
     *@param MaxZhu 房间最大分值
     *@return
     * 2017年7月10日
     */
    public void genZhu(int maxZhu, int MaxZhu){
    	if(score <= maxZhu){
    		 throw new RuntimeException("分值不足");
    	}else{
    		if(maxZhu > MaxZhu){
    			score = score - MaxZhu;//房间最大分值
    			getScore -= MaxZhu;
    		}else{
    			score = score - maxZhu;
    			getScore -= maxZhu;
    		}
    	}
    }
    /**
     * 加注操作
     *@param maxZhu	选择加注分值
     *@param MaxZhu 房间最大分值
     *@return
     * 2017年7月10日
     */
    public void jiaZhu(int maxZhu, int MaxZhu) {
		if(score <= maxZhu){
			 throw new RuntimeException("分值不足");
		}else {
			if(maxZhu > MaxZhu){
				score = score - MaxZhu;//房间最大分值
				getScore -= MaxZhu;
			}else{
				score = score - maxZhu;
				getScore -= maxZhu;
			}
		}
	}
//    /**
//     * 比牌
//     *@return
//     * 2017年7月10日
//     */
//    public static int comparePoker(ArrayList<Poker> locationPoker, ArrayList<Poker> indexPoker){
//    	Collections.sort(locationPoker);
//    	Collections.sort(indexPoker);
//    	int compareTo = 0;
//    	if(locationPoker.size() != indexPoker.size()){
//    		throw new RuntimeException("严重错误！ 双方牌不一致");
//    	}
//    	for (int i = 0; i < 3; i++) {
//    		 ZJHPoker LocationPoker = (ZJHPoker) locationPoker.get(i);
//    		 ZJHPoker poker = (ZJHPoker) indexPoker.get(i);
//    		  compareTo = LocationPoker.compareTo(poker);
//		}
//    	return compareTo;
//    }
    
    /**
     * 获取手牌在扑克牌中的位置
     *@param pokers
     *@return
     *@return
     * 2017年7月10日
     */
    @SuppressWarnings("null")
	public static int[] getShouPaiNum(ArrayList<Poker> pokers){
    	Collections.sort(pokers);
    	int[] pokerIndexNum = new int[3]; 
    	for (int i = 0; i < pokers.size(); i++) {
			int indexByPoker = Poker.getIndexByPoker(pokers.get(i));
			pokerIndexNum[i] = indexByPoker;
		}
    	return pokerIndexNum;
    	
    }
    /**
     * 减去比牌分数
     *@param curScore
     *@return
     * 2017年7月11日
     */
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
    /**
     * 结束之后加分
     *@param sunScore
     *@return
     * 2017年7月12日
     */
    public void endAddScore(int sumScore){
    	score += sumScore;
    }
    
    public ZJHPaiType getPokerType(){
    	ZJHPaiType paiType = ZJHPaiType.getPaiType(shouPaiList);
    	return paiType;
    }
	public int getLocationIndex() {
		return locationIndex;
	}
	public void setLocationIndex(int locationIndex) {
		this.locationIndex = locationIndex;
	}
	public boolean isOffLine() {
		return offLine;
	}
	public void setOffLine(boolean offLine) {
		this.offLine = offLine;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
//	public boolean isChuJu() {
//		return isChuJu;
//	}
//	public void setChuJu(boolean isChuJu) {
//		this.isChuJu = isChuJu;
//	}
	public boolean isKan() {
		return isKan;
	}
	public void setKan(boolean isKan) {
		this.isKan = isKan;
	}
	public int getZhu() {
		return zhu;
	}
	public void setZhu(int zhu) {
		this.zhu += zhu;
	}
	public boolean isQi() {
		return isQi;
	}
	public void setQi(boolean isQi) {
		this.isQi = isQi;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}

	public boolean isShu() {
		return isShu;
	}
	public void setShu(boolean isShu) {
		this.isShu = isShu;
	}
//	public ArrayListMultimap<PokerColorType, PokerNum> getShouPai() {
//		return shouPai;
//	}
//	public ArrayListMultimap<Integer, PokerNum> getShouPaiMap() {
//		return shouPaiMap;
//	}
	public ArrayList<Poker> getShouPaiList() {
		return shouPaiList;
	}
	public void setPokerType(String pokerType) {
		this.pokerType = pokerType;
	}
	
	public ArrayList<ZJHPai> getAllPai() {
		return allPai;
	}
	public void setAllPai(ArrayList<ZJHPai> allPai) {
		this.allPai = allPai;
	}
	public void addScore(int score){
		this.score -= score;
	}
	
	public int getWinCount() {
		return winCount;
	}
	public void addWinCount(){
		winCount++;
	}
	
	public void setWinCount(int winCount) {
		this.winCount = winCount;
	}
	
	public int getLoseCount() {
		return loseCount;
	}
	public void setLoseCount(int chapternum) {
		this.loseCount = chapternum - winCount;
	}
	
	public int getGetScore() {
		return getScore;
	}
	public void setGetScore(int getScore) {
		this.getScore = getScore;
	}
	@Override
	public String toString() {
		return "ZJHUserPlace [pokerType=" + pokerType + ", locationIndex=" + locationIndex + ", offLine=" + offLine
				+ ", userId=" + userId + ", userName=" + userName + ", isKan=" + isKan + ", zhu=" + zhu + ", isQi="
				+ isQi + ", score=" + score + ", isShu=" + isShu + ", winCount=" + winCount + ", loseCount=" + loseCount
				+ ", getScore=" + getScore + ", shouPaiList=" + shouPaiList + ", allPai=" + allPai + ", scores="
				+ scores + "]";
	}
	
}
