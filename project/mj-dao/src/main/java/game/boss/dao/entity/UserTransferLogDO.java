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

public class UserTransferLogDO extends EntityObject<UserTransferLogDO, UserTransferLogDO.Key>{

	private int id;
	private Integer srcUserId;
	private Integer destUserId;
	private java.util.Date createTime;
	private Integer gold;

	public static class Key implements KeyObject<UserTransferLogDO, UserTransferLogDO.Key>{
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
			return "UserTransferLog[id:"+ id+ "]";
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
				return "UserTransferLog[id:"+ id+ "]";
			}
		};
	}




	public UserTransferLogDO() {
    }

	public UserTransferLogDO(Integer srcUserId,Integer destUserId,java.util.Date createTime,Integer gold) {
		this.srcUserId = srcUserId;
		this.destUserId = destUserId;
		this.createTime = createTime;
		this.gold = gold;
	}


	public UserTransferLogDO newInstance(){
		return new UserTransferLogDO();
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

	public Integer getSrcUserId() {
		return srcUserId;
	}

	public void setSrcUserId(Integer srcUserId) {
		this.srcUserId = srcUserId;
		changeProperty("srcUserId",srcUserId);
	}

	public Integer getDestUserId() {
		return destUserId;
	}

	public void setDestUserId(Integer destUserId) {
		this.destUserId = destUserId;
		changeProperty("destUserId",destUserId);
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
		changeProperty("createTime",createTime);
	}

	public Integer getGold() {
		return gold;
	}

	public void setGold(Integer gold) {
		this.gold = gold;
		changeProperty("gold",gold);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "src_user_id":
            return srcUserId;
        case "dest_user_id":
            return destUserId;
        case "create_time":
            return createTime;
        case "gold":
            return gold;
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
		case "src_user_id":
			srcUserId = (Integer)obj;
			return true;
		case "dest_user_id":
			destUserId = (Integer)obj;
			return true;
		case "create_time":
			createTime = (java.util.Date)obj;
			return true;
		case "gold":
			gold = (Integer)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "UserTransferLog[id:"+ id+",srcUserId:"+ srcUserId+",destUserId:"+ destUserId+",createTime:"+ createTime+",gold:"+ gold+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<UserTransferLogDO ,Key>{
		public static final String DB_TABLE_NAME = "user_transfer_log";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String SRC_USER_ID = "src_user_id";
		public static final String DEST_USER_ID = "dest_user_id";
		public static final String CREATE_TIME = "create_time";
		public static final String GOLD = "gold";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("src_user_id", "srcUserId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("dest_user_id", "destUserId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("create_time", "createTime", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("gold", "gold", Integer.class, new TypeReference<Integer>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `user_transfer_log` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `user_transfer_log` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(UserTransferLogDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object srcUserIdPtr;
			srcUserIdPtr = t.getSrcUserId();

			ps.setObject(i++, srcUserIdPtr);
			Object destUserIdPtr;
			destUserIdPtr = t.getDestUserId();

			ps.setObject(i++, destUserIdPtr);
			Object createTimePtr;
			createTimePtr = t.getCreateTime();

			ps.setObject(i++, createTimePtr);
			Object goldPtr;
			goldPtr = t.getGold();

			ps.setObject(i++, goldPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(UserTransferLogDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object srcUserIdPtr;
				srcUserIdPtr = t.getSrcUserId();

			ps.setObject(i++,  srcUserIdPtr);
			Object destUserIdPtr;
				destUserIdPtr = t.getDestUserId();

			ps.setObject(i++,  destUserIdPtr);
			Object createTimePtr;
				createTimePtr = t.getCreateTime();

			ps.setObject(i++,  createTimePtr);
			Object goldPtr;
				goldPtr = t.getGold();

			ps.setObject(i++,  goldPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(UserTransferLogDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `user_transfer_log` (`src_user_id`,`dest_user_id`,`create_time`,`gold`) VALUES (?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `user_transfer_log` (`id`,`src_user_id`,`dest_user_id`,`create_time`,`gold`) VALUES (?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `user_transfer_log` (`src_user_id`,`dest_user_id`,`create_time`,`gold`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `user_transfer_log` SET `src_user_id`=?,`dest_user_id`=?,`create_time`=?,`gold`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `user_transfer_log` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `user_transfer_log`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `user_transfer_log` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `user_transfer_log` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `user_transfer_log` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<UserTransferLogDO> getRowMapper(){
			return new RowMapper<UserTransferLogDO>() {
				@Override
				public UserTransferLogDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					UserTransferLogDO o=new UserTransferLogDO();
					o.id = rs.getInt("id");
					o.srcUserId = rs.getInt("src_user_id");
					o.destUserId = rs.getInt("dest_user_id");
					o.createTime = rs.getTimestamp("create_time");
					o.gold = rs.getInt("gold");
					return o;
				}
			};
		}

		@Override public <C extends UserTransferLogDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setSrcUserId(rs.getInt("src_user_id"));
						o.setDestUserId(rs.getInt("dest_user_id"));
						o.setCreateTime(rs.getTimestamp("create_time"));
						o.setGold(rs.getInt("gold"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
