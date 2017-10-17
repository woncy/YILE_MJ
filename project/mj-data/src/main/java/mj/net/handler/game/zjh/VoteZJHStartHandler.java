package mj.net.handler.game.zjh;

import mj.net.handler.MessageHandler;
import mj.net.message.game.zjh.VoteStart;

public interface VoteZJHStartHandler  <U> extends MessageHandler<VoteStart, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(VoteStart msg, U user);
}
