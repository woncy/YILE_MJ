package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.AgentApplyDO;

@Component
public class AgentApplyDao extends BaseDaoImpi<AgentApplyDO,AgentApplyDO.Key>  {
	public AgentApplyDao() {
		super(AgentApplyDO.TABLE_INFO);
	}

	public AgentApplyDO get(int id) {
		return get(new AgentApplyDO.Key(id));
	}
}
