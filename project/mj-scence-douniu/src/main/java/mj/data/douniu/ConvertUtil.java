package mj.data.douniu;

import java.util.ArrayList;
import mj.data.poker.douniu.DouniuPai;
import mj.net.message.game.douniu.DouniuOnePai;

public class ConvertUtil {
	
	public static  ArrayList<DouniuOnePai>  convertToOnePai(ArrayList<DouniuPai>  paiList){
		ArrayList <DouniuOnePai>  list = null;
		if(paiList != null &&  paiList.size() > 0 ){
			 list = new ArrayList <DouniuOnePai>();
		}else{
			return null; 
		}
		for(int i = 0 ; i < paiList.size() ; i++){
			DouniuPai pai = paiList.get(i);
			if(pai != null){
				DouniuOnePai onePai = convertToOnePai(pai);
				if(onePai != null){
					list.add(onePai);
				}
			}
		}
		return list;
	}

	
	/*
	 * 将一张斗牛的牌转化成返回给前端的牌的样式
	 */
	public static DouniuOnePai  convertToOnePai(DouniuPai  pai){
		DouniuOnePai onePai = null;
		if(pai != null){
			onePai= new DouniuOnePai();
		    onePai.setPokerNum(pai.getPokerNum().getNum());
		    onePai.setPokerSuit(pai.getPokerType().getSize());
		    onePai.setPokerValue(pai.getPokerValue());
		    return onePai;
		}else{
			return null;
		}
	}

}
