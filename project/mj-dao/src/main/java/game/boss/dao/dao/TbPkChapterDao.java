package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.TbPkChapterDO;

@Component
public class TbPkChapterDao extends BaseDaoImpi<TbPkChapterDO,TbPkChapterDO.Key>  {
	public TbPkChapterDao() {
		super(TbPkChapterDO.TABLE_INFO);
	}

	public TbPkChapterDO get(int id) {
		return get(new TbPkChapterDO.Key(id));
	}
}
