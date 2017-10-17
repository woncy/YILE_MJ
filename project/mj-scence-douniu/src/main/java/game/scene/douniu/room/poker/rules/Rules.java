package game.scene.douniu.room.poker.rules;

import java.util.ArrayList;
import Douniu.data.DouniuConfig;
import mj.data.poker.douniu.DouniuPai;

/**
 * @author zuoge85@gmail.com on 2017/7/10.
 *
 */
public abstract class Rules {
	public static final int DEFAUL_USER_NUM = 6;
    public static Rules createRules(String name, DouniuConfig config) {
        switch (name) {
        case "qiangzhuang":
                return new FangKaDouniuRule(config);
//		case "jingji":
//            	return new JingjiDouniuRule(config);
        default:
            return new FangKaDouniuRule(config);
        }
    }
    public abstract boolean rest();

    protected DouniuConfig config;

    private int shouTimeMillisecond =15000;

    public Rules(DouniuConfig config) {
        this.config = config;
    }

    public int getShouTimeMillisecond() {
        return shouTimeMillisecond;
    }
    public void setShouTimeMillisecond(int shouTimeMillisecond) {
        this.shouTimeMillisecond = shouTimeMillisecond;
    }
    public abstract ArrayList<DouniuPai> getAllPai();
    
    public int getUserNum(){
    	return DEFAUL_USER_NUM;
    }
    
    /*
     * 初始化发牌张数
     */
    public abstract int getInitFaPaiCount();
    /*
     * 是否是抢庄模式
     */
    public abstract boolean isQingZhuang(); 
}