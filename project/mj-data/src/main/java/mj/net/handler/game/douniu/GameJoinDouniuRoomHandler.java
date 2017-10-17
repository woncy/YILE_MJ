package mj.net.handler.game.douniu;

import mj.net.message.game.douniu.GameJoinDouniuRoom;
import mj.net.handler.MessageHandler;

/**
 * 准备就绪,通知服务器可以开始发送房间信息了
 * 
 * 消息处理器接口
 * 添加属性回被spring注入！默认注入为类型注入！
 * <b>生成器生成代码!</b>
 * @author isnowfox消息生成器
 */
public interface GameJoinDouniuRoomHandler <U> extends MessageHandler<GameJoinDouniuRoom, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(GameJoinDouniuRoom msg, U user);
}