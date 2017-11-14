package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.MjAdminDO;

@Component
public class MjAdminDao extends BaseDaoImpi<MjAdminDO,MjAdminDO.Key>  {
	public MjAdminDao() {
		super(MjAdminDO.TABLE_INFO);
	}

	public MjAdminDO get(int id) {
		return get(new MjAdminDO.Key(id));
	}
}
