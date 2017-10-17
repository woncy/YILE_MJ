package mj.net.handler.login;

import mj.net.handler.MessageHandler;

import mj.net.message.login.WXPay;

	/**
    * @ClassName: WXPayHandler
    * @Description: 微信支付消息处理接口
    * @author 13323659953@163.com
    * @date 2017年6月12日
    *
    */

public interface WXPayHandler<U> extends MessageHandler<WXPay, U> {

	@Override
	public boolean handler(WXPay msg, U user);


	    
	

}

