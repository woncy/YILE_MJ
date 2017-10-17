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
 * @Package:game.scene.room.majiang.rules
 * @ClassName: ZhengzhouRules
 * @Description: TODO
 * @author XuKaituo
 * @date May 12, 2017  9:57:56 AM
 */
public class ZhengzhouRules extends Rules{


    private static ArrayList<Pai> createDaiziAllList() {
        ArrayList<Pai> list = createBuDaiZiAllList();

        list.add(Pai.SANYUAN_ZHONG);
        list.add(Pai.SANYUAN_FA);
        list.add(Pai.SANYUAN_BEI);
        list.add(Pai.FENG_DONG);
        list.add(Pai.FENG_NAN);
        list.add(Pai.FENG_XI);
        list.add(Pai.FENG_BEI);
        return list;
    }

    private static ArrayList<Pai> createBuDaiZiAllList() {
        ArrayList<Pai> list = new ArrayList<>();

        for (int paiIndex = Pai.TONG_1.getIndex(); paiIndex <= Pai.WAN_9.getIndex(); paiIndex++) {
            Pai pai = Pai.fromIndex(paiIndex);
            list.add(pai);
        }
        return list;
    }
    
    /**
    *构造方法 ZhengzhouRules.
    * @param config
    */
    public ZhengzhouRules(Config config) {
        super(config);
    }
    
    @Override
    public boolean rest() {
        return false;
    }
    
    @Override
    public ArrayList<Pai> getAllPai() {
        //test
//       if(config.getBoolean(Config.IS_DAI_ZI_PAI)){ 
           return createDaiziAllList();
//       }else{
//           return createBuDaiZiAllList();
//       }
    }

    @Override
    public Pai[] getHuiEr(PaiPool paiPool) {
        if (isHuiEr()) {
            Pai hunPeiEr = paiPool.get(13);
            return new Pai[]{hunPeiEr.next()};
        }
        return null;
    }
    
    @Override
    public boolean isChi() {//是否吃
        return false;
    }

    @Override
    public boolean isFangPao() {
//        return true;//test
        return config.getBoolean(Config.IS_FANG_PAO);
    }
    
    @Override
    public boolean isZaMa() {//没用到
        return false;
    }

    @Override
    public int getZaMa() {//没用到
        return 0;
    }

    /**
     * 计算分数加倍的地方，用到再实现
     */
    @Override
    public Map<JiaFanType, FanInfo> getJiaFanMap() {
//        return JiaFanType.jiaFanMap;
        return null;
    }
    @Override
    public Map<BaseFanType, FanInfo> getBaseFanMap() {
        return BaseFanType.baseFanMap;
    }

    /**
     * 4张混牌
     */
    @Override
    public boolean isHuiGang() {//是否4张混牌可以胡 郑州麻将是可以的
        return true;
    }

    @Override
    public boolean isHuiEr() {
//        return true;//test
       return config.getBoolean(Config.IS_HUIER);
    }

    @Override
    public int getXuanPaoCount() {//定跑的运用
        int xuanPaoCount = config.getInt(Config.XUAN_PAO_COUNT);//默认每局都选跑
        return xuanPaoCount==0?1:xuanPaoCount;
    }

    @Override
    public boolean isGangDaiPao() {
        return config.getBoolean(Config.IS_GANG_DAI_PAO);
    }

    @Override
    public boolean isQiDuiFanBei() {
//        return true;//test
        return config.getBoolean(Config.IS_QI_DUI_FAN_BEI);
    }

    @Override
    public boolean isZhuangJiaDi() {
        return config.getBoolean(Config.IS_ZHUANG_JIA_DI);
    }

    @Override
    public boolean isGangKaiFanBei() {
        
//        return true;//test
        return config.getBoolean(Config.IS_GANG_KAI_FAN_BEI);
    }

	@Override
	public boolean hasFeng() {
		return config.getBoolean(Config.IS_DAI_ZI_PAI);
	}

	@Override
	public int getUserNum() {
		return config.getInt(Config.USER_NUM);
	}
    

}
