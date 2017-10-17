package  game.boss.poker.dao.entity;

import java.beans.Transient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


import org.forkjoin.core.dao.UniqueInfo;


import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;

import  org.forkjoin.core.dao.EntityObject;
import  org.forkjoin.core.dao.KeyObject;
import  org.forkjoin.core.dao.TableInfo;

public class UserQuestionDO extends EntityObject<UserQuestionDO, UserQuestionDO.Key>{

	private int id;
	private int userId;
	private String question;
	private java.util.Date time;

	public static class Key implements KeyObject<UserQuestionDO, UserQuestionDO.Key>{
    	private int id;

		public Key() {
   		}

		public Key(int id) {
			this.id = id;
		}

		@JsonIgnore
		@Transient
		@Override
		public Object[] getQueryParams() {
			return new Object[]{
				getId()
			};
		}

		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}

        @Override
        public String toStringKey(){
            return String.valueOf(getId());

        }

		@Override
		public Table getTableInfo() {
			return TABLE_INFO;
		}

		@Override
		public String toString() {
			return "UserQuestion[id:"+ id+ "]";
		}
	}

	@Override
	public Key getKey() {
		return new Key() {

			public int getId() {
				return id;
			}

			@Override
			public String toString() {
				return "UserQuestion[id:"+ id+ "]";
			}
		};
	}




	public UserQuestionDO() {
    }

	public UserQuestionDO(int userId,String question,java.util.Date time) {
		this.userId = userId;
		this.question = question;
		this.time = time;
	}


	public UserQuestionDO newInstance(){
		return new UserQuestionDO();
	}

    public void setKey(Object key){
        this.id = ((Number)key).intValue();
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		changeProperty("id",id);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
		changeProperty("userId",userId);
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
		changeProperty("question",question);
	}

	public java.util.Date getTime() {
		return time;
	}

	public void setTime(java.util.Date time) {
		this.time = time;
		changeProperty("time",time);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "user_id":
            return userId;
        case "question":
            return question;
        case "time":
            return time;
        default :
            return null;
        }
    }


	@Override
	public boolean set(String dbName, Object obj) {
		switch(dbName){
		case "id":
			id = (int)obj;
			return true;
		case "user_id":
			userId = (int)obj;
			return true;
		case "question":
			question = (String)obj;
			return true;
		case "time":
			time = (java.util.Date)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "UserQuestion[id:"+ id+",userId:"+ userId+",question:"+ (question == null ?"null":question.substring(0, Math.min(question.length(), 64)))+",time:"+ time+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<UserQuestionDO ,Key>{
		public static final String DB_TABLE_NAME = "user_question";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String USER_ID = "user_id";
		public static final String QUESTION = "question";
		public static final String TIME = "time";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("user_id", "userId", int.class, new TypeReference<Integer>() {});
			super.initProperty("question", "question", String.class, new TypeReference<String>() {});
			super.initProperty("time", "time", java.util.Date.class, new TypeReference<java.util.Date>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `user_question` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `user_question` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(UserQuestionDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object userIdPtr;
			userIdPtr = t.getUserId();

			ps.setObject(i++, userIdPtr);
			Object questionPtr;
			questionPtr = t.getQuestion();

			ps.setObject(i++, questionPtr);
			Object timePtr;
			timePtr = t.getTime();

			ps.setObject(i++, timePtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(UserQuestionDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object userIdPtr;
				userIdPtr = t.getUserId();

			ps.setObject(i++,  userIdPtr);
			Object questionPtr;
				questionPtr = t.getQuestion();

			ps.setObject(i++,  questionPtr);
			Object timePtr;
				timePtr = t.getTime();

			ps.setObject(i++,  timePtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(UserQuestionDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `user_question` (`user_id`,`question`,`time`) VALUES (?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `user_question` (`id`,`user_id`,`question`,`time`) VALUES (?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `user_question` (`user_id`,`question`,`time`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `user_question` SET `user_id`=?,`question`=?,`time`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `user_question` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `user_question`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `user_question` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `user_question` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `user_question` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<UserQuestionDO> getRowMapper(){
			return new RowMapper<UserQuestionDO>() {
				@Override
				public UserQuestionDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserQuestionDO o=new UserQuestionDO();
					o.id = rs.getInt("id");
					o.userId = rs.getInt("user_id");
					o.question = rs.getString("question");
					o.time = rs.getTimestamp("time");
					return o;
				}
			};
		}

		@Override public <C extends UserQuestionDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setUserId(rs.getInt("user_id"));
						o.setQuestion(rs.getString("question"));
						o.setTime(rs.getTimestamp("time"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
