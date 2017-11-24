package game.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import game.boss.model.User;
import game.boss.services.UserService;
import mj.net.handler.login.WXShowHandler;
import mj.net.message.login.WXPayQuery;
import mj.net.message.login.WXShow;

/**
    * @ClassName: WXShowHandlerImpl
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
    * @date 2017年6月19日
    *
    */

@Component
public class WXShowHandlerImpl implements WXShowHandler<User> {

	@Autowired
	UserService UserService;
	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param msg
	    * @param user
	    * @return
	    * @see mj.net.handler.login.WXShowHandler#handler(mj.net.message.login.WXPayQuery, java.lang.Object)
	    */
	    
	@Override
	public boolean handler(WXShow msg, User user) {
		UserService.wxshow(msg,user);
		return false;
	}

	
	    /* (非 Javadoc)
	    * 
	    * 
	    * @param msg
	    * @param user
	    * @return
	    * @see mj.net.handler.login.WXShowHandler#handler(mj.net.message.login.WXShow, java.lang.Object)
	    */
	    


}

