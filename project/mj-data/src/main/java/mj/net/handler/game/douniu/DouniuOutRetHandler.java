package mj.net.handler.game.douniu;

import mj.net.message.game.douniu.DouniuOutRet;
import mj.net.handler.MessageHandler;

/**
 * 出牌回复
 * 
 * 消息处理器接口
 * 添加属性回被spring注入！默认注入为类型注入！
 * <b>生成器生成代码!</b>
 * @author isnowfox消息生成器
 */
public interface DouniuOutRetHandler <U> extends MessageHandler<DouniuOutRet, U> {
	/**
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */
	@Override
    boolean handler(DouniuOutRet msg, U user);
}