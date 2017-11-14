package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.TbPkUserChapterDO;

@Component
public class TbPkUserChapterDao extends BaseDaoImpi<TbPkUserChapterDO,TbPkUserChapterDO.Key>  {
	public TbPkUserChapterDao() {
		super(TbPkUserChapterDO.TABLE_INFO);
	}

	public TbPkUserChapterDO get(int id) {
		return get(new TbPkUserChapterDO.Key(id));
	}
}
