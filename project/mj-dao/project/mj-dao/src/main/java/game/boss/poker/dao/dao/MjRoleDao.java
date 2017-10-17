package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.MjRoleDO;

import org.springframework.stereotype.Component;

@Component
public class MjRoleDao extends BaseDaoImpi<MjRoleDO,MjRoleDO.Key>  {
	public MjRoleDao() {
		super(MjRoleDO.TABLE_INFO);
	}

	public MjRoleDO get(int roleId) {
		return get(new MjRoleDO.Key(roleId));
	}
}
