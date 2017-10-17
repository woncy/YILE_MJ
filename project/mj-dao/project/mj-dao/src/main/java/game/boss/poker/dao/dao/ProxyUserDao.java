package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.ProxyUserDO;

import org.springframework.stereotype.Component;

@Component
public class ProxyUserDao extends BaseDaoImpi<ProxyUserDO,ProxyUserDO.Key>  {
	public ProxyUserDao() {
		super(ProxyUserDO.TABLE_INFO);
	}

	public ProxyUserDO get(int id) {
		return get(new ProxyUserDO.Key(id));
	}
}
