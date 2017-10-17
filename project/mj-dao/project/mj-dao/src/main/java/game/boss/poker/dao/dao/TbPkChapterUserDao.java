package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.TbPkChapterUserDO;

import org.springframework.stereotype.Component;

@Component
public class TbPkChapterUserDao extends BaseDaoImpi<TbPkChapterUserDO,TbPkChapterUserDO.Key>  {
	public TbPkChapterUserDao() {
		super(TbPkChapterUserDO.TABLE_INFO);
	}

	public TbPkChapterUserDO get(int id) {
		return get(new TbPkChapterUserDO.Key(id));
	}
}
