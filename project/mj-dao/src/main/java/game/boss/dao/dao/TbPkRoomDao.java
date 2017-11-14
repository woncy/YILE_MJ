package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.TbPkRoomDO;

@Component
public class TbPkRoomDao extends BaseDaoImpi<TbPkRoomDO,TbPkRoomDO.Key>  {
	public TbPkRoomDao() {
		super(TbPkRoomDO.TABLE_INFO);
	}

	public TbPkRoomDO get(int id) {
		return get(new TbPkRoomDO.Key(id));
	}
}
