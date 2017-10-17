package game.scene.room.majiang.rules;

import game.scene.room.majiang.PaiPool;

import java.util.ArrayList;
import java.util.Map;

import mj.data.BaseFanType;
import mj.data.Config;
import mj.data.FanInfo;
import mj.data.JiaFanType;
import mj.data.Pai;

/**
 * @author zuoge85@gmail.com on 2017/1/17.
 */
public abstract class Rules {
	public static final int DEFAUL_USER_NUM=4;
    public static Rules createRules(String name, Config config) {
        switch (name) {
            case "zhengzhou":
                return new ZhengzhouRules(config);
//            case "zhongyouGD":
//                return new ZhongyouGdRules(config);
            default:
                return new ZhengzhouRules(config);
        }
    }
    public abstract boolean rest();

    protected Config config;
    private int baoliuLength = 14;


    private int shouTimeMillisecond =15000;

    public Rules(Config config) {
        this.config = config;
    }

    public int getBaoliuLength() {
        return baoliuLength;
    }
    
    public void setBaoliuLength(int baoliuLength) {
        this.baoliuLength = baoliuLength;
    }

    public int getShouTimeMillisecond() {
        return shouTimeMillisecond;
    }
    public void setShouTimeMillisecond(int shouTimeMillisecond) {
        this.shouTimeMillisecond = shouTimeMillisecond;
    }
    public abstract ArrayList<Pai> getAllPai();
    public abstract boolean isHuiEr();//带混
    public abstract Pai[] getHuiEr(PaiPool paiPool);
    public abstract boolean isChi();
    public abstract boolean isFangPao();//点炮胡
    public abstract boolean isZaMa();
    public abstract int getZaMa();
    public abstract Map<JiaFanType, FanInfo> getJiaFanMap();
    public abstract Map<BaseFanType, FanInfo> getBaseFanMap();
    public abstract boolean isHuiGang();
    public abstract int getXuanPaoCount();//选跑圈数
    public abstract boolean isGangDaiPao();
    public abstract boolean isQiDuiFanBei();
    public abstract boolean isZhuangJiaDi();
    public abstract boolean isGangKaiFanBei();
    public abstract boolean hasFeng();
    public int getUserNum(){
    	return DEFAUL_USER_NUM;
    }
    
}
