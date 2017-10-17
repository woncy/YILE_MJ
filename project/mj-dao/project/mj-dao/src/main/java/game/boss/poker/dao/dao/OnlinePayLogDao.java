package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.OnlinePayLogDO;

import org.springframework.stereotype.Component;

@Component
public class OnlinePayLogDao extends BaseDaoImpi<OnlinePayLogDO,OnlinePayLogDO.Key>  {
	public OnlinePayLogDao() {
		super(OnlinePayLogDO.TABLE_INFO);
	}

	public OnlinePayLogDO get(String orderId) {
		return get(new OnlinePayLogDO.Key(orderId));
	}
}
