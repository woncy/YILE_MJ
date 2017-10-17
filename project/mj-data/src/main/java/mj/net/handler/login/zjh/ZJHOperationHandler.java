package mj.net.handler.login.zjh;

import mj.net.handler.MessageHandler;
import mj.net.message.game.zjh.ZJHOperation;

public interface ZJHOperationHandler  <U> extends MessageHandler<ZJHOperation, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(ZJHOperation msg, U user);


}
