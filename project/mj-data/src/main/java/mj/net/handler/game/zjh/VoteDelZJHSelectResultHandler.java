package mj.net.handler.game.zjh;

import mj.net.handler.MessageHandler;
import mj.net.message.game.VoteDelSelectRet;
import mj.net.message.game.zjh.VoteDelZJHSelectResult;

public interface VoteDelZJHSelectResultHandler<U> extends MessageHandler<VoteDelZJHSelectResult, U> {
    /**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(VoteDelZJHSelectResult msg, U user);

}
