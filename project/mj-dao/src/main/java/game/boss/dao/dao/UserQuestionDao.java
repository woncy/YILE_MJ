package game.boss.dao.dao;

import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import org.springframework.stereotype.Component;

import game.boss.dao.entity.UserQuestionDO;

@Component
public class UserQuestionDao extends BaseDaoImpi<UserQuestionDO,UserQuestionDO.Key>  {
	public UserQuestionDao() {
		super(UserQuestionDO.TABLE_INFO);
	}

	public UserQuestionDO get(int id) {
		return get(new UserQuestionDO.Key(id));
	}
}
