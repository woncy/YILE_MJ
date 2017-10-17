package mj.net.handler.game.douniu;

import mj.net.message.game.douniu.DouniuBiCard;
import mj.net.handler.MessageHandler;

/**
 * 发送消息给客户端，和指定玩家比牌
 * 
 * 消息处理器接口
 * 添加属性回被spring注入！默认注入为类型注入！
 * <b>生成器生成代码!</b>
 * @author isnowfox消息生成器
 */
public interface DouniuBiCardHandler <U> extends MessageHandler<DouniuBiCard, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(DouniuBiCard msg, U user);
}