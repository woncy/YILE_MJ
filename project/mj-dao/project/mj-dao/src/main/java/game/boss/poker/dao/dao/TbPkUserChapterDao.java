package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.TbPkUserChapterDO;

import org.springframework.stereotype.Component;

@Component
public class TbPkUserChapterDao extends BaseDaoImpi<TbPkUserChapterDO,TbPkUserChapterDO.Key>  {
	public TbPkUserChapterDao() {
		super(TbPkUserChapterDO.TABLE_INFO);
	}

	public TbPkUserChapterDO get(int id) {
		return get(new TbPkUserChapterDO.Key(id));
	}
}
