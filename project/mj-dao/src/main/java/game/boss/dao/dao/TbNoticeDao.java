package game.boss.dao.dao;
import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.dao.entity.TbNoticeDO;

import org.springframework.stereotype.Component;

@Component
public class TbNoticeDao extends BaseDaoImpi<TbNoticeDO,TbNoticeDO.Key>  {
	public TbNoticeDao() {
		super(TbNoticeDO.TABLE_INFO);
	}

	public TbNoticeDO get(int id) {
		return get(new TbNoticeDO.Key(id));
	}
}
