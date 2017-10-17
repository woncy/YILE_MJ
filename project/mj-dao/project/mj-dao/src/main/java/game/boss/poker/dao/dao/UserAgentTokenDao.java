package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.UserAgentTokenDO;

import org.springframework.stereotype.Component;

@Component
public class UserAgentTokenDao extends BaseDaoImpi<UserAgentTokenDO,UserAgentTokenDO.Key>  {
	public UserAgentTokenDao() {
		super(UserAgentTokenDO.TABLE_INFO);
	}

	public UserAgentTokenDO get(int id) {
		return get(new UserAgentTokenDO.Key(id));
	}
}
