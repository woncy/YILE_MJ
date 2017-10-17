package mj.net.handler.login.zjh;

import mj.net.handler.MessageHandler;
import mj.net.message.game.zjh.ZJHGameSatrt;

public interface ZJHGameStartHandler  <U> extends MessageHandler<ZJHGameSatrt, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(ZJHGameSatrt msg, U user);


}
