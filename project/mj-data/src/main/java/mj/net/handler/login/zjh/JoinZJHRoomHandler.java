package mj.net.handler.login.zjh;

import mj.net.handler.MessageHandler;
import mj.net.message.login.zjh.JoinZJHRoom;
/**
 * 加入房间
 * @author 13323659953@163.com
 *
 * @param <U>
 */
public interface JoinZJHRoomHandler <U> extends MessageHandler<JoinZJHRoom, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(JoinZJHRoom msg, U user);
}
