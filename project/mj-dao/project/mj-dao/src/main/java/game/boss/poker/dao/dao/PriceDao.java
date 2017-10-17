package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.PriceDO;

import org.springframework.stereotype.Component;

@Component
public class PriceDao extends BaseDaoImpi<PriceDO,PriceDO.Key>  {
	public PriceDao() {
		super(PriceDO.TABLE_INFO);
	}

	public PriceDO get(int id) {
		return get(new PriceDO.Key(id));
	}
}
