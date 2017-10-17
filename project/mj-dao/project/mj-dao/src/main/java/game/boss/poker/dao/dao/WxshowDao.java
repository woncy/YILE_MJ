package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.WxshowDO;

import org.springframework.stereotype.Component;

@Component
public class WxshowDao extends BaseDaoImpi<WxshowDO,WxshowDO.Key>  {
	public WxshowDao() {
		super(WxshowDO.TABLE_INFO);
	}

	public WxshowDO get(int id) {
		return get(new WxshowDO.Key(id));
	}
}
