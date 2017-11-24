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

public class WxshowDO extends EntityObject<WxshowDO, WxshowDO.Key>{

	private int id;
	private Integer userid;
	private java.util.Date time;

	public static class Key implements KeyObject<WxshowDO, WxshowDO.Key>{
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
			return "Wxshow[id:"+ id+ "]";
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
				return "Wxshow[id:"+ id+ "]";
			}
		};
	}




	public WxshowDO() {
    }

	public WxshowDO(Integer userid,java.util.Date time) {
		this.userid = userid;
		this.time = time;
	}


	public WxshowDO newInstance(){
		return new WxshowDO();
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

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
		changeProperty("userid",userid);
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
        case "userid":
            return userid;
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
		case "userid":
			userid = (Integer)obj;
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
		return "Wxshow[id:"+ id+",userid:"+ userid+",time:"+ time+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<WxshowDO ,Key>{
		public static final String DB_TABLE_NAME = "wxshow";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String USERID = "userid";
		public static final String TIME = "time";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("userid", "userid", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("time", "time", java.util.Date.class, new TypeReference<java.util.Date>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `wxshow` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `wxshow` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(WxshowDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object useridPtr;
			useridPtr = t.getUserid();

			ps.setObject(i++, useridPtr);
			Object timePtr;
			timePtr = t.getTime();

			ps.setObject(i++, timePtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(WxshowDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object useridPtr;
				useridPtr = t.getUserid();

			ps.setObject(i++,  useridPtr);
			Object timePtr;
				timePtr = t.getTime();

			ps.setObject(i++,  timePtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(WxshowDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `wxshow` (`userid`,`time`) VALUES (?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `wxshow` (`id`,`userid`,`time`) VALUES (?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `wxshow` (`userid`,`time`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `wxshow` SET `userid`=?,`time`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `wxshow` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `wxshow`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `wxshow` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `wxshow` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `wxshow` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<WxshowDO> getRowMapper(){
			return new RowMapper<WxshowDO>() {
				@Override
				public WxshowDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					WxshowDO o=new WxshowDO();
					o.id = rs.getInt("id");
					o.userid = rs.getInt("userid");
					o.time = rs.getTimestamp("time");
					return o;
				}
			};
		}

		@Override public <C extends WxshowDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setUserid(rs.getInt("userid"));
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
