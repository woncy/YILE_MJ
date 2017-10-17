package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.UserTransferLogDO;

import org.springframework.stereotype.Component;

@Component
public class UserTransferLogDao extends BaseDaoImpi<UserTransferLogDO,UserTransferLogDO.Key>  {
	public UserTransferLogDao() {
		super(UserTransferLogDO.TABLE_INFO);
	}

	public UserTransferLogDO get(int id) {
		return get(new UserTransferLogDO.Key(id));
	}
}
