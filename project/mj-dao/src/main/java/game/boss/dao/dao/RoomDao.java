package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.RoomDO;

@Component
public class RoomDao extends BaseDaoImpi<RoomDO,RoomDO.Key>  {
	public RoomDao() {
		super(RoomDO.TABLE_INFO);
	}

	public RoomDO get(int id) {
		return get(new RoomDO.Key(id));
	}
}
