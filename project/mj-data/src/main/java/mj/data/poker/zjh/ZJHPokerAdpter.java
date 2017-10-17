package mj.data.poker.zjh;

import mj.data.poker.Poker;

public class ZJHPokerAdpter {
	
	public ZJHPoker adpter(Poker poker) throws Exception{
		return new ZJHPoker(poker.getPokerType(),poker.getPokerNum());
		
	}

}
