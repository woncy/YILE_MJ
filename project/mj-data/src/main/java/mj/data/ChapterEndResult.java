package mj.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import mj.net.message.game.GameChapterEnd;

/**
 * 牌局结束结果集
 *
 * @author zuoge85@gmail.com on 2016/11/4.
 */
public class ChapterEndResult {
    private boolean isHuPai;
    private int huPaiIndex;
    private int zhuangIndex;
    private UserPaiInfo[] userPaiInfos;
    private ArrayList<Pai> left;//剩下的
    private boolean isZiMo;
    private Pai[] huiEr;
    
    private int huPaiUserId;
    
    private boolean isGangShangHua;
    private boolean isQiDuiHu;
    private boolean isHuiErHu;
    
    private boolean isLastPai;
    private int fangPaoIndex;
    private int zaMaType;
    private int[] zaMaPai;
    private int zaMaFan;
    
//    private int endType = -1 ; // -1 ：表示没有胡，  1: 缺一手, 2 : 缺两手, 3 : 缺三首 , 4  清一色 
    
    public ChapterEndResult() {
    	
    }
    
    

//    @SuppressWarnings("unchecked")
//    public ChapterEndResult(MajiangChapter chapter, int huPaiIndex, int fangPaoIndex, boolean isGangShangHua) {
//        this.isHuPai = huPaiIndex != -1;
//        this.zhuangIndex = chapter.getZhuangIndex();
//        this.huPaiIndex = huPaiIndex;
//        this.isZiMo = fangPaoIndex == -1;
//        this.isGangShangHua = isGangShangHua;
//        this.fangPaoIndex = fangPaoIndex;
//
//        isLastPai = chapter.isLastPai();
//
//
//        left = (ArrayList<Pai>) chapter.getLeftPai().clone();
//
//        huiEr = chapter.getHuiEr();
//
//        userPaiInfos = Stream.of(chapter.getUserPlaces())
//                .map(u -> new UserPaiInfo(
//                        u, u.getLocationIndex() == huPaiIndex, u.getLocationIndex() == zhuangIndex, isZiMo
//                ))
//                .toArray(UserPaiInfo[]::new);
//
//    }


    /**
     * 计算胡牌得分
     */
    
    public void excuteScore(int score ) {
        for (int i = 0; i < userPaiInfos.length; i++) {
            userPaiInfos[i].setFan(score);
            if (i == huPaiIndex) {
                userPaiInfos[i].setScore(score * 3);
            }else{
                userPaiInfos[i].setScore(-score );
            }
        }
    }

//    public int getEndType() {
//        return endType;
//    }
//
//    public void setEndType(int endType) {
//        this.endType = endType;
//    }

    public GameChapterEnd toMessage(boolean isQiDuiHu) {
        GameChapterEnd m = new GameChapterEnd();
        m.setFangPaoIndex(fangPaoIndex);
        m.setHuPaiIndex(huPaiIndex);
        m.setZaMaFan(zaMaFan);
        m.setZaMaPai(zaMaPai);
        m.setZaMaType(zaMaType);


        m.setFanResults(Arrays.stream(userPaiInfos)
                .map(UserPaiInfo::toMessage)
                .collect(Collectors.toCollection(ArrayList::new))
        );
        return m;
    }

    public int getFanNums() {
        return userPaiInfos[huPaiIndex].getFanNums();
    }

    public FanResult getMaxFanResult() {
        return userPaiInfos[huPaiIndex].getMaxFanResult();
    }

    public boolean isHuPai() {
        return isHuPai;
    }

    public void setHuPai(boolean huPai) {
        isHuPai = huPai;
    }

    public int getHuPaiIndex() {
        return huPaiIndex;
    }

    public void setHuPaiIndex(int huPaiIndex) {
        this.huPaiIndex = huPaiIndex;
    }

    public int getZhuangIndex() {
        return zhuangIndex;
    }

    public void setZhuangIndex(int zhuangIndex) {
        this.zhuangIndex = zhuangIndex;
    }

    public UserPaiInfo[] getUserPaiInfos() {
        return userPaiInfos;
    }

    public void setUserPaiInfos(UserPaiInfo[] userPaiInfos) {
        this.userPaiInfos = userPaiInfos;
    }

    public ArrayList<Pai> getLeft() {
        return left;
    }

    public void setLeft(ArrayList<Pai> left) {
        this.left = left;
    }

    public boolean isGangShangHua() {
        return isGangShangHua;
    }

    public Pai[] getHuiEr() {
        return huiEr;
    }

    public void setHuiEr(Pai[] huiEr) {
        this.huiEr = huiEr;
    }

    public boolean isZiMo() {
        return isZiMo;
    }

    public boolean isLastPai() {
        return isLastPai;
    }

    public int getFangPaoIndex() {
        return fangPaoIndex;
    }

    public void setZiMo(boolean ziMo) {
        isZiMo = ziMo;
    }

    public void setGangShangHua(boolean gangShangHua) {
        isGangShangHua = gangShangHua;
    }

    public void setLastPai(boolean lastPai) {
        isLastPai = lastPai;
    }

    public void setFangPaoIndex(int fangPaoIndex) {
        this.fangPaoIndex = fangPaoIndex;
    }

    public int getZaMaType() {
        return zaMaType;
    }

    public void setZaMaType(int zaMaType) {
        this.zaMaType = zaMaType;
    }

    public int[] getZaMaPai() {
        return zaMaPai;
    }

    public void setZaMaPai(int[] zaMaPai) {
        this.zaMaPai = zaMaPai;
    }

    public int getZaMaFan() {
        return zaMaFan;
    }

    public void setZaMaFan(int zaMaFan) {
        this.zaMaFan = zaMaFan;
    }
    
    public boolean isQiDuiHu() {
        return isQiDuiHu;
    }

    public void setQiDuiHu(boolean isQiDuiHu) {
        this.isQiDuiHu = isQiDuiHu;
    }

    public boolean isHuiErHu() {
        return isHuiErHu;
    }

    public void setHuiErHu(boolean isHuiErHu) {
        this.isHuiErHu = isHuiErHu;
    }

    public int getHuPaiUserId() {
		return huPaiUserId;
	}

	public void setHuPaiUserId(int huPaiUserId) {
		this.huPaiUserId = huPaiUserId;
	}

	@Override
	public String toString() {
		return "ChapterEndResult [isHuPai=" + isHuPai + ", huPaiIndex=" + huPaiIndex + ", zhuangIndex=" + zhuangIndex
				+ ", userPaiInfos=" + Arrays.toString(userPaiInfos) + ", left=" + left + ", isZiMo=" + isZiMo
				+ ", huiEr=" + Arrays.toString(huiEr) + ", huPaiUserId=" + huPaiUserId + ", isGangShangHua="
				+ isGangShangHua + ", isQiDuiHu=" + isQiDuiHu + ", isHuiErHu=" + isHuiErHu + ", isLastPai=" + isLastPai
				+ ", fangPaoIndex=" + fangPaoIndex + ", zaMaType=" + zaMaType + ", zaMaPai=" + Arrays.toString(zaMaPai)
				+ ", zaMaFan=" + zaMaFan + "]";
	}
}
