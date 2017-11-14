package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.OnlinePayLogDO;

@Component
public class OnlinePayLogDao extends BaseDaoImpi<OnlinePayLogDO,OnlinePayLogDO.Key>  {
	public OnlinePayLogDao() {
		super(OnlinePayLogDO.TABLE_INFO);
	}

	public OnlinePayLogDO get(String orderId) {
		return get(new OnlinePayLogDO.Key(orderId));
	}
}
