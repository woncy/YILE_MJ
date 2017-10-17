package mj.data.poker;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
    * @ClassName: Poker
    * @Description: Poker基类，请勿修改，有需要请拓展
    * @author 13323659953@163.com
    * @date 2017年6月23日
    *
 */
public class Poker implements Comparable<Poker>,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PokerColorType type;
	private PokerNum num;
	private String name;
	private static List<Poker> pokers = new ArrayList<Poker>();
	public Poker() {
		
	}
	
	static {
		for (int j = PokerNum.P_A.getNum(); j <= PokerNum.P_K.getNum(); j++) {
			for (int i = PokerColorType.FANG_KUAI.size(); i <= PokerColorType.HEI_TAO.size(); i++) {
				Poker poker = new Poker(PokerColorType.getColorTypeBySize(i),PokerNum.fromIndex(j));
				pokers.add(poker);
			}
		}
		pokers.add(new Poker(PokerColorType.WNAG,PokerNum.P_XIAOWANG));
		pokers.add(new Poker(PokerColorType.WNAG,PokerNum.P_DAWANG));
		
	}
	/**
	 * 按照黑红梅方A-K顺序大小排列的一幅牌的集合
	 * 示例：
	 * 	方块A,梅花A,红桃A,黑桃A...红桃K,黑桃K,小王,大王
	 * 
	 * @return
	 */
	public static List<Poker> getPokers(){
		return pokers;
	}
	
	public Poker(PokerColorType type, PokerNum num) {
		super();
		this.type = type;
		this.num = num;
		this.name = type.getName()+num.getName();
		
		
	}
	
	

	public PokerColorType getPokerType() {
		return type;
	}

	public PokerNum getPokerNum() {
		return num;
	}
	public String getName() {
		return name;
	}
	
	public static Poker getPokerFromIndex(int index){
		return pokers.get(index);
	}
	/**
	 * 根據poker獲取下標
	 *@param poker
	 *@return
	 *@return
	 * 2017年7月5日
	 */
	public static int getIndexByPoker(Poker poker){
		PokerColorType type = poker.getPokerType();
		PokerNum num = poker.getPokerNum();
		if(type!=PokerColorType.WNAG){
			return (num.getNum()-1)*4+type.size()-1;
		}else{
			if(poker.getPokerNum()==PokerNum.P_XIAOWANG){
				return 52;
			}else{
				return 53;
			}
		}
	}

	public void setNum(PokerNum num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Poker poker) {
		int numCompare = this.num.compare(poker.getPokerNum());
		return  numCompare !=0 ? numCompare:this.type.compare(poker.getPokerType());
	}
	
	
}
