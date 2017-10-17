package game.zjh.scene.zjh;

import java.util.List;

import com.isnowfox.util.RandomUtils;

import game.zjh.scene.rules.Rules;
import mj.data.poker.Poker;
import mj.data.poker.zjh.ZJHPoker;

public class PokerPool {
	private static List<Poker> pokers;
	private static ZJHPoker zp = new ZJHPoker();

	private final Rules rules;
	 public PokerPool(Rules rules) {
	        this.rules = rules;
	    }
	
	/**
	 * 乱序
	 *@return
	 * 2017年6月26日
	 */
	public static void start(){
		pokers = Poker.getPokers();
		pokers.remove(pokers.size() - 1);
		pokers.remove(pokers.size() - 1);
		for (int i = 0; i < pokers.size(); i++) {
			int random = RandomUtils.nextInt(pokers.size());
			Poker poker = pokers.get(random);
			pokers.set(random, pokers.get(i));
			pokers.set(i, poker);
		}
		System.out.println("12");
	}
	
	
	
	/**
	 * 发牌
	 *@return
	 *@return
	 * 2017年7月10日
	 */
	public Poker getFreePoker() {
        int index = pokers.size() - 1;
        return index > -1 ? pokers.remove(index) : null;
    }
	
}
