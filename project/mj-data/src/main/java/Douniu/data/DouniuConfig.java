package Douniu.data;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import mj.net.message.login.OptionEntry;
import mj.net.message.login.douniu.CreateDouniuRoom;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 对应的参数设置
 * @author Administrator
 */
public class DouniuConfig extends mj.data.Config {
	
	public static final String TYPE = "type";//玩法类型
	
	public static final String CHAPTER_MAX = "jushu";// 局数

	public static final int CHUSHIFEN = 0;// 进入房间初始分值

	public static final String MOSHI = "moShi";// 庄家模式
	
	public static final int USERNUM = 6;// 玩家数量
	
	public static final String ISCHECK = "ischeck";//是否看牌
	
	public static final String NIUNIUNUM = "niuniuNum";//牛牛倍数
	
	public static String USER_NUM = "USER_NUM";
    
    private final Map<String, String> options;

    public DouniuConfig() {
        options = new TreeMap<>();
    }

    public DouniuConfig(CreateDouniuRoom msg) {
        this(msg.getOptions());
    }

	public DouniuConfig(ArrayList<OptionEntry> toptions){
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

    public DouniuConfig(Map<String, String> options) {
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
		return "DouniuConfig{" + "options=" + this.getOptions() + '}';
	}
}
