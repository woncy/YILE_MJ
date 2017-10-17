package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.RoomUserDO;

import org.springframework.stereotype.Component;

@Component
public class RoomUserDao extends BaseDaoImpi<RoomUserDO,RoomUserDO.Key>  {
	public RoomUserDao() {
		super(RoomUserDO.TABLE_INFO);
	}

	public RoomUserDO get(int userId) {
		return get(new RoomUserDO.Key(userId));
	}
}
