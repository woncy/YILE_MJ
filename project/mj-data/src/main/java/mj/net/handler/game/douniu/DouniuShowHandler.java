package mj.net.handler.game.douniu;

import mj.net.message.game.douniu.DouniuShow;
import mj.net.handler.MessageHandler;

/**
 *  牌的类型处理器接口 
 * 消息处理器接口
 * 添加属性回被spring注入！默认注入为类型注入！
 * <b>生成器生成代码!</b>
 * @author isnowfox消息生成器
 */
public interface DouniuShowHandler <U> extends MessageHandler<DouniuShow, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(DouniuShow msg, U user);
}