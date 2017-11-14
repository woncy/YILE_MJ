package  game.boss.dao.entity;

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

public class UserPayLogDO extends EntityObject<UserPayLogDO, UserPayLogDO.Key>{

	/**自增id*/
	private int id;
	/**用户名*/
	private int userId;
	/**充值者id*/
	private int recharger;
	/**充值数量*/
	private int count;
	/**充值日期*/
	private java.util.Date date;

	public static class Key implements KeyObject<UserPayLogDO, UserPayLogDO.Key>{
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
			return "UserPayLog[id:"+ id+ "]";
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
				return "UserPayLog[id:"+ id+ "]";
			}
		};
	}




	public UserPayLogDO() {
    }

	public UserPayLogDO(int userId,int recharger,int count,java.util.Date date) {
		this.userId = userId;
		this.recharger = recharger;
		this.count = count;
		this.date = date;
	}


	public UserPayLogDO newInstance(){
		return new UserPayLogDO();
	}

    public void setKey(Object key){
        this.id = ((Number)key).intValue();
    }

	/**
	 * 自增id
	 **/
	public int getId() {
		return id;
	}

	/**
	 * 自增id
	 **/
	public void setId(int id) {
		this.id = id;
		changeProperty("id",id);
	}

	/**
	 * 用户名
	 **/
	public int getUserId() {
		return userId;
	}

	/**
	 * 用户名
	 **/
	public void setUserId(int userId) {
		this.userId = userId;
		changeProperty("userId",userId);
	}

	/**
	 * 充值者id
	 **/
	public int getRecharger() {
		return recharger;
	}

	/**
	 * 充值者id
	 **/
	public void setRecharger(int recharger) {
		this.recharger = recharger;
		changeProperty("recharger",recharger);
	}

	/**
	 * 充值数量
	 **/
	public int getCount() {
		return count;
	}

	/**
	 * 充值数量
	 **/
	public void setCount(int count) {
		this.count = count;
		changeProperty("count",count);
	}

	/**
	 * 充值日期
	 **/
	public java.util.Date getDate() {
		return date;
	}

	/**
	 * 充值日期
	 **/
	public void setDate(java.util.Date date) {
		this.date = date;
		changeProperty("date",date);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "user_id":
            return userId;
        case "recharger":
            return recharger;
        case "count":
            return count;
        case "date":
            return date;
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
		case "recharger":
			recharger = (int)obj;
			return true;
		case "count":
			count = (int)obj;
			return true;
		case "date":
			date = (java.util.Date)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "UserPayLog[id:"+ id+",userId:"+ userId+",recharger:"+ recharger+",count:"+ count+",date:"+ date+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<UserPayLogDO ,Key>{
		public static final String DB_TABLE_NAME = "user_pay_log";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String USER_ID = "user_id";
		public static final String RECHARGER = "recharger";
		public static final String COUNT = "count";
		public static final String DATE = "date";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("user_id", "userId", int.class, new TypeReference<Integer>() {});
			super.initProperty("recharger", "recharger", int.class, new TypeReference<Integer>() {});
			super.initProperty("count", "count", int.class, new TypeReference<Integer>() {});
			super.initProperty("date", "date", java.util.Date.class, new TypeReference<java.util.Date>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `user_pay_log` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `user_pay_log` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(UserPayLogDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object userIdPtr;
			userIdPtr = t.getUserId();

			ps.setObject(i++, userIdPtr);
			Object rechargerPtr;
			rechargerPtr = t.getRecharger();

			ps.setObject(i++, rechargerPtr);
			Object countPtr;
			countPtr = t.getCount();

			ps.setObject(i++, countPtr);
			Object datePtr;
			datePtr = t.getDate();

			ps.setObject(i++, datePtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(UserPayLogDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object userIdPtr;
				userIdPtr = t.getUserId();

			ps.setObject(i++,  userIdPtr);
			Object rechargerPtr;
				rechargerPtr = t.getRecharger();

			ps.setObject(i++,  rechargerPtr);
			Object countPtr;
				countPtr = t.getCount();

			ps.setObject(i++,  countPtr);
			Object datePtr;
				datePtr = t.getDate();

			ps.setObject(i++,  datePtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(UserPayLogDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `user_pay_log` (`user_id`,`recharger`,`count`,`date`) VALUES (?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `user_pay_log` (`id`,`user_id`,`recharger`,`count`,`date`) VALUES (?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `user_pay_log` (`user_id`,`recharger`,`count`,`date`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `user_pay_log` SET `user_id`=?,`recharger`=?,`count`=?,`date`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `user_pay_log` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `user_pay_log`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `user_pay_log` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `user_pay_log` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `user_pay_log` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<UserPayLogDO> getRowMapper(){
			return new RowMapper<UserPayLogDO>() {
				@Override
				public UserPayLogDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserPayLogDO o=new UserPayLogDO();
					o.id = rs.getInt("id");
					o.userId = rs.getInt("user_id");
					o.recharger = rs.getInt("recharger");
					o.count = rs.getInt("count");
					o.date = rs.getTimestamp("date");
					return o;
				}
			};
		}

		@Override public <C extends UserPayLogDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setUserId(rs.getInt("user_id"));
						o.setRecharger(rs.getInt("recharger"));
						o.setCount(rs.getInt("count"));
						o.setDate(rs.getTimestamp("date"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
