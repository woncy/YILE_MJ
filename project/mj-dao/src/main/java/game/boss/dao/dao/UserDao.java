package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.UserDO;

@Component
public class UserDao extends BaseDaoImpi<UserDO,UserDO.Key>  {
	public UserDao() {
		super(UserDO.TABLE_INFO);
	}

	public UserDO get(int id) {
		return get(new UserDO.Key(id));
	}
}
