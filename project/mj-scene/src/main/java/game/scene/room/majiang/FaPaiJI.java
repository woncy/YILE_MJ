package game.scene.room.majiang;

import java.util.ArrayList;

import org.apache.commons.lang.math.RandomUtils;

import mj.data.Pai;

public class FaPaiJI {
	
	
	public static ArrayList<Pai> qiDui(){
		ArrayList<Pai> pais = new ArrayList<Pai>();
		
		
		for (int i = Pai.TONG_1.getIndex(); i <= Pai.SANYUAN_BEI.getIndex(); i++) {
			for (int j = 0; j < 4; j++) {
				if(i<=6){
					if(j<2){
						Pai pai = Pai.fromIndex(i);
						pais.add(pai);
					}
				}else{
					Pai pai = Pai.fromIndex(i);
					pais.add(pai);
				}
			}
		}
		pais.add(Pai.fromIndex(5));
		pais.add(Pai.fromIndex(5));
		//洗牌
		xiPai(pais);
		
		for (int i = Pai.TONG_1.getIndex(); i <= 6; i++) {
//			pais.add(pais.get(13).next());
//			pais.add(pais.get(13).next().next());
//			pais.add(pais.get(13).prev());
			if(i!=5){
				for (int j = 0; j < 2; j++) {
					Pai pai = Pai.fromIndex(i);
					pais.add(pai);
				}
			}
		}
		
		
		
		
		return pais;
	}
	
	
	
	public static ArrayList<ArrayList<Pai>> controlWinPai(int index,int num,boolean hasfeng){
		ArrayList<Pai> all = new ArrayList<Pai>();
		
		ArrayList<ArrayList<Pai>> userPais = new ArrayList<ArrayList<Pai>>(num);
		
		ArrayList<Pai> winPai = new ArrayList<Pai>();
		
		if(hasfeng){
			all.addAll(getFengPai());
		}else{
			all.addAll(getNotHasFengPai());
		}
		
		
		return userPais;
		
	}
	
	
	public static void xiPai(ArrayList<Pai> pais){
		for (int i = 0; i < pais.size(); i++) {
            int randomIndex = RandomUtils.nextInt(pais.size());
            Pai temp = pais.get(i);
            pais.set(i, pais.get(randomIndex));
            pais.set(randomIndex, temp);
        }
	}
	
	
	
	public static ArrayList<Pai> getNotHasFengPai(){
		ArrayList<Pai> pais = new ArrayList<Pai>();
		for(int c=0;c<4;c++)
			for (int i = 0; i <= Pai.WAN_9.getIndex(); i++) {
				Pai pai = Pai.fromIndex(i);
				pais.add(pai);
			}
		return pais;
	}
	
	public static  ArrayList<Pai> getFengPai(){
		ArrayList<Pai> pais = new ArrayList<Pai>();
		for(int c=0;c<4;c++)
			for (int i = 0; i <= Pai.SANYUAN_BEI.getIndex(); i++) {
				Pai pai = Pai.fromIndex(i);
				pais.add(pai);
			}
		return pais;
	}
	

}
