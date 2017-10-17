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

public class OnlinePayLogDO extends EntityObject<OnlinePayLogDO, OnlinePayLogDO.Key>{

	/**用户id*/
	private int userId;
	private String orderId;
	private String platId;
	private int gold;
	private int count;
	private java.sql.Date date;
	private int status;
	private byte type;

	public static class Key implements KeyObject<OnlinePayLogDO, OnlinePayLogDO.Key>{
    	private String orderId;

		public Key() {
   		}

		public Key(String orderId) {
			this.orderId = orderId;
		}

		@JsonIgnore
		@Transient
		@Override
		public Object[] getQueryParams() {
			return new Object[]{
				getOrderId()
			};
		}

		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

        @Override
        public String toStringKey(){
            return String.valueOf(getOrderId());

        }

		@Override
		public Table getTableInfo() {
			return TABLE_INFO;
		}

		@Override
		public String toString() {
			return "OnlinePayLog[orderId:"+ (orderId == null ?"null":orderId.substring(0, Math.min(orderId.length(), 64)))+ "]";
		}
	}

	@Override
	public Key getKey() {
		return new Key() {

			public String getOrderId() {
				return orderId;
			}

			public void setOrderId(String orderId) {
				OnlinePayLogDO.this.orderId  = orderId;
				OnlinePayLogDO.this.changeProperty("orderId",orderId);
			}

			@Override
			public String toString() {
				return "OnlinePayLog[orderId:"+ (orderId == null ?"null":orderId.substring(0, Math.min(orderId.length(), 64)))+ "]";
			}
		};
	}




	public OnlinePayLogDO() {
    }

	public OnlinePayLogDO(int userId,String orderId,String platId,int gold,int count,java.sql.Date date,int status,byte type) {
		this.userId = userId;
		this.orderId = orderId;
		this.platId = platId;
		this.gold = gold;
		this.count = count;
		this.date = date;
		this.status = status;
		this.type = type;
	}


	public OnlinePayLogDO newInstance(){
		return new OnlinePayLogDO();
	}

    public void setKey(Object key){
        this.orderId = (String)key;
    }

	/**
	 * 用户id
	 **/
	public int getUserId() {
		return userId;
	}

	/**
	 * 用户id
	 **/
	public void setUserId(int userId) {
		this.userId = userId;
		changeProperty("userId",userId);
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
		changeProperty("orderId",orderId);
	}

	public String getPlatId() {
		return platId;
	}

	public void setPlatId(String platId) {
		this.platId = platId;
		changeProperty("platId",platId);
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
		changeProperty("gold",gold);
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
		changeProperty("count",count);
	}

	public java.sql.Date getDate() {
		return date;
	}

	public void setDate(java.sql.Date date) {
		this.date = date;
		changeProperty("date",date);
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		changeProperty("status",status);
	}

	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
		changeProperty("type",type);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "user_id":
            return userId;
        case "order_id":
            return orderId;
        case "plat_id":
            return platId;
        case "gold":
            return gold;
        case "count":
            return count;
        case "date":
            return date;
        case "status":
            return status;
        case "type":
            return type;
        default :
            return null;
        }
    }


	@Override
	public boolean set(String dbName, Object obj) {
		switch(dbName){
		case "user_id":
			userId = (int)obj;
			return true;
		case "order_id":
			orderId = (String)obj;
			return true;
		case "plat_id":
			platId = (String)obj;
			return true;
		case "gold":
			gold = (int)obj;
			return true;
		case "count":
			count = (int)obj;
			return true;
		case "date":
			date = (java.sql.Date)obj;
			return true;
		case "status":
			status = (int)obj;
			return true;
		case "type":
			type = (byte)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "OnlinePayLog[userId:"+ userId+",orderId:"+ (orderId == null ?"null":orderId.substring(0, Math.min(orderId.length(), 64)))+",platId:"+ (platId == null ?"null":platId.substring(0, Math.min(platId.length(), 64)))+",gold:"+ gold+",count:"+ count+",date:"+ date+",status:"+ status+",type:"+ type+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<OnlinePayLogDO ,Key>{
		public static final String DB_TABLE_NAME = "online_pay_log";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String USER_ID = "user_id";
		public static final String ORDER_ID = "order_id";
		public static final String PLAT_ID = "plat_id";
		public static final String GOLD = "gold";
		public static final String COUNT = "count";
		public static final String DATE = "date";
		public static final String STATUS = "status";
		public static final String TYPE = "type";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("user_id", "userId", int.class, new TypeReference<Integer>() {});
			super.initProperty("order_id", "orderId", String.class, new TypeReference<String>() {});
			super.initProperty("plat_id", "platId", String.class, new TypeReference<String>() {});
			super.initProperty("gold", "gold", int.class, new TypeReference<Integer>() {});
			super.initProperty("count", "count", int.class, new TypeReference<Integer>() {});
			super.initProperty("date", "date", java.sql.Date.class, new TypeReference<java.sql.Date>() {});
			super.initProperty("status", "status", int.class, new TypeReference<Integer>() {});
			super.initProperty("type", "type", byte.class, new TypeReference<Byte>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `online_pay_log` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `order_id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `online_pay_log` WHERE `order_id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(OnlinePayLogDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object userIdPtr;
			userIdPtr = t.getUserId();

			ps.setObject(i++, userIdPtr);
			Object orderIdPtr;
			orderIdPtr = t.getOrderId();

			if(isSetUnique){
				ps.setObject(i++, orderIdPtr);
			}
			Object platIdPtr;
			platIdPtr = t.getPlatId();

			ps.setObject(i++, platIdPtr);
			Object goldPtr;
			goldPtr = t.getGold();

			ps.setObject(i++, goldPtr);
			Object countPtr;
			countPtr = t.getCount();

			ps.setObject(i++, countPtr);
			Object datePtr;
			datePtr = t.getDate();

			ps.setObject(i++, datePtr);
			Object statusPtr;
			statusPtr = t.getStatus();

			ps.setObject(i++, statusPtr);
			Object typePtr;
			typePtr = t.getType();

			ps.setObject(i++, typePtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(OnlinePayLogDO t, PreparedStatement ps, int i) throws SQLException {
			Object userIdPtr;
				userIdPtr = t.getUserId();

			ps.setObject(i++,  userIdPtr);
			Object orderIdPtr;
				orderIdPtr = t.getOrderId();

			ps.setObject(i++,  orderIdPtr);
			Object platIdPtr;
				platIdPtr = t.getPlatId();

			ps.setObject(i++,  platIdPtr);
			Object goldPtr;
				goldPtr = t.getGold();

			ps.setObject(i++,  goldPtr);
			Object countPtr;
				countPtr = t.getCount();

			ps.setObject(i++,  countPtr);
			Object datePtr;
				datePtr = t.getDate();

			ps.setObject(i++,  datePtr);
			Object statusPtr;
				statusPtr = t.getStatus();

			ps.setObject(i++,  statusPtr);
			Object typePtr;
				typePtr = t.getType();

			ps.setObject(i++,  typePtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(OnlinePayLogDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getOrderId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getOrderId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `online_pay_log` (`user_id`,`order_id`,`plat_id`,`gold`,`count`,`date`,`status`,`type`) VALUES (?,?,?,?,?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `online_pay_log` (`user_id`,`order_id`,`plat_id`,`gold`,`count`,`date`,`status`,`type`) VALUES (?,?,?,?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `online_pay_log` (`user_id`,`order_id`,`plat_id`,`gold`,`count`,`date`,`status`,`type`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?,?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `online_pay_log` SET `user_id`=?,`order_id`=?,`plat_id`=?,`gold`=?,`count`=?,`date`=?,`status`=?,`type`=? WHERE `order_id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `online_pay_log` WHERE `order_id`=? ORDER BY `order_id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `online_pay_log`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `online_pay_log` ORDER BY `order_id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `online_pay_log` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `online_pay_log` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `order_id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<OnlinePayLogDO> getRowMapper(){
			return new RowMapper<OnlinePayLogDO>() {
				@Override
				public OnlinePayLogDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					OnlinePayLogDO o=new OnlinePayLogDO();
					o.userId = rs.getInt("user_id");
					o.orderId = rs.getString("order_id");
					o.platId = rs.getString("plat_id");
					o.gold = rs.getInt("gold");
					o.count = rs.getInt("count");
					o.date = rs.getDate("date");
					o.status = rs.getInt("status");
					o.type = rs.getByte("type");
					return o;
				}
			};
		}

		@Override public <C extends OnlinePayLogDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setUserId(rs.getInt("user_id"));
						o.setOrderId(rs.getString("order_id"));
						o.setPlatId(rs.getString("plat_id"));
						o.setGold(rs.getInt("gold"));
						o.setCount(rs.getInt("count"));
						o.setDate(rs.getDate("date"));
						o.setStatus(rs.getInt("status"));
						o.setType(rs.getByte("type"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
