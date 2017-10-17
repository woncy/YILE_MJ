package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.MjAdminDO;

import org.springframework.stereotype.Component;

@Component
public class MjAdminDao extends BaseDaoImpi<MjAdminDO,MjAdminDO.Key>  {
	public MjAdminDao() {
		super(MjAdminDO.TABLE_INFO);
	}

	public MjAdminDO get(int id) {
		return get(new MjAdminDO.Key(id));
	}
}
