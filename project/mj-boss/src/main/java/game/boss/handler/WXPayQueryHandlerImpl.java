package game.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import game.boss.model.User;
import game.boss.services.UserService;
import mj.net.handler.login.WXPayHandler;
import mj.net.handler.login.WXPayQueryHandler;
import mj.net.message.login.WXPay;
import mj.net.message.login.WXPayQuery;


/**
    * @ClassName: WXPayHandlerImpl
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
 * @param <U>
    * @date 2017年6月12日
    *
    */
@Component
public class WXPayQueryHandlerImpl implements WXPayQueryHandler<User>{

	@Autowired
	private UserService userService;
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param msg
	    * @param user
	    * @return
	    * @see mj.net.handler.login.WXPayHandler#handler(mj.net.message.login.WXPay, java.lang.Object)
	    */

	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param msg
	    * @param user
	    * @return
	    * @see mj.net.handler.login.WXPayHandler#handler(mj.net.message.login.WXPay, java.lang.Object)
	    */
	    
	@Override
	public boolean handler(WXPayQuery msg, User user) {
		userService.wxPayQuery(msg, user);
		return false;
	}
	    


}

