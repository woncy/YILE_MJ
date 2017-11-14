package mj.data.poker.pdk;

import java.util.Arrays;

import org.junit.Test;

import mj.data.poker.douniu.DouniuPai;
import mj.data.poker.douniu.DouniuPaiType;
import mj.data.poker.douniu.DouniuPoker;

public class PdkPokerTest {

	@Test
	public void test() {
		System.out.println(DouniuPaiType.fromIndex(1));
	}
	@Test
	public void testDN(){
		int[] pais = new int[]{33, 15, 19, 48, 1};
		try {
			DouniuPoker[] pokers = DouniuPoker.toDouniuArrayFromIntArray(pais);
			DouniuPai pai = new DouniuPai(pokers);
			System.out.println(Arrays.toString(pokers));
			System.out.print(pai.getType());
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
	

}
