package game.scene.douniu.room.poker;

import java.util.ArrayList;
import org.apache.commons.lang.math.RandomUtils;
import Douniu.data.DouniuUserPlace;
import game.scene.douniu.room.poker.rules.Rules;
import mj.data.poker.douniu.DouniuPai;

/**
 * 发牌的池子
 * @author zuoge85@gmail.com on 2016/11/4.
 */
public class PaiPool {
	/**
     * 桌面剩余牌的信息
     */
    private final ArrayList<DouniuPai> pais = new ArrayList<>();

    private Rules rules;

    public PaiPool(Rules rules) {
        this.rules = rules;
    }
    
    public PaiPool() {
    	
    }
	
    public void start() {   
    	     //发牌
        pais.addAll(rules.getAllPai());
        //乱序
        for (int i = 0; i < pais.size(); i++) {
            int randomIndex = RandomUtils.nextInt(pais.size());
            DouniuPai temp = pais.get(i);
            pais.set(i, pais.get(randomIndex));
            pais.set(randomIndex, temp);
        }
        System.out.println(pais.size()+"??????????????");
    }
    
    //斗牛发牌
    public void faPai(int index, DouniuUserPlace userPlace, int paiCount) { 
    	if(paiCount > 0 ){
	    	for (int j = 0; j < paiCount ; j++) {
	    		DouniuPai pai = getFreePai();
	    		userPlace.addShouPai(pai);
	    		System.out.println(pai.getName());
			}     
    	}
    }
    
    public int size() {
        return pais.size();
    }

	public DouniuPai get(int index) {
        return pais.get(index);
    }

	public DouniuPai getFreePai() {
        int index = pais.size() -1;
        return index > -1 ? pais.remove(index) : null;
    }
	
	public void clear() {
        pais.clear();
    }

}
