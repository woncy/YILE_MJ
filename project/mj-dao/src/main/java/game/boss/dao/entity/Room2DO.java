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

import mj.data.Config;

import  org.forkjoin.core.dao.EntityObject;
import  org.forkjoin.core.dao.KeyObject;
import  org.forkjoin.core.dao.TableInfo;

public class Room2DO extends EntityObject<Room2DO, Room2DO.Key>{

	private int id;
	/**创建人*/
	private int createUserId;
	/**最大人数*/
	private Integer userMax;
	private String roomCheckId;
	private java.util.Date startDate;
	private java.util.Date endDate;
	private Boolean start;
	private Boolean end;
	private Integer sceneId;
	private mj.data.Config config;

	public static class Key implements KeyObject<Room2DO, Room2DO.Key>{
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
			return "Room2[id:"+ id+ "]";
		}
	}

	@Override
	public Key getKey() {
		return new Key() {

			public int getId() {
				return id;
			}

			public void setId(int id) {
				Room2DO.this.id  = id;
				Room2DO.this.changeProperty("id",id);
			}

			@Override
			public String toString() {
				return "Room2[id:"+ id+ "]";
			}
		};
	}




	public Room2DO() {
    }

	public Room2DO(int id,int createUserId,Integer userMax,String roomCheckId,java.util.Date startDate,java.util.Date endDate,Boolean start,Boolean end,Integer sceneId,mj.data.Config config) {
		this.id = id;
		this.createUserId = createUserId;
		this.userMax = userMax;
		this.roomCheckId = roomCheckId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.start = start;
		this.end = end;
		this.sceneId = sceneId;
		this.config = config;
	}


	public Room2DO newInstance(){
		return new Room2DO();
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

	/**
	 * 创建人
	 **/
	public int getCreateUserId() {
		return createUserId;
	}

	/**
	 * 创建人
	 **/
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
		changeProperty("createUserId",createUserId);
	}

	/**
	 * 最大人数
	 **/
	public Integer getUserMax() {
		return userMax;
	}

	/**
	 * 最大人数
	 **/
	public void setUserMax(Integer userMax) {
		this.userMax = userMax;
		changeProperty("userMax",userMax);
	}

	public String getRoomCheckId() {
		return roomCheckId;
	}

	public void setRoomCheckId(String roomCheckId) {
		this.roomCheckId = roomCheckId;
		changeProperty("roomCheckId",roomCheckId);
	}

	public java.util.Date getStartDate() {
		return startDate;
	}

	public void setStartDate(java.util.Date startDate) {
		this.startDate = startDate;
		changeProperty("startDate",startDate);
	}

	public java.util.Date getEndDate() {
		return endDate;
	}

	public void setEndDate(java.util.Date endDate) {
		this.endDate = endDate;
		changeProperty("endDate",endDate);
	}

	public Boolean getStart() {
		return start;
	}

	public void setStart(Boolean start) {
		this.start = start;
		changeProperty("start",start);
	}

	public Boolean getEnd() {
		return end;
	}

	public void setEnd(Boolean end) {
		this.end = end;
		changeProperty("end",end);
	}

	public Integer getSceneId() {
		return sceneId;
	}

	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
		changeProperty("sceneId",sceneId);
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
		changeProperty("config",config);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "create_user_id":
            return createUserId;
        case "user_max":
            return userMax;
        case "room_check_id":
            return roomCheckId;
        case "start_date":
            return startDate;
        case "end_date":
            return endDate;
        case "start":
            return start;
        case "end":
            return end;
        case "scene_id":
            return sceneId;
        case "config":
            return config;
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
		case "create_user_id":
			createUserId = (int)obj;
			return true;
		case "user_max":
			userMax = (Integer)obj;
			return true;
		case "room_check_id":
			roomCheckId = (String)obj;
			return true;
		case "start_date":
			startDate = (java.util.Date)obj;
			return true;
		case "end_date":
			endDate = (java.util.Date)obj;
			return true;
		case "start":
			start = (Boolean)obj;
			return true;
		case "end":
			end = (Boolean)obj;
			return true;
		case "scene_id":
			sceneId = (Integer)obj;
			return true;
		case "config":
			config = (Config)obj;
			return true;
		default :
			return false;
		}
	}


	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<Room2DO ,Key>{
		public static final String DB_TABLE_NAME = "room2";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String CREATE_USER_ID = "create_user_id";
		public static final String USER_MAX = "user_max";
		public static final String ROOM_CHECK_ID = "room_check_id";
		public static final String START_DATE = "start_date";
		public static final String END_DATE = "end_date";
		public static final String START = "start";
		public static final String END = "end";
		public static final String SCENE_ID = "scene_id";
		public static final String CONFIG = "config";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("create_user_id", "createUserId", int.class, new TypeReference<Integer>() {});
			super.initProperty("user_max", "userMax", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("room_check_id", "roomCheckId", String.class, new TypeReference<String>() {});
			super.initProperty("start_date", "startDate", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("end_date", "endDate", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("start", "start", Boolean.class, new TypeReference<Boolean>() {});
			super.initProperty("end", "end", Boolean.class, new TypeReference<Boolean>() {});
			super.initProperty("scene_id", "sceneId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("config", "config", String.class, new TypeReference<String>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `room2` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `room2` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(Room2DO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			if(isSetUnique){
				ps.setObject(i++, idPtr);
			}
			Object createUserIdPtr;
			createUserIdPtr = t.getCreateUserId();

			ps.setObject(i++, createUserIdPtr);
			Object userMaxPtr;
			userMaxPtr = t.getUserMax();

			ps.setObject(i++, userMaxPtr);
			Object roomCheckIdPtr;
			roomCheckIdPtr = t.getRoomCheckId();

			ps.setObject(i++, roomCheckIdPtr);
			Object startDatePtr;
			startDatePtr = t.getStartDate();

			ps.setObject(i++, startDatePtr);
			Object endDatePtr;
			endDatePtr = t.getEndDate();

			ps.setObject(i++, endDatePtr);
			Object startPtr;
			startPtr = t.getStart();

			ps.setObject(i++, startPtr);
			Object endPtr;
			endPtr = t.getEnd();

			ps.setObject(i++, endPtr);
			Object sceneIdPtr;
			sceneIdPtr = t.getSceneId();

			ps.setObject(i++, sceneIdPtr);
			Object configPtr;
			if(t.getConfig() != null){
				configPtr = com.isnowfox.util.JsonUtils.serialize(t.getConfig());
			}else{
				configPtr = null;
			}
			ps.setObject(i++, configPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(Room2DO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object createUserIdPtr;
				createUserIdPtr = t.getCreateUserId();

			ps.setObject(i++,  createUserIdPtr);
			Object userMaxPtr;
				userMaxPtr = t.getUserMax();

			ps.setObject(i++,  userMaxPtr);
			Object roomCheckIdPtr;
				roomCheckIdPtr = t.getRoomCheckId();

			ps.setObject(i++,  roomCheckIdPtr);
			Object startDatePtr;
				startDatePtr = t.getStartDate();

			ps.setObject(i++,  startDatePtr);
			Object endDatePtr;
				endDatePtr = t.getEndDate();

			ps.setObject(i++,  endDatePtr);
			Object startPtr;
				startPtr = t.getStart();

			ps.setObject(i++,  startPtr);
			Object endPtr;
				endPtr = t.getEnd();

			ps.setObject(i++,  endPtr);
			Object sceneIdPtr;
				sceneIdPtr = t.getSceneId();

			ps.setObject(i++,  sceneIdPtr);
			Object configPtr;
			configPtr = t.getConfig();

			ps.setObject(i++,  configPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(Room2DO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `room2` (`id`,`create_user_id`,`user_max`,`room_check_id`,`start_date`,`end_date`,`start`,`end`,`scene_id`,`config`) VALUES (?,?,?,?,?,?,?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `room2` (`id`,`create_user_id`,`user_max`,`room_check_id`,`start_date`,`end_date`,`start`,`end`,`scene_id`,`config`) VALUES (?,?,?,?,?,?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `room2` (`id`,`create_user_id`,`user_max`,`room_check_id`,`start_date`,`end_date`,`start`,`end`,`scene_id`,`config`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?,?,?,?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `room2` SET `id`=?,`create_user_id`=?,`user_max`=?,`room_check_id`=?,`start_date`=?,`end_date`=?,`start`=?,`end`=?,`scene_id`=?,`config`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `room2` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `room2`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `room2` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `room2` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `room2` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<Room2DO> getRowMapper(){
			return new RowMapper<Room2DO>() {
				@Override
				public Room2DO mapRow(ResultSet rs, int rowNum) throws SQLException {
					Room2DO o=new Room2DO();
					o.id = rs.getInt("id");
					o.createUserId = rs.getInt("create_user_id");
					o.userMax = rs.getInt("user_max");
					o.roomCheckId = rs.getString("room_check_id");
					o.startDate = rs.getTimestamp("start_date");
					o.endDate = rs.getTimestamp("end_date");
					o.start = rs.getBoolean("start");
					o.end = rs.getBoolean("end");
					o.sceneId = rs.getInt("scene_id");
					String configStr = rs.getString("config");
					if (com.isnowfox.util.StringExpandUtils.isNotEmpty(configStr)) {
						o.config =  com.isnowfox.util.JsonUtils.deserialize(configStr,new com.fasterxml.jackson.core.type.TypeReference<mj.data.Config>(){});
					}else{
						o.config = null;
					}
					return o;
				}
			};
		}

		@Override public <C extends Room2DO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setCreateUserId(rs.getInt("create_user_id"));
						o.setUserMax(rs.getInt("user_max"));
						o.setRoomCheckId(rs.getString("room_check_id"));
						o.setStartDate(rs.getTimestamp("start_date"));
						o.setEndDate(rs.getTimestamp("end_date"));
						o.setStart(rs.getBoolean("start"));
						o.setEnd(rs.getBoolean("end"));
						o.setSceneId(rs.getInt("scene_id"));
						String configStr = rs.getString("config");
						if (com.isnowfox.util.StringExpandUtils.isNotEmpty(configStr)) {
							o.setConfig(com.isnowfox.util.JsonUtils.deserialize(configStr,new com.fasterxml.jackson.core.type.TypeReference<mj.data.Config>(){}));
						}else{
							o.setConfig(null);
						}
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
