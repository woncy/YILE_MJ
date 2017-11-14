package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.WxshowDO;

@Component
public class WxshowDao extends BaseDaoImpi<WxshowDO,WxshowDO.Key>  {
	public WxshowDao() {
		super(WxshowDO.TABLE_INFO);
	}

	public WxshowDO get(int id) {
		return get(new WxshowDO.Key(id));
	}
}
