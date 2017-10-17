package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.UserPayLogDO;

import org.springframework.stereotype.Component;

@Component
public class UserPayLogDao extends BaseDaoImpi<UserPayLogDO,UserPayLogDO.Key>  {
	public UserPayLogDao() {
		super(UserPayLogDO.TABLE_INFO);
	}

	public UserPayLogDO get(int id) {
		return get(new UserPayLogDO.Key(id));
	}
}
