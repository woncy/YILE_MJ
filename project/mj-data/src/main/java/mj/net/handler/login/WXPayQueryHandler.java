package mj.net.handler.login;


import mj.net.handler.MessageHandler;
import mj.net.message.login.WXPayQuery;

/**
    * @ClassName: WXPayQueryHandler
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
 * @param <U>
    * @date 2017年6月14日
    *
    */

public interface WXPayQueryHandler<U> extends MessageHandler<WXPayQuery, U> {
	@Override
	public boolean handler(WXPayQuery msg, U user);
}

