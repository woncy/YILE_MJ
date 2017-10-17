package mj.net.handler.login.zjh;

import mj.net.handler.MessageHandler;
import mj.net.message.login.zjh.GameReadySatrt;

public interface GameReadySatrtHandler <U> extends MessageHandler<GameReadySatrt, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(GameReadySatrt msg, U user);
}
