package game.scene.douniu.room.poker.rules;

import java.util.ArrayList;

import Douniu.data.DouniuConfig;
import mj.data.poker.PokerColorType;
import mj.data.poker.PokerNum;
import mj.data.poker.douniu.DouniuPai;

public class FangKaDouniuRule extends Rules {

	public FangKaDouniuRule(DouniuConfig config) {
		super(config);
	}

	@Override
	public boolean rest() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see game.scene.douniu.room.poker.rules.Rules#getAllPai()
	 * 封装斗牛中的牌的信息。
	 */
	
	@Override
	public ArrayList<DouniuPai> getAllPai() {
		// TODO Auto-generated method stub
		ArrayList<DouniuPai> pais = new ArrayList<DouniuPai>();
		for(int i = 1 ;i <= 4  ;i++){
			PokerColorType pokerColorType = PokerColorType.fromIndex(i);
			for(int j = 1 ;j<= 13 ;j++){
				PokerNum  pokerNum = PokerNum.fromIndex(j);
				DouniuPai pai = new DouniuPai(pokerColorType, pokerNum);
				while(true){
					if(!pais.contains(pai)){   //这里增加个判断，否则会发重复牌
			     		pais.add(pai);
						break;
					}
				}
				
			}
		}
		return pais;
	}

	@Override
	public int getInitFaPaiCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public boolean isQingZhuang() {
		// TODO Auto-generated method stub
		return true;
	}

}
