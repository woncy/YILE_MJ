package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.TbPkRoomUserDO;

import org.springframework.stereotype.Component;

@Component
public class TbPkRoomUserDao extends BaseDaoImpi<TbPkRoomUserDO,TbPkRoomUserDO.Key>  {
	public TbPkRoomUserDao() {
		super(TbPkRoomUserDO.TABLE_INFO);
	}

	public TbPkRoomUserDO get(int id) {
		return get(new TbPkRoomUserDO.Key(id));
	}
}
