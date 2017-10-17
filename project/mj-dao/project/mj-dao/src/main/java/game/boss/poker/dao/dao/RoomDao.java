package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.RoomDO;

import org.springframework.stereotype.Component;

@Component
public class RoomDao extends BaseDaoImpi<RoomDO,RoomDO.Key>  {
	public RoomDao() {
		super(RoomDO.TABLE_INFO);
	}

	public RoomDO get(int id) {
		return get(new RoomDO.Key(id));
	}
}
