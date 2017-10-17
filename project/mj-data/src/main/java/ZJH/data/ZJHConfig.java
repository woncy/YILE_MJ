package ZJH.data;

import java.util.Map;

import mj.net.message.login.zjh.CreateZJHRoom;

public class ZJHConfig extends mj.data.Config{
	public static final String CHAPTERMAX = "chapterMax";//局数 
	
	public static final String DANZHU = "danZhu";//单注
	
	public static final String CHUSHIFEN = "chuShiFen";//进入房间初始分值
	
	public static final String MOSHI = "moShi";//庄家模式
	
	public static final String USERNUM="userNum";//玩家数量

    public ZJHConfig(CreateZJHRoom msg) {
        super(msg.getOptions());
    }
    
    public ZJHConfig(Map<String, String> ops) {
    	super(ops);
	}


    @Override
    public String toString() {
        return "Config{" +
                "options=" + this.getOptions() +
                '}';
    }

}
