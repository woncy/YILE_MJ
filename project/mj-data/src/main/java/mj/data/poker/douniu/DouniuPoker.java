package mj.data.poker.douniu;

import java.util.ArrayList;
import java.util.List;

import mj.data.poker.Poker;
import mj.data.poker.PokerColorType;
import mj.data.poker.PokerNum;

@SuppressWarnings("serial")
public class DouniuPoker extends Poker{
	public DouniuPoker() {
		super();
	}
	private int score;
	
	public DouniuPoker(Poker poker)throws Exception{
		this(poker.getPokerType(),poker.getPokerNum());
	}
	
	public DouniuPoker(PokerColorType type, PokerNum num) throws Exception {
		super(type, num);
		if("WANG".equals(this.getPokerType().getType())){
			 throw new  Exception("不能有王"); 
		}
		this.score = num.getNum();
		if(num.getNum()>=PokerNum.P_J.getNum() && num.getNum()<= PokerNum.P_K.getNum()){
			this.score = 10;
		}
		
	}
	

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public int compareTo(Poker poker) {
		return super.compareTo(poker);
	}
	public static List<DouniuPoker> toListFromIntArray(int[] pais) throws Exception{
		if(pais==null){
			return null;
		}
		List<DouniuPoker> pokers = new ArrayList<DouniuPoker>();
		for (int i = 0; i < pais.length; i++) {
			int j = pais[i];
			pokers.add(new DouniuPoker(Poker.getPokerFromIndex(j)));
		}
		return pokers;
		
	}
	
	public static int[] toIntArrayFromList(List<DouniuPoker> pokers){
		int[] pais = null;
		if(pokers!=null){
			pais = new int[pokers.size()];
			int i = 0;
			for (DouniuPoker p : pokers) {
				pais[i] = Poker.getIndexByPoker(p);
				i++;
			}
		}
		return pais;
	}
	
	public static int[] toIntArrayFromDouniuArray(DouniuPoker [] pokers){
		int[] pais = null;
		if(pokers!=null){
			pais = new int[pokers.length];
			for (int i = 0; i < pais.length; i++) {
				pais[i] = Poker.getIndexByPoker(pokers[i]);
			}
		}
		return pais;
	}
	
	public static DouniuPoker[] toDouniuArrayFromIntArray(int pais[]) throws Exception{
		DouniuPoker pokers[] = null;
		if(pais!=null){
			pokers = new DouniuPoker[pais.length];
			for (int i = 0; i < pokers.length; i++) {
				pokers[i] = new DouniuPoker(Poker.getPokerFromIndex(pais[i]));
			}
		}
		return pokers;
	}

	

}
