package mj.data.poker.zjh;

import java.util.Collections;
import java.util.List;

import mj.data.poker.Poker;
import mj.data.poker.PokerNum;



public enum ZJHPaiType {
	BaoZi("豹子",6),
	TongHuaShun("同花顺",5),
	TongHua("同花",4),
	ShunZi("顺子",3),
	DuiZi("对子",2),
	SanPai("散牌",1);
	
	
	private String name;
	private int weight;

	ZJHPaiType(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	@Override
	public String toString() {
		return name;
	}

	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}
	
	/**
	 * 获取牌的类型 豹子 同花 同花顺 顺子 对子 散牌
	 *@param pokers
	 *@return
	 *@return
	 * 2017年6月23日
	 */
	public static ZJHPaiType getPaiType(List<Poker> pokers){
		Collections.sort(pokers);
		if(isBaozi(pokers)){
			return BaoZi;
		}
		if(isTonghua(pokers)){
			if(isShunZi(pokers)){
				return TongHuaShun;
			}
			return TongHua;
		}
		if(isShunZi(pokers)){
			return ShunZi;
		}
		if(isDuiZi(pokers)){
			return DuiZi;
		}
		return SanPai;
		
	}
	/**
	 * 顺子
	 *@param pokers
	 *@return
	 *@return
	 * 2017年6月23日
	 */
	private static boolean isShunZi(List<Poker> pokers){
		
		if(pokers.get(0).getPokerNum() == PokerNum.P_A){//正常情况
			if(pokers.get(pokers.size()-1).getPokerNum()==PokerNum.P_K){
				for (int i = pokers.size()-1; i > 1; i--) {
					Poker nextPoker = pokers.get(i-1);
					Poker nowPoker = pokers.get(i);
					if(nextPoker.getPokerNum().getNum()-nowPoker.getPokerNum().getNum()!=-1){
						return false;
					}
				}
				return true;
			
			}else{
				return false;
			}
		
		}else{//处理Q K A特殊情况
			for (int i = 0; i < pokers.size()-1; i++) {
				Poker nextPoker = pokers.get(i+1);
				Poker nowPoker = pokers.get(i);
				if(nextPoker.getPokerNum().getNum()-nowPoker.getPokerNum().getNum()!=1){
					return false;
				}
			}
			return true;
		}
	}
	/**
	 * 豹子
	 *@param pokers
	 *@return
	 *@return
	 * 2017年6月23日
	 */
	private static boolean isBaozi(List<Poker> pokers){  
     Poker poker = pokers.get(0);
        for (int i = 1, size = pokers.size(); i < size; i++){  
           if (poker.getPokerNum().getNum() != pokers.get(i).getPokerNum().getNum()){  
                return false;  
            }  
        }  
        return true;  
    }  
	/**
	 * 同花
	 *@return
	 *@return
	 * 2017年6月23日
	 */
	private static  boolean isTonghua(List<Poker> pokers){
		for (int i = 1, size = pokers.size(); i < size; i++){
			if(pokers.get(0).getPokerType() != pokers.get(i).getPokerType()){
				return false;
			}
		}
		return true;
	}
	/**
	 * 对子
	 *@param pokers
	 *@return
	 *@return
	 * 2017年6月23日
	 */
	private static boolean isDuiZi(List<Poker> pokers){
		Poker p1 = pokers.get(0);
		Poker p2 = pokers.get(1);
		Poker p3 = pokers.get(2);
		
		return p1.getPokerNum()==p2.getPokerNum() ? 
				true : p1.getPokerNum() == p3.getPokerNum() ?
						true : p2.getPokerNum() == p3.getPokerNum() ?
								true:false;
	}
	
}
