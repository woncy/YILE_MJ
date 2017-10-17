package mj.data.poker;

import java.util.Collections;
import java.util.List;

import com.isnowfox.util.RandomUtils;

public class PokerUtil {
	/**
	 * 洗牌
	 *@param pokers
	 *@return
	 * 2017年7月10日
	 */
	@SuppressWarnings("unchecked")
	public static void xiPai(List pokers) {
		for (int i = 0; i < pokers.size(); i++) {
			int random = RandomUtils.nextInt(pokers.size());
			Object poker = pokers.get(random);
			pokers.set(random, pokers.get(i));
			pokers.set(i, poker);
		}
		
		
		
	}
	public static void main(String[] args) {
		List<Poker> pokers = Poker.getPokers();
		xiPai(pokers);
		
		Collections.sort(pokers);
		for (int i = 0; i < pokers.size(); i++) {
			System.out.println(pokers.get(i));
		}
	}
}
