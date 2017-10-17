package mj.data.poker.douniu;

import mj.data.poker.Poker;
import mj.data.poker.PokerColorType;
import mj.data.poker.PokerNum;

/**
 	* 
    * @ClassName: PdkPai
    * @Description: 牌的种类和牌的集合
    * @author liuyihui
    * @date 2017年7月6日
    */

public class DouniuPai extends Poker{
	
	private int pokerValue;
	
	/**
     * 有牌，但是不知道是啥
     */
    public static final int HAS_PAI_INDEX = 1;  //发给自己
    public static final int NOT_PAI_INDEX = -1;  //发送给其他人  隐藏
    
	public DouniuPai() {
		super();
	}
	
	public DouniuPai(PokerColorType pokerColorType, PokerNum pokerNum) {
		super(pokerColorType,pokerNum);
		if(pokerColorType==null){
			throw new RuntimeException("不正确的牌型");
		}
		if(pokerNum.getNum()>=10){
			this.pokerValue = 10;
		}else{
			this.pokerValue = pokerNum.getNum();
		}
	}
	
	public int getPokerValue() {
		return pokerValue;
	}

	public void setPokerValue(int pokerValue) {
		this.pokerValue = pokerValue;
	}
	
	
	public int compareTo(DouniuPai poker) {
		int res = Integer.compare(poker.getPokerNum().getNum() *10 + poker.getPokerType().size()
				, poker.getPokerNum().getNum() + poker.getPokerType().size());
		return res == 0 ? super.compareTo(poker) : res;
		
	}
  
	/**
	 * 调取Poker的牌的序号方法
	 * @param paiIndex
	 * @return
	 */
	public static DouniuPai fromIndex(int paiIndex) {
		
		return (DouniuPai) getPokerFromIndex(paiIndex);
	}

	
}
