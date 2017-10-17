package mj.data.poker;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public enum PokerNum {
			P_A(1,"A"),
			P_2(2,"2"),
			P_3(3,"3"),
			P_4(4,"4"),
			P_5(5,"5"),
			P_6(6,"6"),
			P_7(7,"7"),
			P_8(8,"8"),
			P_9(9,"9"),
			P_10(10,"10"),
			P_J(11,"J"),
			P_Q(12,"Q"),
			P_K(13,"K"),
			P_XIAOWANG(99,"小王"),
			P_DAWANG(100,"大王");
			
		private final int num;
		private final String name;
		private static final Map<Integer, PokerNum> numMap = Arrays.stream(PokerNum.values()).collect(
	            Collectors.toMap(PokerNum::getNum, v -> v)
	    );
		
		
		PokerNum(int num, String name){
			this.num = num;
			this.name = name;
		}

		public int getNum() {
			return num;
		}
		

		public String getName() {
			
			return name;
		}

		public static PokerNum fromIndex(int num){
			return numMap.get(num);
		}	
		
		@Override
		public String toString() {
			return this.name;
		}

		public int compare(PokerNum num) {
			return Integer.compare(this.num, num.getNum());
		}
		
		
			
}
