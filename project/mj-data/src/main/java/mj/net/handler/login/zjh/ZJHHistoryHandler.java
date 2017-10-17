package mj.net.handler.login.zjh;

import mj.net.handler.MessageHandler;
import mj.net.message.game.zjh.ZJHHistory;

public interface ZJHHistoryHandler <U> extends MessageHandler<ZJHHistory, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(ZJHHistory msg, U user);

}
