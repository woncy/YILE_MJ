package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.UserDO;

import org.springframework.stereotype.Component;

@Component
public class UserDao extends BaseDaoImpi<UserDO,UserDO.Key>  {
	public UserDao() {
		super(UserDO.TABLE_INFO);
	}

	public UserDO get(int id) {
		return get(new UserDO.Key(id));
	}
}
