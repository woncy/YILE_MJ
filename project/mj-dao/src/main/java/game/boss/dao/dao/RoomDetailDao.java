package game.boss.dao.dao;

import java.util.List;
import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.dao.entity.RoomDetailDO;
import org.springframework.stereotype.Component;


@Component
public class RoomDetailDao extends BaseDaoImpi<RoomDetailDO,RoomDetailDO.Key>  {
	public RoomDetailDao() {
		super(RoomDetailDO.TABLE_INFO);
	}

	public RoomDetailDO get(int id) {
		return get(new RoomDetailDO.Key(id));
	}
	
	public List<RoomDetailDO>  getByRoomCheckId(String room_check_id,String chapterIndex){
		return find(20, RoomDetailDO.Table.CHECK_ROOM_ID, room_check_id,
						RoomDetailDO.Table.CHEPTER_INDEX, chapterIndex);
	}
	
	/*
	 *根据回放号查询回放记录 
	 */
	public List<RoomDetailDO>  getByRoomCode(String playBackCode){
		return find(20, RoomDetailDO.Table.PLAY_BACK_CODE, playBackCode);
	}
	
}
