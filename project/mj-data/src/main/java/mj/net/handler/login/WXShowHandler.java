package mj.net.handler.login;

import mj.net.handler.MessageHandler;
import mj.net.message.login.WXPayQuery;
import mj.net.message.login.WXShow;

/**
    * @ClassName: WXShowHandler
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
 * @param <U>
    * @date 2017年6月19日
    *
    */

public interface WXShowHandler<U> extends MessageHandler<WXShow, U>{

	@Override
	public boolean handler(WXShow msg, U user);

}

