package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.TbWinProDO;

@Component
public class TbWinProDao extends BaseDaoImpi<TbWinProDO,TbWinProDO.Key>  {
	public TbWinProDao() {
		super(TbWinProDO.TABLE_INFO);
	}

	public TbWinProDO get(int id) {
		return get(new TbWinProDO.Key(id));
	}
}
