package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.RoomUserDO;

@Component
public class RoomUserDao extends BaseDaoImpi<RoomUserDO,RoomUserDO.Key>  {
	public RoomUserDao() {
		super(RoomUserDO.TABLE_INFO);
	}

	public RoomUserDO get(int userId) {
		return get(new RoomUserDO.Key(userId));
	}
}
