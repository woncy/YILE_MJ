package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.UserAgentTokenDO;

@Component
public class UserAgentTokenDao extends BaseDaoImpi<UserAgentTokenDO,UserAgentTokenDO.Key>  {
	public UserAgentTokenDao() {
		super(UserAgentTokenDO.TABLE_INFO);
	}

	public UserAgentTokenDO get(int id) {
		return get(new UserAgentTokenDO.Key(id));
	}
}
