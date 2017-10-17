package mj.data;

import java.util.ArrayList;

import mj.data.poker.Poker;
import mj.data.poker.PokerNum;

public class ZJHUserPaiInfo {
		/**
		 * 手牌
		 */
		private ArrayList<Poker> shouPai = new ArrayList<Poker>();
		/**
		 *牌的类型 
		 */
		private String pokerType;
		
		private int userId;
		
		private String UserName;
		
		private int score;
		
		private int locationIndex;

		public ZJHUserPaiInfo() {
			
		}
		
}
