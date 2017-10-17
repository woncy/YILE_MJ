package mj.data.poker.pdk;

import java.util.List;

import mj.data.poker.Poker;
import mj.data.poker.PokerNum;

/**
 	* 
    * @ClassName: PdkPai
    * @Description: 牌的种类和牌的集合
    * @author 13323659953@163.com
    * @date 2017年6月26日
    *
    */
public class PdkPai implements Comparable<PdkPai>{
	private PdkPaiType paiType;
	private List<Poker> pokers;
	private Poker max;
	public PdkPai() {
		super();
	}
	
	public PdkPai(List<Poker> pokers){
		this(PdkPaiType.getPaiType(pokers),pokers);
	}
	
	public PdkPai(PdkPaiType paiType, List<Poker> pokers) {
		super();
		if(paiType==null){
			throw new RuntimeException("不正确的牌型");
		}
		this.paiType = paiType;
		this.pokers = pokers;
		setMax(paiType,pokers);
	}
	
	
	private void setMax(PdkPaiType paiType, List<Poker> pokers){

		int type = paiType.getType();
		if(type==1||type==2||type==6||type==4 ||type==5||type==8){
			this.max = pokers.get(pokers.size()-1);
			return;
		}else if(type == 3){
			int[] pais = new int[PokerNum.P_K.getNum()];
			for (int i = 0; i < this.pokers.size(); i++) {
				pais[pokers.get(i).getPokerNum().getNum()]++;
			}
			for (int i = 0; i < pais.length; i++) {
				int j = pais[i];
				if(j==3){
					this.max = new PdkPokerAdapter().pokerToPdkPoker(Poker.getPokerFromIndex(i));
				}
			}
		}else if(type==9){
			int[] pais = new int[PokerNum.P_K.getNum()];
			for (int i = 0; i < this.pokers.size(); i++) {
				pais[pokers.get(i).getPokerNum().getNum()]++;
			}
			for (int i = 0; i < pais.length; i++) {
				int j = pais[i];
				if(j==4){
					this.max = new PdkPokerAdapter().pokerToPdkPoker(Poker.getPokerFromIndex(i));
				}
			}
		}else if(type==7){
			int[] pais = new int[PokerNum.P_K.getNum()];
			for (int i = 0; i < this.pokers.size(); i++) {
				pais[pokers.get(i).getPokerNum().getNum()]++;
			}
			for (int i = 0; i < pais.length; i++) {
				int j = pais[i];
				if(j==3){
					this.max = new PdkPokerAdapter().pokerToPdkPoker(Poker.getPokerFromIndex(i));
				}
			}
		}else{
			this.max = null;
		}
	}
	
	
	
	public Poker getMax() {
		return max;
	}

	public void setMax(Poker max) {
		this.max = max;
	}

	public PdkPaiType getPaiType() {
		return paiType;
	}
	public void setPaiType(PdkPaiType paiType) {
		this.paiType = paiType;
	}
	public List<Poker> getPokers() {
		return pokers;
	}
	public void setPokers(List<Poker> pokers) {
		this.pokers = pokers;
	}
	@Override
	public int compareTo(PdkPai pai) {
		int size = Integer.compare(this.getPaiType().getSize(), pai.getPaiType().getSize());
		if(size!=0){
			return size;
		}
		boolean type = this.getPaiType().getType() == pai.getPaiType().getType();
		if(!type){
			throw new RuntimeException("不可以比较");
		}
		if(this.paiType.getType()>=4 && this.paiType.getType()<=7){
			if(this.pokers.size()!=pai.getPokers().size()){
				throw new RuntimeException("不可以比较");
			}
		}
		if(this.max==null){
			throw new RuntimeException("不可以比较");
		}
		return this.max.compareTo(pai.getMax());
	}
	
}
