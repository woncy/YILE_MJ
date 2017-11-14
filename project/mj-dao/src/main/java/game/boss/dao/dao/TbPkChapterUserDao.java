package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.TbPkChapterUserDO;

@Component
public class TbPkChapterUserDao extends BaseDaoImpi<TbPkChapterUserDO,TbPkChapterUserDO.Key>  {
	public TbPkChapterUserDao() {
		super(TbPkChapterUserDO.TABLE_INFO);
	}

	public TbPkChapterUserDO get(int id) {
		return get(new TbPkChapterUserDO.Key(id));
	}
}
