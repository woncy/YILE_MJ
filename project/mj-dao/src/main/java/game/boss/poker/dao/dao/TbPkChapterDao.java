package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.TbPkChapterDO;

import org.springframework.stereotype.Component;

@Component
public class TbPkChapterDao extends BaseDaoImpi<TbPkChapterDO,TbPkChapterDO.Key>  {
	public TbPkChapterDao() {
		super(TbPkChapterDO.TABLE_INFO);
	}

	public TbPkChapterDO get(int id) {
		return get(new TbPkChapterDO.Key(id));
	}
}
