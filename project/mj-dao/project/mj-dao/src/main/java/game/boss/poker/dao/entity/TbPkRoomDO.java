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

public class TbPkRoomDO extends EntityObject<TbPkRoomDO, TbPkRoomDO.Key>{

	/**牌局id*/
	private int id;
	/**创建用户id*/
	private int createUserId;
	/**用户数*/
	private Integer userMax;
	/**牌局uuid*/
	private String uuid;
	/**房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同*/
	private String roomCheckId;
	private java.util.Date startDate;
	private java.util.Date endDate;
	private Boolean start;
	/**正常结束*/
	private Boolean end;
	private Integer version;
	private Integer sceneId;
	/**局数*/
	private Integer chapterNums;
	/**配置*/
	private mj.data.Config config;
	/**初始分数*/
	private Integer initScore;
	private Integer createUserLevel;

	public static class Key implements KeyObject<TbPkRoomDO, TbPkRoomDO.Key>{
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
			return "TbPkRoom[id:"+ id+ "]";
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
				return "TbPkRoom[id:"+ id+ "]";
			}
		};
	}




	public TbPkRoomDO() {
    }

	public TbPkRoomDO(int createUserId,Integer userMax,String uuid,String roomCheckId,java.util.Date startDate,java.util.Date endDate,Boolean start,Boolean end,Integer version,Integer sceneId,Integer chapterNums,mj.data.Config config,Integer initScore,Integer createUserLevel) {
		this.createUserId = createUserId;
		this.userMax = userMax;
		this.uuid = uuid;
		this.roomCheckId = roomCheckId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.start = start;
		this.end = end;
		this.version = version;
		this.sceneId = sceneId;
		this.chapterNums = chapterNums;
		this.config = config;
		this.initScore = initScore;
		this.createUserLevel = createUserLevel;
	}


	public TbPkRoomDO newInstance(){
		return new TbPkRoomDO();
	}

    public void setKey(Object key){
        this.id = ((Number)key).intValue();
    }

	/**
	 * 牌局id
	 **/
	public int getId() {
		return id;
	}

	/**
	 * 牌局id
	 **/
	public void setId(int id) {
		this.id = id;
		changeProperty("id",id);
	}

	/**
	 * 创建用户id
	 **/
	public int getCreateUserId() {
		return createUserId;
	}

	/**
	 * 创建用户id
	 **/
	public void setCreateUserId(int createUserId) {
		this.createUserId = createUserId;
		changeProperty("createUserId",createUserId);
	}

	/**
	 * 用户数
	 **/
	public Integer getUserMax() {
		return userMax;
	}

	/**
	 * 用户数
	 **/
	public void setUserMax(Integer userMax) {
		this.userMax = userMax;
		changeProperty("userMax",userMax);
	}

	/**
	 * 牌局uuid
	 **/
	public String getUuid() {
		return uuid;
	}

	/**
	 * 牌局uuid
	 **/
	public void setUuid(String uuid) {
		this.uuid = uuid;
		changeProperty("uuid",uuid);
	}

	/**
	 * 房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同
	 **/
	public String getRoomCheckId() {
		return roomCheckId;
	}

	/**
	 * 房间的check-id,进入id,可以重复,但是不允许同时活跃状态的id 相同
	 **/
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

	/**
	 * 正常结束
	 **/
	public Boolean getEnd() {
		return end;
	}

	/**
	 * 正常结束
	 **/
	public void setEnd(Boolean end) {
		this.end = end;
		changeProperty("end",end);
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
		changeProperty("version",version);
	}

	public Integer getSceneId() {
		return sceneId;
	}

	public void setSceneId(Integer sceneId) {
		this.sceneId = sceneId;
		changeProperty("sceneId",sceneId);
	}

	/**
	 * 局数
	 **/
	public Integer getChapterNums() {
		return chapterNums;
	}

	/**
	 * 局数
	 **/
	public void setChapterNums(Integer chapterNums) {
		this.chapterNums = chapterNums;
		changeProperty("chapterNums",chapterNums);
	}

	/**
	 * 配置
	 **/
	public mj.data.Config getConfig() {
		return config;
	}

	/**
	 * 配置
	 **/
	public void setConfig(mj.data.Config config) {
		this.config = config;
		changeProperty("config",config);
	}

	/**
	 * 初始分数
	 **/
	public Integer getInitScore() {
		return initScore;
	}

	/**
	 * 初始分数
	 **/
	public void setInitScore(Integer initScore) {
		this.initScore = initScore;
		changeProperty("initScore",initScore);
	}

	public Integer getCreateUserLevel() {
		return createUserLevel;
	}

	public void setCreateUserLevel(Integer createUserLevel) {
		this.createUserLevel = createUserLevel;
		changeProperty("createUserLevel",createUserLevel);
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
        case "uuid":
            return uuid;
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
        case "version":
            return version;
        case "scene_id":
            return sceneId;
        case "chapter_nums":
            return chapterNums;
        case "config":
            return config;
        case "initScore":
            return initScore;
        case "createUserLevel":
            return createUserLevel;
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
		case "uuid":
			uuid = (String)obj;
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
		case "version":
			version = (Integer)obj;
			return true;
		case "scene_id":
			sceneId = (Integer)obj;
			return true;
		case "chapter_nums":
			chapterNums = (Integer)obj;
			return true;
		case "config":
			config = (mj.data.Config)obj;
			return true;
		case "initScore":
			initScore = (Integer)obj;
			return true;
		case "createUserLevel":
			createUserLevel = (Integer)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "TbPkRoom[id:"+ id+",createUserId:"+ createUserId+",userMax:"+ userMax+",uuid:"+ (uuid == null ?"null":uuid.substring(0, Math.min(uuid.length(), 64)))+",roomCheckId:"+ (roomCheckId == null ?"null":roomCheckId.substring(0, Math.min(roomCheckId.length(), 64)))+",startDate:"+ startDate+",endDate:"+ endDate+",start:"+ start+",end:"+ end+",version:"+ version+",sceneId:"+ sceneId+",chapterNums:"+ chapterNums+",config:"+ config+",initScore:"+ initScore+",createUserLevel:"+ createUserLevel+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<TbPkRoomDO ,Key>{
		public static final String DB_TABLE_NAME = "tb_pk_room";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String CREATE_USER_ID = "create_user_id";
		public static final String USER_MAX = "user_max";
		public static final String UUID = "uuid";
		public static final String ROOM_CHECK_ID = "room_check_id";
		public static final String START_DATE = "start_date";
		public static final String END_DATE = "end_date";
		public static final String START = "start";
		public static final String END = "end";
		public static final String VERSION = "version";
		public static final String SCENE_ID = "scene_id";
		public static final String CHAPTER_NUMS = "chapter_nums";
		public static final String CONFIG = "config";
		public static final String INITSCORE = "initScore";
		public static final String CREATEUSERLEVEL = "createUserLevel";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("create_user_id", "createUserId", int.class, new TypeReference<Integer>() {});
			super.initProperty("user_max", "userMax", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("uuid", "uuid", String.class, new TypeReference<String>() {});
			super.initProperty("room_check_id", "roomCheckId", String.class, new TypeReference<String>() {});
			super.initProperty("start_date", "startDate", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("end_date", "endDate", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("start", "start", Boolean.class, new TypeReference<Boolean>() {});
			super.initProperty("end", "end", Boolean.class, new TypeReference<Boolean>() {});
			super.initProperty("version", "version", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("scene_id", "sceneId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("chapter_nums", "chapterNums", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("config", "config", mj.data.Config.class, new TypeReference<mj.data.Config>() {});
			super.initProperty("initScore", "initScore", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("createUserLevel", "createUserLevel", Integer.class, new TypeReference<Integer>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `tb_pk_room` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `tb_pk_room` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(TbPkRoomDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object createUserIdPtr;
			createUserIdPtr = t.getCreateUserId();

			ps.setObject(i++, createUserIdPtr);
			Object userMaxPtr;
			userMaxPtr = t.getUserMax();

			ps.setObject(i++, userMaxPtr);
			Object uuidPtr;
			uuidPtr = t.getUuid();

			ps.setObject(i++, uuidPtr);
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
			Object versionPtr;
			versionPtr = t.getVersion();

			ps.setObject(i++, versionPtr);
			Object sceneIdPtr;
			sceneIdPtr = t.getSceneId();

			ps.setObject(i++, sceneIdPtr);
			Object chapterNumsPtr;
			chapterNumsPtr = t.getChapterNums();

			ps.setObject(i++, chapterNumsPtr);
			Object configPtr;
			if(t.getConfig() != null){
				configPtr = com.isnowfox.util.JsonUtils.serialize(t.getConfig());
			}else{
				configPtr = null;
			}

			ps.setObject(i++, configPtr);
			Object initScorePtr;
			initScorePtr = t.getInitScore();

			ps.setObject(i++, initScorePtr);
			Object createUserLevelPtr;
			createUserLevelPtr = t.getCreateUserLevel();

			ps.setObject(i++, createUserLevelPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(TbPkRoomDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object createUserIdPtr;
				createUserIdPtr = t.getCreateUserId();

			ps.setObject(i++,  createUserIdPtr);
			Object userMaxPtr;
				userMaxPtr = t.getUserMax();

			ps.setObject(i++,  userMaxPtr);
			Object uuidPtr;
				uuidPtr = t.getUuid();

			ps.setObject(i++,  uuidPtr);
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
			Object versionPtr;
				versionPtr = t.getVersion();

			ps.setObject(i++,  versionPtr);
			Object sceneIdPtr;
				sceneIdPtr = t.getSceneId();

			ps.setObject(i++,  sceneIdPtr);
			Object chapterNumsPtr;
				chapterNumsPtr = t.getChapterNums();

			ps.setObject(i++,  chapterNumsPtr);
			Object configPtr;
			if(t.getConfig() != null){
				configPtr = com.isnowfox.util.JsonUtils.serialize(t.getConfig());
			}else{
				configPtr = null;
			}

			ps.setObject(i++,  configPtr);
			Object initScorePtr;
				initScorePtr = t.getInitScore();

			ps.setObject(i++,  initScorePtr);
			Object createUserLevelPtr;
				createUserLevelPtr = t.getCreateUserLevel();

			ps.setObject(i++,  createUserLevelPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(TbPkRoomDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `tb_pk_room` (`create_user_id`,`user_max`,`uuid`,`room_check_id`,`start_date`,`end_date`,`start`,`end`,`version`,`scene_id`,`chapter_nums`,`config`,`initScore`,`createUserLevel`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `tb_pk_room` (`id`,`create_user_id`,`user_max`,`uuid`,`room_check_id`,`start_date`,`end_date`,`start`,`end`,`version`,`scene_id`,`chapter_nums`,`config`,`initScore`,`createUserLevel`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `tb_pk_room` (`create_user_id`,`user_max`,`uuid`,`room_check_id`,`start_date`,`end_date`,`start`,`end`,`version`,`scene_id`,`chapter_nums`,`config`,`initScore`,`createUserLevel`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `tb_pk_room` SET `create_user_id`=?,`user_max`=?,`uuid`=?,`room_check_id`=?,`start_date`=?,`end_date`=?,`start`=?,`end`=?,`version`=?,`scene_id`=?,`chapter_nums`=?,`config`=?,`initScore`=?,`createUserLevel`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `tb_pk_room` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `tb_pk_room`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `tb_pk_room` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `tb_pk_room` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `tb_pk_room` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<TbPkRoomDO> getRowMapper(){
			return new RowMapper<TbPkRoomDO>() {
				@Override
				public TbPkRoomDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					TbPkRoomDO o=new TbPkRoomDO();
					o.id = rs.getInt("id");
					o.createUserId = rs.getInt("create_user_id");
					o.userMax = rs.getInt("user_max");
					o.uuid = rs.getString("uuid");
					o.roomCheckId = rs.getString("room_check_id");
					o.startDate = rs.getTimestamp("start_date");
					o.endDate = rs.getTimestamp("end_date");
					o.start = rs.getBoolean("start");
					o.end = rs.getBoolean("end");
					o.version = rs.getInt("version");
					o.sceneId = rs.getInt("scene_id");
					o.chapterNums = rs.getInt("chapter_nums");
					String configStr = rs.getString("config");
					if (com.isnowfox.util.StringExpandUtils.isNotEmpty(configStr)) {
						o.config =  com.isnowfox.util.JsonUtils.deserialize(configStr,new com.fasterxml.jackson.core.type.TypeReference<mj.data.Config>(){});
					}else{
						o.config = null;
					}
					o.initScore = rs.getInt("initScore");
					o.createUserLevel = rs.getInt("createUserLevel");
					return o;
				}
			};
		}

		@Override public <C extends TbPkRoomDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setCreateUserId(rs.getInt("create_user_id"));
						o.setUserMax(rs.getInt("user_max"));
						o.setUuid(rs.getString("uuid"));
						o.setRoomCheckId(rs.getString("room_check_id"));
						o.setStartDate(rs.getTimestamp("start_date"));
						o.setEndDate(rs.getTimestamp("end_date"));
						o.setStart(rs.getBoolean("start"));
						o.setEnd(rs.getBoolean("end"));
						o.setVersion(rs.getInt("version"));
						o.setSceneId(rs.getInt("scene_id"));
						o.setChapterNums(rs.getInt("chapter_nums"));
						String configStr = rs.getString("config");
						if (com.isnowfox.util.StringExpandUtils.isNotEmpty(configStr)) {
							o.setConfig(com.isnowfox.util.JsonUtils.deserialize(configStr,new com.fasterxml.jackson.core.type.TypeReference<mj.data.Config>(){}));
						}else{
							o.setConfig(null);
					}
						o.setInitScore(rs.getInt("initScore"));
						o.setCreateUserLevel(rs.getInt("createUserLevel"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
