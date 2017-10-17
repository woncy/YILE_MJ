package mj.net.handler;

import com.isnowfox.core.net.message.Message;

import mj.net.message.login.WXPay;

public interface MessageHandler<T extends Message,U> {
	/**
	 * 返回值决定是否把这个对象脱离对象缓冲！
	 * 
	 * @param msg
	 * @return 返回true 表示需要脱离缓冲，不然这个消息的内容可能被覆盖
	 */

    boolean handler(T msg, U user);

	
	    /**
	    * @Title: handler
	    * @Description: 这里用一句话描述这个方法的作用
	    * @param @param msg
	    * @param @param user
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	    */
	    
	
    
}
