package game.scene.douniu.room.poker.rules;

import java.util.ArrayList;

import Douniu.data.DouniuConfig;
import mj.data.poker.PokerColorType;
import mj.data.poker.PokerNum;
import mj.data.poker.douniu.DouniuPai;

public class JingjiDouniuRule extends Rules{

	public JingjiDouniuRule(DouniuConfig config) {
		super(config);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean rest() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<DouniuPai> getAllPai() {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		ArrayList<DouniuPai> pais = new ArrayList<DouniuPai>();
		for(int i = 1 ;i <= 4  ;i++){
			PokerColorType pokerColorType = PokerColorType.fromIndex(i);
			for(int j = 1 ;j<= 13 ;j++){
				PokerNum  pokerNum = PokerNum.fromIndex(j);
				DouniuPai pai = new DouniuPai(pokerColorType, pokerNum);
				pais.add(pai);
			}
		}
		return pais;
	
	}

	@Override
	public int getInitFaPaiCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public boolean isQingZhuang() {
		// TODO Auto-generated method stub
		return false;
	}

}
