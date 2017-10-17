package mj.data.poker.pdk;

import mj.data.poker.Poker;
import mj.data.poker.PokerColorType;
import mj.data.poker.PokerNum;

   /**
	* 
    * @ClassName: PdkPoker
    * @Description: 跑的快扑克拓展
    * @author 13323659953@163.com
    * @date 2017年6月23日
    *
    */
public class PdkPoker extends Poker{

	
	    /**
	    * @Fields serialVersionUID : 序列化标识符
	    */
	
	private static final long serialVersionUID = 1L;

	//常主的大小 0表示不是常主   跑得快中规定 A,2,王为常主
	private int zhuSize;

	public PdkPoker(PokerColorType type, PokerNum num) {
		super(type, num);
		if(num==PokerNum.P_A){
			zhuSize = 1;
		}else if(num==PokerNum.P_2){
			zhuSize = 2;
		}else if(num==PokerNum.P_XIAOWANG){
			zhuSize = 3;
		}else if(num==PokerNum.P_DAWANG){
			zhuSize = 4;
		}
	}

	@Override
	public int compareTo(Poker poker) {
		if(poker instanceof PdkPoker){
			int res = Integer.compare(zhuSize, ((PdkPoker) poker).getZhuSize());
			return res == 0 ? super.compareTo(poker) : res;
		}else{
			return super.compareTo(poker);
		}
	}

	public int getZhuSize() {
		return zhuSize;
	}
	
	
	
	

}
