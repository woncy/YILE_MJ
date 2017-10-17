package mj.data.poker.pdk;

import mj.data.poker.Poker;

public class PdkPokerAdapter{
	
	public PdkPoker pokerToPdkPoker(Poker poker){
		PdkPoker pdkPoker = new PdkPoker(poker.getPokerType(), poker.getPokerNum());
		poker = null;
		return pdkPoker;
		
	}
	
	
	public Poker pdkPokerToPoker(PdkPoker pdkPoker){
		Poker poker = new Poker(pdkPoker.getPokerType(),pdkPoker.getPokerNum());
		pdkPoker = null;
		return poker;
	}

}
