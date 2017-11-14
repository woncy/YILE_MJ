package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.SettingDO;

@Component
public class SettingDao extends BaseDaoImpi<SettingDO,SettingDO.Key>  {
	public SettingDao() {
		super(SettingDO.TABLE_INFO);
	}

	public SettingDO get(int id) {
		return get(new SettingDO.Key(id));
	}
}
