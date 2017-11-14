package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.AdminDO;

@Component
public class AdminDao extends BaseDaoImpi<AdminDO,AdminDO.Key>  {
	public AdminDao() {
		super(AdminDO.TABLE_INFO);
	}

	public AdminDO get(int id) {
		return get(new AdminDO.Key(id));
	}
}
