package mj.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import mj.net.message.login.CreateRoom;
import mj.net.message.login.OptionEntry;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author zuoge85@gmail.com on 2016/12/29.
 */
public class Config {
    public static final String CHAPTER_MAX = "chapterMax";//局数
    public static final String IS_HUIER = "IS_HUIER";//带混
    
    public static final String MAI_MA = "maiMa";
    

    public static final String IS_TUI_DAO_HU = "isTuiDaoHu";
    
    public static final String BIAN_TYPE = "bianType";

    public static final String BIAN_TYPE_HONG_ZHONG_BIAN = "hongZhongBian";
    public static final String BIAN_TYPE_BAI_BAN_BIAN = "baiBanBian";
    public static final String BIAN_TYPE_DAN_GUI = "danGui";
    public static final String BIAN_TYPE_SHUANG_GUI = "shuangGui";
    public static final String BIAN_TYPE_TUI_DAO_HU = "tuiDaoHu";

    public static final String IS_GANG_DAI_PAO = "IS_GANG_DAI_PAO";//杠带跑 ``
    public static final String IS_DAI_ZI_PAI = "IS_DAI_ZI_PAI";//带字牌 ``
    public static final String IS_QI_DUI_FAN_BEI = "IS_QI_DUI_FAN_BEI";//7对翻倍 7对胡本来就是有的 ``
    public static final String IS_ZHUANG_JIA_DI ="IS_ZHUANG_JIA_DI";//庄加底 ``
    public static final String IS_GANG_KAI_FAN_BEI = "IS_GANG_KAI_FAN_BEI";//杠开翻倍 ``
    public static final String XUAN_PAO_COUNT ="XUAN_PAO_COUNT";//选跑局数 4局 每局``
    public static final String IS_FANG_PAO="IS_FANG_PAO";//点炮胡  ``\
    public static final String USER_NUM = "user_num";//用户数量
    
    private final Map<String, String> options;

    public Config() {
        options = new TreeMap<>();
    }

    public Config(CreateRoom msg) {
        this(msg.getOptions());
    }
    
    public Config(ArrayList<OptionEntry> toptions){
    	options=toptions.stream().filter(
                e -> e.getKey() != null && e.getValue() != null
        ).collect(
                Collectors.toMap(
                        OptionEntry::getKey,
                        OptionEntry::getValue,
                        (u, v) -> {
                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                        },
                        TreeMap::new
                )
        );
    	
    }

    public Config(Map<String, String> options) {
        if (options instanceof TreeMap) {
            this.options = options;
        } else {
            this.options = new TreeMap<>(options);
        }
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }

    public int getInt(String key, int defaultValue) {
        Object o = options.get(key);
        if (o == null) {
            return defaultValue;
        } else {
            return NumberUtils.toInt(o.toString(), defaultValue);
        }
    }

    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Object o = options.get(key);
        if (o == null) {
            return defaultValue;
        } else {
            return BooleanUtils.toBoolean(o.toString(), "true", "false");
        }
    }

    public String getString(String key) {
        return getString(key, null);
    }

    public String getString(String key, String defaultValue) {
        Object o = options.get(key);
        if (o == null) {
            return defaultValue;
        } else {
            return o.toString();
        }
    }

    @Override
    public String toString() {
        return "Config{" +
                "options=" + options +
                '}';
    }

}
