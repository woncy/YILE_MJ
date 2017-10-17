package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.MjRoleAdminDO;

import org.springframework.stereotype.Component;

@Component
public class MjRoleAdminDao extends BaseDaoImpi<MjRoleAdminDO,MjRoleAdminDO.Key>  {
	public MjRoleAdminDao() {
		super(MjRoleAdminDO.TABLE_INFO);
	}

	public MjRoleAdminDO get(int id) {
		return get(new MjRoleAdminDO.Key(id));
	}
}
