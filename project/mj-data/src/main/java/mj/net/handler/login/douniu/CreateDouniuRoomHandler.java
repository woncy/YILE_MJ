package mj.net.handler.login.douniu;

import mj.net.handler.MessageHandler;
import mj.net.message.login.douniu.CreateDouniuRoom;

public interface CreateDouniuRoomHandler  <U> extends MessageHandler<CreateDouniuRoom, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(CreateDouniuRoom msg, U user);
}
