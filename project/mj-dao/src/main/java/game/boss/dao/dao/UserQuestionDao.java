package game.boss.dao.dao;
import  org.forkjoin.core.dao.impi.BaseDaoImpi;
import game.boss.dao.entity.UserQuestionDO;

import org.springframework.stereotype.Component;

@Component
public class UserQuestionDao extends BaseDaoImpi<UserQuestionDO,UserQuestionDO.Key>  {
	public UserQuestionDao() {
		super(UserQuestionDO.TABLE_INFO);
	}

	public UserQuestionDO get(int id) {
		return get(new UserQuestionDO.Key(id));
	}
}
