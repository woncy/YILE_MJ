package mj.data.poker.pdk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mj.data.poker.Poker;
import mj.data.poker.PokerNum;

/**
 * 
    * @ClassName: PdkPaiType
    * @Description: 跑得快牌型枚举
    * @author 13323659953@163.com
    * @date 2017年6月26日
    *
 */
public  class PdkPaiType implements Serializable {
	
    /**
    * @Fields serialVersionUID 
    */
	    
	private static final long serialVersionUID = 1L;
	private int type;
	private String name;
	private int size;
	
	public static PdkPaiType DANZHANG=new PdkPaiType(1, "单张", 0);
	public static PdkPaiType DUIZI = new PdkPaiType(2, "对子", 0);
	public static PdkPaiType SANBUDAI = new PdkPaiType(3, "三不带", 0);
	public static PdkPaiType SANDAIYI = new PdkPaiType(3, "三带一", 0);
	public static PdkPaiType SANDAIER = new PdkPaiType(3, "三带二", 0);
	public static PdkPaiType DANSHUN = new PdkPaiType(4, "单顺", 0);
	public static PdkPaiType SHUANGSHUN = new PdkPaiType(5, "双顺", 0);
	public static PdkPaiType SANSHUN = new PdkPaiType(6, "三顺", 0);
	public static PdkPaiType FEIJI = new PdkPaiType(7, "飞机带翅膀", 0);
	public static PdkPaiType ZHADAN = new PdkPaiType(8, "炸弹", 1);
	public static PdkPaiType SIDAISAN = new PdkPaiType(9, "四带三", 0);

	public PdkPaiType(int type, String name, int size) {
		super();
		this.type = type;
		this.name = name;
		this.size = size;
	}
	

	public static PdkPaiType getPaiType(List<Poker> pokers) {
		Collections.sort(pokers);
		int length = pokers.size();
		if(length<=0){
			return null;
		}
		if(length==1){
			return DANZHANG;
		}else if(length == 2){
			return pokers.get(0).getPokerNum() == pokers.get(1).getPokerNum() ? DUIZI : null;
		}else if(length == 3){
			return pokers.get(0).getPokerNum() == pokers.get(2).getPokerNum() ? SANBUDAI : null;
		}else if(length == 4){
			return isZhaDan(pokers)?ZHADAN
					:isSanDaiYi(pokers)?SANDAIYI
						:isShuangShun(pokers)?SHUANGSHUN:null;
		}else if(length == 5){
			return isDanShun(pokers)?DANSHUN
					:isSanDaiEr(pokers)?SANDAIER:null;
		}else if(length == 6){
			return isDanShun(pokers)?DANSHUN
					:isShuangShun(pokers)?SHUANGSHUN
						:isSanShun(pokers)?SANSHUN:null;
		}else if(length == 7){
			return isSiDaiSan(pokers)?SIDAISAN
					:isDanShun(pokers)?DANSHUN:null;
		}else {
			if(length%5==0 && isFeiJI(pokers)){
				return FEIJI;
			}else if(length%3==0 && isSanShun(pokers)){
				return SANSHUN;
			}else if(length%2==0 && isShuangShun(pokers)){
				return SHUANGSHUN;
			}else {
				return isDanShun(pokers)?DANSHUN
						:isShuangShun(pokers)?SHUANGSHUN
							:isSanShun(pokers)?SANSHUN
								:isFeiJI(pokers)?FEIJI:null;
			}
		}
	}
	
	
	private static boolean isFeiJI(List<Poker> pokers){
		if(pokers.size()%5!=0){
			return false;
		}
		int[] counts = new int[PokerNum.P_K.getNum()];
		pokers.stream().forEach(v->{
				int num = v.getPokerNum().getNum();
				counts[num-1]++;
			}
		);
		int count = 0;
		for (int i = 0; i < counts.length; i++) {
			int j = counts[i];
			if(j==3){
				count++;
			}
		}
		if(count>=2 && count==pokers.size()/5){
			return true;
		}else{
			return false;
		}
	}
	
	private static boolean isZhaDan(List<Poker> pokers){
		return pokers.get(0).getPokerNum() == pokers.get(pokers.size()-1).getPokerNum();
	}
	
	private static boolean isSanDaiYi(List<Poker> pokers){
		PokerNum p0 = pokers.get(0).getPokerNum();
		PokerNum p1 = pokers.get(1).getPokerNum();
		PokerNum p2 = pokers.get(2).getPokerNum();
		PokerNum p3 = pokers.get(3).getPokerNum();
		return p0 != p1 ? 
				p1 == p3 : p0 == p2;
	}
	
	private static boolean isSanShun(List<Poker> pokers){
		List<Poker> poker0 = new ArrayList<Poker>();
		List<Poker> poker1 = new ArrayList<Poker>();
		List<Poker> poker2 = new ArrayList<Poker>();
		if(!(pokers.get(0).getPokerNum()==pokers.get(1).getPokerNum() 
				&& pokers.get(0).getPokerNum()==pokers.get(2).getPokerNum())){
			return false;
		}
		for (int i = 0; i < pokers.size(); i++) {
			if(i%3==0){
				poker0.add(pokers.get(i));
			}else if(i%3==1){
				poker1.add(pokers.get(i));
			}else {
				poker2.add(pokers.get(i));
			}
		}
		if(isDanShun(poker0)&&isDanShun(poker1)&&isDanShun(poker2)){
			return true;
		}else{
			return false;
		}
		
	}
	
	private static boolean isSanDaiEr(List<Poker> pokers){
		int length = pokers.size();
		int[] pais = new int[PokerNum.P_K.getNum()];
		for (int i = 0; i < length; i++) {
			pais[pokers.get(i).getPokerNum().getNum()]++;
		}
		for (int i = 0; i < pais.length; i++) {
			int j = pais[i];
			if(j==3){
				return true;
			}
		}
		return false;
	}
	
	private static boolean isShuangShun(List<Poker> pokers){
		int length = pokers.size();
		if(length <=3 ){
			return false;
		}
		List<Poker> q = new ArrayList<Poker>();
		List<Poker> o = new ArrayList<Poker>();
		if(pokers.get(0).getPokerNum()!=pokers.get(1).getPokerNum()){
			return false;
		}
		for (int i = 0; i < length-1; i++) {
			if(i%2==1){
				q.add(pokers.get(i));
			}else{
				o.add(pokers.get(i));
			}
		}
		return isDanShun(q)&&isDanShun(o);
	}
	
	private static boolean isDanShun(List<Poker> pokers){
		int length = pokers.size();
		if(length <=1 ){
			return false;
		}
		int start = pokers.get(0).getPokerNum().getNum();
		if(start<3){
			return false;
		}
		for (int i = 1; i < length-1; i++) {
			int curNum = pokers.get(i).getPokerNum().getNum();
			if(curNum != start+i){
				return false;
			}
		}
		
		if(pokers.get(length-2).getPokerNum()==PokerNum.P_K ){
			return pokers.get(length-1).getPokerNum()==PokerNum.P_A;
		}else{
			return pokers.get(length-2).getPokerNum().getNum()+1 ==pokers.get(length-1).getPokerNum().getNum();
		}
		
	}
	
	private static boolean isSiDaiSan(List<Poker> pokers){
		PokerNum p1 = pokers.get(0).getPokerNum();
		PokerNum p3 = pokers.get(2).getPokerNum();
		PokerNum p4 = pokers.get(3).getPokerNum();
		PokerNum p5 = pokers.get(4).getPokerNum();
		PokerNum p7 = pokers.get(6).getPokerNum();
		return (p1==p3 && p4==p7)?true
				:(p1==p4 && p5==p7)?true:false;
	}

	public int getType() {
		return type;
	}


	public String getName() {
		return name;
	}


	public int getSize() {
		return size;
	}

	@Override
	public int hashCode() {
		return size+name.hashCode()+size;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof PdkPaiType){
			return this.hashCode()==obj.hashCode();
		}else{
			return false;
		}
	}

	@Override
	public String toString() {
		return "PdkPaiType [type=" + type + ", name=" + name + ", size=" + size + "]";
	}

}
