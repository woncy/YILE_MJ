package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.UserLinkRoomDO;

@Component
public class UserLinkRoomDao extends BaseDaoImpi<UserLinkRoomDO,UserLinkRoomDO.Key>  {
	public UserLinkRoomDao() {
		super(UserLinkRoomDO.TABLE_INFO);
	}

	public UserLinkRoomDO get(int id) {
		return get(new UserLinkRoomDO.Key(id));
	}
}
