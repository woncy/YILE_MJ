package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.MjRoleNodeDO;

@Component
public class MjRoleNodeDao extends BaseDaoImpi<MjRoleNodeDO,MjRoleNodeDO.Key>  {
	public MjRoleNodeDao() {
		super(MjRoleNodeDO.TABLE_INFO);
	}

	public MjRoleNodeDO get(int id) {
		return get(new MjRoleNodeDO.Key(id));
	}
}
