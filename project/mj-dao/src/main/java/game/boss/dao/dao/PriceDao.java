package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.PriceDO;

@Component
public class PriceDao extends BaseDaoImpi<PriceDO,PriceDO.Key>  {
	public PriceDao() {
		super(PriceDO.TABLE_INFO);
	}

	public PriceDO get(int id) {
		return get(new PriceDO.Key(id));
	}
}
