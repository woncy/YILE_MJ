package game.boss.handler;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import game.boss.model.User;
import game.boss.services.UserService;
import mj.net.handler.login.WXPayHandler;
import mj.net.message.login.WXPay;


/**
    * @ClassName: WXPayHandlerImpl
    * @Description: 这里用一句话描述这个类的作用
    * @author 13323659953@163.com
 * @param <U>
    * @date 2017年6月12日
    *
    */
@Component
public class WXPayHandlerImpl implements WXPayHandler<User>{

	@Autowired
	private UserService userService;
	  
	    /**
	     * 购买房卡支付请求
	     */
	@Override
	public boolean handler(WXPay msg, User user) {
		userService.payGold(msg, user);
		return false;
	}
	    


}

