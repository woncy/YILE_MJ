package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.MjRoleAdminDO;

@Component
public class MjRoleAdminDao extends BaseDaoImpi<MjRoleAdminDO,MjRoleAdminDO.Key>  {
	public MjRoleAdminDao() {
		super(MjRoleAdminDO.TABLE_INFO);
	}

	public MjRoleAdminDO get(int id) {
		return get(new MjRoleAdminDO.Key(id));
	}
}
