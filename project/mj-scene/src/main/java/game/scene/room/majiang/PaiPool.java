package game.scene.room.majiang;

import java.util.ArrayList;

import org.apache.commons.lang.math.RandomUtils;

import game.scene.room.majiang.rules.Rules;
import mj.data.Pai;
import mj.data.UserPlace;

/**
 * 发牌的池子
 *
 * @author zuoge85@gmail.com on 2016/11/4.
 */
public class PaiPool {

    /**
     * 桌面剩余牌的信息
     */
    private final ArrayList<Pai> pais = new ArrayList<>();

    private Rules rules;

    public PaiPool(Rules rules) {
        this.rules = rules;
    }


    public void start() {
        //发牌
        pais.addAll(rules.getAllPai());
        pais.addAll(rules.getAllPai());
        pais.addAll(rules.getAllPai());
        pais.addAll(rules.getAllPai());
    	
    	
//        //乱序
        for (int i = 0; i < pais.size(); i++) {
            int randomIndex = RandomUtils.nextInt(pais.size());

            Pai temp = pais.get(i);
            pais.set(i, pais.get(randomIndex));
            pais.set(randomIndex, temp);
        }
    	
//    	pais.addAll(FaPaiJI.qiDui());
    }
    
    public void start(boolean hasFeng){
    	if(hasFeng){
    		pais.addAll(FaPaiJI.getFengPai());
    	}else{
    		pais.addAll(FaPaiJI.getNotHasFengPai());
    	} 
    	
    	for (int j = 0; j < 3; j++) {
    		for (int i = 0; i < pais.size(); i++) {
    			int randomIndex = RandomUtils.nextInt(pais.size());
    			Pai temp = pais.get(i);
    			pais.set(i, pais.get(randomIndex));
    			pais.set(randomIndex, temp);
    		}
		}
		
    }
    

    public void clear() {
        pais.clear();
    }

    public Pai get(int index) {
        return pais.get(index);
    }

    public int size() {
        return pais.size();
    }

    public Pai getFreePai() {
        int index = pais.size() - 1;
        return index > -1 ? pais.remove(index) : null;
    }

    public Pai getFreeGangPai() {
        for (int i = 0; i < pais.size(); i++) {
            Pai pai = pais.get(i);
            if (pai != null) {
                pais.set(i, null);
                return pai;
            }
        }
        return null;
    }

    private int[][] faPaiss;

    public void faPai(int index, UserPlace userPlace) {
        if (faPaiss != null) {
            int[] faPais = faPaiss[index];
            for (int i = 0; i < 13; i++) {
                Pai pai = Pai.fromIndex(faPais[i]);
                if (!pais.remove(pai)) {
                    throw new RuntimeException("错误的牌" + pai + ",index:" + index + ",i:" + i);
                }
                userPlace.addShouPai(pai);
            }
        } else {
            for (int j = 0; j < 13; j++) {
                userPlace.addShouPai(getFreePai());
            }
        }

    }

    public void initTest() {
        faPaiss = new int[][]{
                new int[]{0, 1, 2, 3, 4, 5, 6, 7, 7, 7, 31, 31, 31},
                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19, 19},
                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 21, 21, 21},
                new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 20, 20, 20, 20},
        };

//        faPaiss = new int[][]{
//                new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6},
//                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 19, 19, 19},
//                new int[]{10, 11, 12, 13, 14, 15, 16, 17, 18, 21, 21, 21, 21},
//                new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 20, 20, 20, 20},
//        };
    }

    public ArrayList<Pai> getLeftPai() {
        return pais;
    }
    

    public ArrayList<Pai> getPais() {
		return pais;
	}


	public Pai[] getHuiEr() {
        //计算会儿
        return rules.getHuiEr(this);
    }


	public Pai changePai(Pai pai) {
		pais.add(pai);
		if(pais.size()-2>-1){
			Pai retPai = pais.get(pais.size()-2);
			if(retPai!=null){
				pais.set(pais.size()-2, pai);
				pais.remove(pais.size()-1);
				return retPai;
			}
		}
		return pais.remove(pais.size()-1);
	}

	private boolean isIn(int index,int[] indexs){
		if(indexs==null){
			return false;
		}
		for (int i = 0; i < indexs.length; i++) {
			if(indexs[i] == index){
				return true;
			}
		}
		return false;
	}

	public Pai changeHuPai(Pai pai, int[] pais2) {
		pais.add(pai);
		for (int i = 0; i < pais.size(); i++) {
			Pai pai2 = pais.get(i);
			if(pai2!=null){
				if(isIn(pai2.getIndex(), pais2)){
					pais.set(i, pai);
					pais.set(pais.size()-1, pai2);
					break;
				}
			}
		}
		return pais.remove(pais.size()-1);
	}
}
