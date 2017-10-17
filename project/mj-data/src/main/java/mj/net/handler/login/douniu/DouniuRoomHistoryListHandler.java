package mj.net.handler.login.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.login.douniu.DouniuRoomHistoryList;

/**
 * 斗牛游戏大厅查询
 * @author Administrator
 *
 * @param <U>
 */
public interface DouniuRoomHistoryListHandler <U> extends MessageHandler<DouniuRoomHistoryList, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(DouniuRoomHistoryList msg, U user);
}
