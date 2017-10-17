package mj.data.poker.douniu;

import mj.data.poker.Poker;
import mj.data.poker.PokerColorType;
import mj.data.poker.PokerNum;


@SuppressWarnings("serial")
public class DouniuPoker extends Poker{

	public DouniuPoker() {
		super();
	}
	
	private int size;
	

	public DouniuPoker(PokerColorType type, PokerNum num) throws Exception {
		super(type, num);
		if("WANG".equals(this.getPokerType().getType())){
			 throw new  Exception("不能有王"); 
		}
		if(num==PokerNum.P_A){
			size = PokerNum.P_K.getNum()+1;
		}else{
			size = num.getNum();
		}
	}
	

	public int getSize() {
		return size;
	}


	@Override
	public int compareTo(Poker poker) {
		if(poker instanceof DouniuPoker){
			return Integer.compare(size, ((DouniuPoker) poker).getSize());
		}else{
			throw new RuntimeException("不能比较");
		}
	}
	


	

}
