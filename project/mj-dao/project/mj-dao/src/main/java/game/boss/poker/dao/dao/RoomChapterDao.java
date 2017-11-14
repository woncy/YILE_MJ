package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.RoomChapterDO;

import org.springframework.stereotype.Component;

@Component
public class RoomChapterDao extends BaseDaoImpi<RoomChapterDO,RoomChapterDO.Key>  {
	public RoomChapterDao() {
		super(RoomChapterDO.TABLE_INFO);
	}

	public RoomChapterDO get(int id) {
		return get(new RoomChapterDO.Key(id));
	}
}
