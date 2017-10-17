package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.TbPkRoomDO;

import org.springframework.stereotype.Component;

@Component
public class TbPkRoomDao extends BaseDaoImpi<TbPkRoomDO,TbPkRoomDO.Key>  {
	public TbPkRoomDao() {
		super(TbPkRoomDO.TABLE_INFO);
	}

	public TbPkRoomDO get(int id) {
		return get(new TbPkRoomDO.Key(id));
	}
}
