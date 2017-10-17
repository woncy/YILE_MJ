package mj.data.poker.zjh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import mj.data.poker.Poker;
import mj.data.poker.PokerColorType;
import mj.data.poker.PokerNum;
public class ZJHTests {
	@Test
	public void test() throws Exception{
		ZJHPoker zp = new ZJHPoker(PokerColorType.FANG_KUAI,PokerNum.P_10);
		ZJHPoker zp2 = new ZJHPoker(PokerColorType.FANG_KUAI,PokerNum.P_8);
		ZJHPoker zp3 = new ZJHPoker(PokerColorType.FANG_KUAI,PokerNum.P_8);
		
		List<Poker> p = new ArrayList<Poker>();
		p.add(zp);
		p.add(zp2);
		p.add(zp3);
		Collections.sort(p);
		int[] pokerNum = getPokerNum(p);
		for (int i = 0; i < pokerNum.length; i++) {
			
			System.out.println(pokerNum[i]);
		}
//		
//		ZJHPoker z = new ZJHPoker(PokerColorType.FANG_KUAI,PokerNum.P_10);
//		ZJHPoker z2 = new ZJHPoker(PokerColorType.FANG_KUAI,PokerNum.P_9);
//		ZJHPoker z3 = new ZJHPoker(PokerColorType.FANG_KUAI,PokerNum.P_8);
//		
//		List<Poker> pz = new ArrayList<Poker>();
//		pz.add(z);
//		pz.add(z2);
//		pz.add(z3);
//		Collections.sort(pz);
//		
//		System.out.println(ZJHPaiType.getPaiType(pz));
//		
//		
//		System.out.println(Integer.compare(ZJHPaiType.getPaiType(pz).getWeight(), ZJHPaiType.getPaiType(p).getWeight()));
		
	}
	public static int[] getPokerNum(List<Poker> p){
		int[] pokerIndexNum = new int[3]; 
    	for (int i = 0; i < p.size(); i++) {
			int indexByPoker = Poker.getIndexByPoker(p.get(i));
			pokerIndexNum[i] = indexByPoker;
		}
    	return pokerIndexNum;
	}
	@Test
	public void testPoker(){
		List<Poker> pokers = Poker.getPokers();
		for (int i = 0; i < pokers.size(); i++) {
			System.out.println(pokers.get(i));
		}
	}
}
