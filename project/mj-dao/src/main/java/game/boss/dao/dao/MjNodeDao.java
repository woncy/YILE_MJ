package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.dao.entity.MjNodeDO;

import org.springframework.stereotype.Component;

@Component
public class MjNodeDao extends BaseDaoImpi<MjNodeDO,MjNodeDO.Key>  {
	public MjNodeDao() {
		super(MjNodeDO.TABLE_INFO);
	}

	public MjNodeDO get(int nodeId) {
		return get(new MjNodeDO.Key(nodeId));
	}
}
