package testMessage;

import java.util.Random;

public class DunTest {

	public static void main(String[] args) {
		Random random = new Random();
		for(int i = 0;i<100;i++){
			System.out.print(random.nextInt(2)+" ");
		}
	}

}
