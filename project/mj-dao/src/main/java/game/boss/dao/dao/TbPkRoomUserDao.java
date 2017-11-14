package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.TbPkRoomUserDO;

@Component
public class TbPkRoomUserDao extends BaseDaoImpi<TbPkRoomUserDO,TbPkRoomUserDO.Key>  {
	public TbPkRoomUserDao() {
		super(TbPkRoomUserDO.TABLE_INFO);
	}

	public TbPkRoomUserDO get(int id) {
		return get(new TbPkRoomUserDO.Key(id));
	}
}
