package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.ProxyUserDO;

@Component
public class ProxyUserDao extends BaseDaoImpi<ProxyUserDO,ProxyUserDO.Key>  {
	public ProxyUserDao() {
		super(ProxyUserDO.TABLE_INFO);
	}

	public ProxyUserDO get(int id) {
		return get(new ProxyUserDO.Key(id));
	}
}
