package mj.data.poker.douniu;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.sun.tools.internal.ws.wsdl.document.jaxws.Exception;

import mj.data.poker.PokerNum;


public enum DouniuPaiType {
	WuNiu("无牛",1, 0 ),
	NiuDing("牛丁",1, 1),
	NiuTwo("牛二",1,2),
	NiuThree("牛三",1,3),
	NiuFour("牛四",1,4),
	NiuFive("牛五",1,5),
	NiuSix("牛六",1,6),
	NiuSeven("牛七",2,7),
	NiuEight("牛八",2,8),
	NiuNine("牛九",3,9),
	NiuNiu("牛牛",4,10),
	SiHuaNiu("四花牛",5,11),
	WuHuaNiu("五花牛",6,12),
	SiZha("四炸",7,13),
	WuXiaoNiu("五小牛",8,14);
	
	private static final Map<Integer, DouniuPaiType> indexMap = Arrays.stream(DouniuPaiType.values()).collect(
            Collectors.toMap(DouniuPaiType::getIndex, v -> v)
    );
	
	private String name;
	//倍数
	private int doubleCount;
	//大小序号
	private   int  index;
	
	public static DouniuPaiType getDouniuPaiType (DouniuPoker [] pokers) throws Throwable{
		/**
		 * 四炸或者五小牛
		 */
		if(pokers==null || pokers.length!=5){
			throw new Throwable("斗牛牌的数量不正确");
		}
		int niuNum = 0;
		int[] pokerCount = new int[13];
		int wuxiaoniuSum= 0;
		for (int i = 0; i < pokers.length; i++) {
			DouniuPoker douniuPoker = pokers[i];
			pokerCount[douniuPoker.getPokerNum().getNum()-1]++;
			wuxiaoniuSum += douniuPoker.getPokerNum().getNum();
		}
		if(wuxiaoniuSum<=10){
			return WuXiaoNiu;
		}
		for (int i = 0; i < pokerCount.length; i++) {
			int j = pokerCount[i];
			if(j>=4){
				return SiZha;
			}
		}
		
		//判断有牛无牛
		boolean hasNiu = false;
		boolean isBreaki = false;
		boolean isBreakj = false;
		for (int i = 0; i < pokers.length-2; i++) {
			
			if(isBreaki){
				break;
			}
			for (int j = 1; j < pokers.length-1; j++) {
				
				if(isBreakj){
					isBreaki = true;
					break;
				}
				for (int j2 = 2; j2 < pokers.length; j2++) {
					DouniuPoker p1 = pokers[i];
					DouniuPoker p2 = pokers[j];
					DouniuPoker p3 = pokers[j2];
					int num = p1.getScore()+p2.getScore()+p3.getScore();
					if(num%10==0){
						isBreakj = true;
						hasNiu = true;
						break;
					}
				}//for j2
				
			}//for j
			
		}// for i
		
		
		if(hasNiu){
			int sum = 0;
			int huaCount = 0;
			for (int i = 0; i < pokers.length; i++) {
				DouniuPoker douniuPoker = pokers[i];
				sum += douniuPoker.getScore();
				if(douniuPoker.getPokerNum().getNum()>=PokerNum.P_J.getNum()){
					huaCount++;
				}
			}
			niuNum = sum%10;
			if(niuNum==0){
				if(huaCount==4){
					return SiHuaNiu;
				}else if(huaCount==5){
					return WuHuaNiu;
				}else{
					return NiuNiu;
				}
			}else{
				return fromIndex(niuNum);
			}
			
		}
		return WuNiu;
	}
	
	




	DouniuPaiType(String name, int doubleCount,int index) {
		this.name = name;
		this.doubleCount = doubleCount;
		this.index = index;
	}
	
	
	@Override
	public String toString() {
		return "DouNiuType[name="+name+",doubleCount="+doubleCount+",index="+index+"]";
	}

	public String getName() {
		return name;
	}

	public int getDoubleCount() {
		return doubleCount;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
   public static DouniuPaiType fromIndex(int index) {
        return indexMap.get(index);
   }
	
	
}
