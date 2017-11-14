package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.RoomDetailDO;

@Component
public class RoomDetailDao extends BaseDaoImpi<RoomDetailDO,RoomDetailDO.Key>  {
	public RoomDetailDao() {
		super(RoomDetailDO.TABLE_INFO);
	}

	public RoomDetailDO get(int id) {
		return get(new RoomDetailDO.Key(id));
	}
}
