package mj.net.handler.login.zjh;

import mj.net.handler.MessageHandler;
import mj.net.message.login.zjh.SelectEveryHistory;

public interface SelectEveryHistoryHandler  <U> extends MessageHandler<SelectEveryHistory, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(SelectEveryHistory msg, U user);

}
