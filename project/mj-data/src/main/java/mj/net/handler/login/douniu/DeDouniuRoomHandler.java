package mj.net.handler.login.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.login.douniu.DeDouniuRoom;

public interface DeDouniuRoomHandler <U> extends MessageHandler<DeDouniuRoom, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(DeDouniuRoom msg, U user);
}