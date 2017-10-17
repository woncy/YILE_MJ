package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.RoomDetailDO;

import org.springframework.stereotype.Component;

@Component
public class RoomDetailDao extends BaseDaoImpi<RoomDetailDO,RoomDetailDO.Key>  {
	public RoomDetailDao() {
		super(RoomDetailDO.TABLE_INFO);
	}

	public RoomDetailDO get(int id) {
		return get(new RoomDetailDO.Key(id));
	}
}
