package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.UserPayLogDO;

@Component
public class UserPayLogDao extends BaseDaoImpi<UserPayLogDO,UserPayLogDO.Key>  {
	public UserPayLogDao() {
		super(UserPayLogDO.TABLE_INFO);
	}

	public UserPayLogDO get(int id) {
		return get(new UserPayLogDO.Key(id));
	}
}
