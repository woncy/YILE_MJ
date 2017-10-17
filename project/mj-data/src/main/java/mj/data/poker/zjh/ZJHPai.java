package mj.data.poker.zjh;

import java.util.ArrayList;
import java.util.Collections;

import mj.data.poker.Poker;

public class ZJHPai {
	private ZJHPaiType paiType;
	private ArrayList<Poker> pokers;
	private int score;
	
	public ZJHPai() {
	}
	
	public ZJHPai(ZJHPaiType paiType, ArrayList<Poker> pokers, int score) {
		super();
		this.paiType = paiType;
		this.pokers = pokers;
		this.score = score;
	}

	public ZJHPaiType getPaiType() {
		return paiType;
	}

	public void setPaiType(ZJHPaiType paiType) {
		this.paiType = paiType;
	}

	public ArrayList<Poker> getPokers() {
		return pokers;
	}

	public void setPokers(ArrayList<Poker> pokers) {
		this.pokers = pokers;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public  int compareWeight(ArrayList<Poker> otherPokers){
		int weight = paiType.getWeight();
		int weight2 = ZJHPaiType.getPaiType(otherPokers).getWeight();
		if(weight > weight2){
			return 1;
		}else if(weight == weight2){
			return compareTo(otherPokers);
		}
		return 0;
	}
	
	public  int compareTo(ArrayList<Poker> otherPokers){
    	Collections.sort(otherPokers);
    	int compareTo = 0;
    	if(pokers.size() != otherPokers.size()){
    		throw new RuntimeException("严重错误！ 双方牌不一致");
    	}
    	for (int i = 0; i < 3; i++) {
    		 ZJHPoker LocationPoker = (ZJHPoker) pokers.get(i);
    		 ZJHPoker poker = (ZJHPoker) otherPokers.get(i);
    		  compareTo = LocationPoker.compareTo(poker);
		}
		return compareTo;
	}

	@Override
	public String toString() {
		return "ZJHPai [paiType=" + paiType + ", pokers=" + pokers + "]";
	}

	
}
