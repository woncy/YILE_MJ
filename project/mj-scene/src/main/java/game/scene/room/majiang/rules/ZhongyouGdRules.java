package game.scene.room.majiang.rules;

import game.scene.room.majiang.PaiPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mj.data.BaseFanType;
import mj.data.Config;
import mj.data.FanInfo;
import mj.data.JiaFanType;
import mj.data.Pai;

import org.apache.commons.lang.math.RandomUtils;


/**
 * @author zuoge85@gmail.com on 2016/10/24.
 */
class ZhongyouGdRules extends Rules {
    private static final ArrayList<Pai> ALL_PAI_LIST = createAllList();
    private static final ArrayList<Pai> ALL_PAI_LIST_TUI_DAOHU = createGuiDaoHuAllList();
    private static final ArrayList<Pai> ALL_PAI_LIST_GUI = createGuiAllList();

    public static final Map<JiaFanType, FanInfo> jiaFanMap = initJiaFanMap();
    public static final Map<BaseFanType, FanInfo> baseFanMap = initBaseFanMap();

    private static Map<JiaFanType, FanInfo> initJiaFanMap() {
        Map<JiaFanType, FanInfo> map = new HashMap<>();
        map.put(JiaFanType.QUEYILU_HU, new FanInfo("缺一路", 1));
        map.put(JiaFanType.QUEERLU_HU, new FanInfo("缺二路", 3));
        map.put(JiaFanType.QUESANLU_HU, new FanInfo("缺三路", 5));
        map.put(JiaFanType.QINGYISE_HU, new FanInfo("清一色", 10));
        map.put(JiaFanType.JIA_AN_GANG, new FanInfo("加暗杠", 0));
        map.put(JiaFanType.JIA_MING_GANG, new FanInfo("加明杠", 0));
        return map;
    }

    private static Map<BaseFanType, FanInfo> initBaseFanMap() {
        Map<BaseFanType, FanInfo> map = new HashMap<>(BaseFanType.baseFanMap);
        map.replace(BaseFanType.ZI_MO, new FanInfo("自摸", 4));
//        map.replace(BaseFanType.JI_HU, new FanInfo("点炮", 1));
        //自摸1番，明杠1番，暗杠2番，中一个码加1番
        return map;
    }

    private static ArrayList<Pai> createAllList() {
        ArrayList<Pai> list = createGuiAllList();

        list.add(Pai.SANYUAN_ZHONG);
        list.add(Pai.SANYUAN_FA);
        list.add(Pai.SANYUAN_BEI);
        return list;
    }

    private static ArrayList<Pai> createGuiDaoHuAllList() {
        ArrayList<Pai> list = createGuiAllList();

        list.add(Pai.SANYUAN_ZHONG);
        list.add(Pai.SANYUAN_FA);
        list.add(Pai.SANYUAN_BEI);
        list.add(Pai.FENG_DONG);
        list.add(Pai.FENG_NAN);
        list.add(Pai.FENG_XI);
        list.add(Pai.FENG_BEI);
        return list;
    }

    private static ArrayList<Pai> createGuiAllList() {
        ArrayList<Pai> list = new ArrayList<>();

        for (int paiIndex = Pai.TONG_1.getIndex(); paiIndex <= Pai.WAN_9.getIndex(); paiIndex++) {
            Pai pai = Pai.fromIndex(paiIndex);
            list.add(pai);
        }
        return list;
    }
    

    @Override
	public boolean hasFeng() {
		// TODO 自动生成的方法存根
		return false;
	}


	private Pai[] huiErs;


    ZhongyouGdRules(Config config) {
        super(config);
    }


    @Override
    public boolean rest() {
        String binType = config.getString(Config.BIAN_TYPE);
        switch (binType) {
            case Config.BIAN_TYPE_DAN_GUI: {
                ArrayList<Pai> allPai = getAllPai();
                huiErs = new Pai[]{allPai.get(RandomUtils.nextInt(allPai.size()))};
                break;
            }
            case Config.BIAN_TYPE_SHUANG_GUI: {
                ArrayList<Pai> allPai = getAllPai();
                Pai pai = allPai.get(RandomUtils.nextInt(allPai.size()));
                huiErs = new Pai[]{pai, pai.next()};
                break;
            }
            case Config.BIAN_TYPE_HONG_ZHONG_BIAN: {
                huiErs = new Pai[]{Pai.SANYUAN_ZHONG};
                break;
            }
            case Config.BIAN_TYPE_BAI_BAN_BIAN: {
                huiErs = new Pai[]{Pai.SANYUAN_BEI};
                break;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Pai> getAllPai() {
        String binType = config.getString(Config.BIAN_TYPE);
        if (Config.BIAN_TYPE_DAN_GUI.equals(binType) || Config.BIAN_TYPE_SHUANG_GUI.equals(binType)) {
            return ALL_PAI_LIST_GUI;
        } else if(Config.BIAN_TYPE_TUI_DAO_HU.equals(binType)) {
            return ALL_PAI_LIST_TUI_DAOHU;
        }else {
            return ALL_PAI_LIST;
        }
    }


    @Override
    public boolean isHuiGang() {
        String binType = config.getString(Config.BIAN_TYPE);
        if (Config.BIAN_TYPE_DAN_GUI.equals(binType) || Config.BIAN_TYPE_SHUANG_GUI.equals(binType)) {
            return false;
        }  else if(Config.BIAN_TYPE_TUI_DAO_HU.equals(binType)) {
            return false;
        }else {
            return true;
        }
    }

    @Override
    public Pai[] getHuiEr(PaiPool paiPool) {
        return huiErs;
    }

    @Override
    public boolean isChi() {
        return false;
    }

    @Override
    public boolean isFangPao() {
        return true;//;;config.getBoolean(Config.IS_TUI_DAO_HU);
    }

    @Override
    public boolean isZaMa() {
        int maiMa = config.getInt(Config.MAI_MA);
        return maiMa != 0;
    }

    @Override
    public int getZaMa() {
        return config.getInt(Config.MAI_MA);
    }

    @Override
    public Map<JiaFanType, FanInfo> getJiaFanMap() {
        if (config.getBoolean(Config.IS_TUI_DAO_HU)) {
            return JiaFanType.jiaFanMap;
        }
        return jiaFanMap;
    }

    @Override
    public Map<BaseFanType, FanInfo> getBaseFanMap() {
        return baseFanMap;
    }


    public int getBaoliuLength() {
        int maiMa = config.getInt(Config.MAI_MA);
        int length = 0;
        if (maiMa == -1) {
            length = 1;
        } else {
            length = maiMa;
        }
        return length;
    }

    /* (non-Javadoc)
     * @see game.scene.room.majiang.rules.Rules#isHuiEr()
     */
    @Override
    public boolean isHuiEr() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see game.scene.room.majiang.rules.Rules#getXuanPaoCount()
     */
    @Override
    public int getXuanPaoCount() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see game.scene.room.majiang.rules.Rules#isGangDaiPao()
     */
    @Override
    public boolean isGangDaiPao() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see game.scene.room.majiang.rules.Rules#isQiDuiFanBei()
     */
    @Override
    public boolean isQiDuiFanBei() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see game.scene.room.majiang.rules.Rules#isZhuangJiaDi()
     */
    @Override
    public boolean isZhuangJiaDi() {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see game.scene.room.majiang.rules.Rules#isGangKaiFanBei()
     */
    @Override
    public boolean isGangKaiFanBei() {
        // TODO Auto-generated method stub
        return false;
    }
}
