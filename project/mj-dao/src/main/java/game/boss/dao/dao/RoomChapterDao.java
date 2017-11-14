package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.RoomChapterDO;

@Component
public class RoomChapterDao extends BaseDaoImpi<RoomChapterDO,RoomChapterDO.Key>  {
	public RoomChapterDao() {
		super(RoomChapterDO.TABLE_INFO);
	}

	public RoomChapterDO get(int id) {
		return get(new RoomChapterDO.Key(id));
	}
}
