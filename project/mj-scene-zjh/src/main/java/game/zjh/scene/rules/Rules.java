package game.zjh.scene.rules;

import ZJH.data.ZJHConfig;

/**
 * @author zuoge85@gmail.com on 2017/1/17.
 */
public abstract class Rules {
	public static final int DEFAUL_USER_NUM=4;
    public static Rules createRules(String name, ZJHConfig config) {
        switch (name) {
            case "ZJh":
                return new ZJHRules(config);
////            case "zhongyouGD":
////                return new ZhongyouGdRules(config);
            default:
                return new ZJHRules(config);
        }
    	
    }
    public abstract boolean rest();

    protected ZJHConfig config;


    private int shouTimeMillisecond =15000;

    public Rules(ZJHConfig config) {
        this.config = config;
    }

    public int getShouTimeMillisecond() {
        return shouTimeMillisecond;
    }
    public void setShouTimeMillisecond(int shouTimeMillisecond) {
        this.shouTimeMillisecond = shouTimeMillisecond;
    }

    
    public abstract int danzhu();
    public abstract String moShi();
    public abstract int chuShiFen();
   
    public int getUserNum(){
    	return DEFAUL_USER_NUM;
    }
    
}
