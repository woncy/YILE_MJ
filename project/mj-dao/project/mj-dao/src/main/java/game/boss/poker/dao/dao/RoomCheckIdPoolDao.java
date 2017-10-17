package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.RoomCheckIdPoolDO;

import org.springframework.stereotype.Component;

@Component
public class RoomCheckIdPoolDao extends BaseDaoImpi<RoomCheckIdPoolDO,RoomCheckIdPoolDO.Key>  {
	public RoomCheckIdPoolDao() {
		super(RoomCheckIdPoolDO.TABLE_INFO);
	}

	public RoomCheckIdPoolDO get(String id) {
		return get(new RoomCheckIdPoolDO.Key(id));
	}
}
