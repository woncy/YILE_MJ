package game.boss.dao.dao;
import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.dao.entity.TbWinProDO;

import org.springframework.stereotype.Component;

@Component
public class TbWinProDao extends BaseDaoImpi<TbWinProDO,TbWinProDO.Key>  {
	public TbWinProDao() {
		super(TbWinProDO.TABLE_INFO);
	}

	public TbWinProDO get(int id) {
		return get(new TbWinProDO.Key(id));
	}
}
