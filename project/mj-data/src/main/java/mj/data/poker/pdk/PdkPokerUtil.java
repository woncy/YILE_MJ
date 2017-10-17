package mj.data.poker.pdk;

import java.util.ArrayList;
import java.util.List;

import mj.data.poker.Poker;
import mj.data.poker.PokerColorType;
import mj.data.poker.PokerNum;

public class PdkPokerUtil {
	public List<PdkPai> getPais(List<List<Poker>> pais,List<Poker> shouPai ,PdkPai pai){
		if(pais==null){
			return null;
		}
		int shoupaiLength = shouPai.size();
		List<PdkPai> result = new ArrayList<PdkPai>();
		int type = pai.getPaiType().getType();
		if(type == PdkPaiType.DANZHANG.getType()){
			for(List<Poker> lPoker: pais){
				if(lPoker!=null)
					for(Poker p:lPoker ){
						List<Poker> pokers = new ArrayList<Poker>();
						PdkPai danzhang = new PdkPai(pokers);
						if(danzhang.compareTo(pai)>0){
							result.add(danzhang);
						}
					}
			}//for
		}else if(type == PdkPaiType.DUIZI.getType()){
			for(List<Poker> lPokers:pais){
				if(lPokers!=null){
					int count = lPokers.size();
					if(count==2){
						PdkPai duizi = new PdkPai(lPokers);
						if(duizi.compareTo(pai)>0)
							result.add(duizi);
					}else if(count>2){
						List<Poker> pokers = new ArrayList<Poker>();
						pokers.add(lPokers.get(0));
						pokers.add(lPokers.get(1));
						PdkPai duizi = new PdkPai(lPokers);
						if(duizi.compareTo(pai)>0)
							result.add(duizi);
					}
				}
			}//for
		}else if(type == PdkPaiType.SANBUDAI.getType()){
			for(List<Poker> lPokers:pais){
				if(lPokers!=null){
					int count = lPokers.size();
					if(count>=3){
						List<Poker> pokers = new ArrayList<Poker>();
						pokers.add(lPokers.get(0));
						pokers.add(lPokers.get(1));
						pokers.add(lPokers.get(2));
						PdkPai sandai = new PdkPai(lPokers);
						if(sandai.compareTo(pai)>0)
							result.add(sandai);
					}
				}
			}
		}else if(type == PdkPaiType.DANSHUN.getType()){
			int danShunlength = pai.getPokers().size();
			List<Poker> danShun = pai.getPokers();
			Poker first = danShun.get(0);
			boolean[] shun = new boolean[12];
			for (int i = 0; i < shoupaiLength; i++) {
				Poker curPoker = shouPai.get(i);
				if(curPoker.getPokerType()!=PokerColorType.WNAG){
					continue;
				}
				
				if(curPoker.getPokerNum()!=PokerNum.P_A){
					if(!shun[curPoker.getPokerNum().getNum()-2])
						shun[curPoker.getPokerNum().getNum()-2] = true;
				}else{
					shun[11] = true;
				}
			}
			int minStartNum = first.getPokerNum().getNum()+1;
			int maxstartNum = 14-first.getPokerNum().getNum()+1;
			for (int i = minStartNum; i <= maxstartNum; i++) {
				int lxlength = 0;
				for (int j = i; j < danShunlength; j++) {
					if(shun[j-2]){
						lxlength = lxlength+1;
					}else{
						lxlength = 0;
					}
				}
				
				if (lxlength==danShunlength) {
					ArrayList<Poker> pokers = new ArrayList<Poker>();
					for (int j = i; j < danShunlength; j++) {
						pokers.add(pais.get(j-1).get(0));
					}
					PdkPai pdkPai = new PdkPai(pokers);
					result.add(pdkPai);
				}
				
			}
		}else if(type == PdkPaiType.SHUANGSHUN.getType()){
			
			
		}else if(type == PdkPaiType.SANSHUN.getType()){
			
		}else if(type == PdkPaiType.FEIJI.getType()){
			
		}else if(type == PdkPaiType.SIDAISAN.getType()){
			
		}else if(type == PdkPaiType.ZHADAN.getType()){
			
		}
		
		return result;
		
	}
	
	public static ArrayList<PdkPoker> getPokersByNum(int paiNum){
		if(paiNum<15 || paiNum>16){
			throw new RuntimeException("不正确的牌数量");
		}
		ArrayList<PdkPoker> pokers = new ArrayList<PdkPoker>();
		List<Poker> pokers2 = Poker.getPokers();
		PdkPokerAdapter adapter = new PdkPokerAdapter();
		for (int i = 0; i < pokers2.size(); i++) {
			pokers.add(adapter.pokerToPdkPoker(pokers2.get(i)));
		}
		ArrayList<PdkPoker> rmPokekrs = new ArrayList<PdkPoker>();
		rmPokekrs.add(new PdkPoker(PokerColorType.WNAG,PokerNum.P_DAWANG));
		rmPokekrs.add(new PdkPoker(PokerColorType.WNAG,PokerNum.P_XIAOWANG));
		rmPokekrs.add(new PdkPoker(PokerColorType.FANG_KUAI, PokerNum.P_2));
		rmPokekrs.add(new PdkPoker(PokerColorType.MEI_HUA, PokerNum.P_2));
		rmPokekrs.add(new PdkPoker(PokerColorType.HONG_TAO, PokerNum.P_2));
		rmPokekrs.add(new PdkPoker(PokerColorType.FANG_KUAI, PokerNum.P_A));
		if(paiNum == 15){
			rmPokekrs.add(new PdkPoker(PokerColorType.MEI_HUA, PokerNum.P_A));
			rmPokekrs.add(new PdkPoker(PokerColorType.HONG_TAO, PokerNum.P_A));
			rmPokekrs.add(new PdkPoker(PokerColorType.FANG_KUAI, PokerNum.P_K));
		}
		for (int i = 0; i < rmPokekrs.size(); i++) {
			pokers.remove(Poker.getIndexByPoker(rmPokekrs.get(i)));
		}
		return pokers;
		
	}
	
	public static ArrayList<Poker> getPokersByIndexs(List<Integer> pokerIndexs){
		PdkPokerAdapter adapter = new PdkPokerAdapter();
		if(pokerIndexs ==null || pokerIndexs.size()<=0){
			throw new RuntimeException("牌索引集合为空");
		}
		ArrayList<Poker> pokers = new ArrayList<Poker>();
		for (int i = 0; i < pokerIndexs.size(); i++) {
			pokers.add(adapter.pokerToPdkPoker(Poker.getPokerFromIndex(pokerIndexs.get(i))));
		}
		
		return pokers;
		
	}
	
	public static ArrayList<Integer> getIndexsByPokers(List<Poker> pokers){
		if(pokers==null || pokers.size()<=0){
			throw new RuntimeException("牌集合为空");
		}
		
		ArrayList<Integer> indexs = new ArrayList<Integer>();
		
		for (int i = 0; i < pokers.size(); i++) {
			indexs.add(Poker.getIndexByPoker(pokers.get(i)));
			
		}
		
		return indexs;
	}
	
}
