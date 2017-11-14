package game.boss.poker.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.poker.dao.entity.Room2DO;

import org.springframework.stereotype.Component;

@Component
public class Room2Dao extends BaseDaoImpi<Room2DO,Room2DO.Key>  {
	public Room2Dao() {
		super(Room2DO.TABLE_INFO);
	}

	public Room2DO get(int id) {
		return get(new Room2DO.Key(id));
	}
}
