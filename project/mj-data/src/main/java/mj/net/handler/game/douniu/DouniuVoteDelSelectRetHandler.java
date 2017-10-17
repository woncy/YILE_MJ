package mj.net.handler.game.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.game.douniu.DouniuVoteDelSelectRet;

public interface DouniuVoteDelSelectRetHandler <U> extends MessageHandler<DouniuVoteDelSelectRet, U> {
	
    /**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(DouniuVoteDelSelectRet msg, U user);
}