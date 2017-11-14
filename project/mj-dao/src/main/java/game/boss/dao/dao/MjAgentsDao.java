package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.MjAgentsDO;

@Component
public class MjAgentsDao extends BaseDaoImpi<MjAgentsDO,MjAgentsDO.Key>  {
	public MjAgentsDao() {
		super(MjAgentsDO.TABLE_INFO);
	}

	public MjAgentsDO get(int id) {
		return get(new MjAgentsDO.Key(id));
	}
}
