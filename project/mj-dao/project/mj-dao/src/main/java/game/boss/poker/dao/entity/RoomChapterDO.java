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

public class RoomChapterDO extends EntityObject<RoomChapterDO, RoomChapterDO.Key>{

	private int id;
	/**局数*/
	private int chapterNum;
	/**房间id*/
	private int roomId;
	/**玩家id*/
	private int userId;
	/**玩家未知*/
	private int userIndex;
	/**玩家姓名*/
	private String userName;
	private java.util.Date startDate;
	private java.util.Date endDate;
	/**1开始，2结束*/
	private Integer state;
	/**用户最终得分*/
	private Integer score;
	/**用户最终的牌*/
	private String pais;
	private Boolean isZhuang;

	public static class Key implements KeyObject<RoomChapterDO, RoomChapterDO.Key>{
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
			return "RoomChapter[id:"+ id+ "]";
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
				return "RoomChapter[id:"+ id+ "]";
			}
		};
	}




	public RoomChapterDO() {
    }

	public RoomChapterDO(int chapterNum,int roomId,int userId,int userIndex,String userName,java.util.Date startDate,java.util.Date endDate,Integer state,Integer score,String pais,Boolean isZhuang) {
		this.chapterNum = chapterNum;
		this.roomId = roomId;
		this.userId = userId;
		this.userIndex = userIndex;
		this.userName = userName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.state = state;
		this.score = score;
		this.pais = pais;
		this.isZhuang = isZhuang;
	}


	public RoomChapterDO newInstance(){
		return new RoomChapterDO();
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
	 * 局数
	 **/
	public int getChapterNum() {
		return chapterNum;
	}

	/**
	 * 局数
	 **/
	public void setChapterNum(int chapterNum) {
		this.chapterNum = chapterNum;
		changeProperty("chapterNum",chapterNum);
	}

	/**
	 * 房间id
	 **/
	public int getRoomId() {
		return roomId;
	}

	/**
	 * 房间id
	 **/
	public void setRoomId(int roomId) {
		this.roomId = roomId;
		changeProperty("roomId",roomId);
	}

	/**
	 * 玩家id
	 **/
	public int getUserId() {
		return userId;
	}

	/**
	 * 玩家id
	 **/
	public void setUserId(int userId) {
		this.userId = userId;
		changeProperty("userId",userId);
	}

	/**
	 * 玩家未知
	 **/
	public int getUserIndex() {
		return userIndex;
	}

	/**
	 * 玩家未知
	 **/
	public void setUserIndex(int userIndex) {
		this.userIndex = userIndex;
		changeProperty("userIndex",userIndex);
	}

	/**
	 * 玩家姓名
	 **/
	public String getUserName() {
		return userName;
	}

	/**
	 * 玩家姓名
	 **/
	public void setUserName(String userName) {
		this.userName = userName;
		changeProperty("userName",userName);
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

	/**
	 * 1开始，2结束
	 **/
	public Integer getState() {
		return state;
	}

	/**
	 * 1开始，2结束
	 **/
	public void setState(Integer state) {
		this.state = state;
		changeProperty("state",state);
	}

	/**
	 * 用户最终得分
	 **/
	public Integer getScore() {
		return score;
	}

	/**
	 * 用户最终得分
	 **/
	public void setScore(Integer score) {
		this.score = score;
		changeProperty("score",score);
	}

	/**
	 * 用户最终的牌
	 **/
	public String getPais() {
		return pais;
	}

	/**
	 * 用户最终的牌
	 **/
	public void setPais(String pais) {
		this.pais = pais;
		changeProperty("pais",pais);
	}

	public Boolean getIsZhuang() {
		return isZhuang;
	}

	public void setIsZhuang(Boolean isZhuang) {
		this.isZhuang = isZhuang;
		changeProperty("isZhuang",isZhuang);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "chapter_num":
            return chapterNum;
        case "room_id":
            return roomId;
        case "user_id":
            return userId;
        case "user_index":
            return userIndex;
        case "user_name":
            return userName;
        case "start_date":
            return startDate;
        case "end_date":
            return endDate;
        case "state":
            return state;
        case "score":
            return score;
        case "pais":
            return pais;
        case "isZhuang":
            return isZhuang;
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
		case "chapter_num":
			chapterNum = (int)obj;
			return true;
		case "room_id":
			roomId = (int)obj;
			return true;
		case "user_id":
			userId = (int)obj;
			return true;
		case "user_index":
			userIndex = (int)obj;
			return true;
		case "user_name":
			userName = (String)obj;
			return true;
		case "start_date":
			startDate = (java.util.Date)obj;
			return true;
		case "end_date":
			endDate = (java.util.Date)obj;
			return true;
		case "state":
			state = (Integer)obj;
			return true;
		case "score":
			score = (Integer)obj;
			return true;
		case "pais":
			pais = (String)obj;
			return true;
		case "isZhuang":
			isZhuang = (Boolean)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "RoomChapter[id:"+ id+",chapterNum:"+ chapterNum+",roomId:"+ roomId+",userId:"+ userId+",userIndex:"+ userIndex+",userName:"+ (userName == null ?"null":userName.substring(0, Math.min(userName.length(), 64)))+",startDate:"+ startDate+",endDate:"+ endDate+",state:"+ state+",score:"+ score+",pais:"+ (pais == null ?"null":pais.substring(0, Math.min(pais.length(), 64)))+",isZhuang:"+ isZhuang+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<RoomChapterDO ,Key>{
		public static final String DB_TABLE_NAME = "room_chapter";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String CHAPTER_NUM = "chapter_num";
		public static final String ROOM_ID = "room_id";
		public static final String USER_ID = "user_id";
		public static final String USER_INDEX = "user_index";
		public static final String USER_NAME = "user_name";
		public static final String START_DATE = "start_date";
		public static final String END_DATE = "end_date";
		public static final String STATE = "state";
		public static final String SCORE = "score";
		public static final String PAIS = "pais";
		public static final String ISZHUANG = "isZhuang";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("chapter_num", "chapterNum", int.class, new TypeReference<Integer>() {});
			super.initProperty("room_id", "roomId", int.class, new TypeReference<Integer>() {});
			super.initProperty("user_id", "userId", int.class, new TypeReference<Integer>() {});
			super.initProperty("user_index", "userIndex", int.class, new TypeReference<Integer>() {});
			super.initProperty("user_name", "userName", String.class, new TypeReference<String>() {});
			super.initProperty("start_date", "startDate", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("end_date", "endDate", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("state", "state", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("score", "score", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("pais", "pais", String.class, new TypeReference<String>() {});
			super.initProperty("isZhuang", "isZhuang", Boolean.class, new TypeReference<Boolean>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `room_chapter` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `room_chapter` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(RoomChapterDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object chapterNumPtr;
			chapterNumPtr = t.getChapterNum();

			ps.setObject(i++, chapterNumPtr);
			Object roomIdPtr;
			roomIdPtr = t.getRoomId();

			ps.setObject(i++, roomIdPtr);
			Object userIdPtr;
			userIdPtr = t.getUserId();

			ps.setObject(i++, userIdPtr);
			Object userIndexPtr;
			userIndexPtr = t.getUserIndex();

			ps.setObject(i++, userIndexPtr);
			Object userNamePtr;
			userNamePtr = t.getUserName();

			ps.setObject(i++, userNamePtr);
			Object startDatePtr;
			startDatePtr = t.getStartDate();

			ps.setObject(i++, startDatePtr);
			Object endDatePtr;
			endDatePtr = t.getEndDate();

			ps.setObject(i++, endDatePtr);
			Object statePtr;
			statePtr = t.getState();

			ps.setObject(i++, statePtr);
			Object scorePtr;
			scorePtr = t.getScore();

			ps.setObject(i++, scorePtr);
			Object paisPtr;
			paisPtr = t.getPais();

			ps.setObject(i++, paisPtr);
			Object isZhuangPtr;
			isZhuangPtr = t.getIsZhuang();

			ps.setObject(i++, isZhuangPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(RoomChapterDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object chapterNumPtr;
				chapterNumPtr = t.getChapterNum();

			ps.setObject(i++,  chapterNumPtr);
			Object roomIdPtr;
				roomIdPtr = t.getRoomId();

			ps.setObject(i++,  roomIdPtr);
			Object userIdPtr;
				userIdPtr = t.getUserId();

			ps.setObject(i++,  userIdPtr);
			Object userIndexPtr;
				userIndexPtr = t.getUserIndex();

			ps.setObject(i++,  userIndexPtr);
			Object userNamePtr;
				userNamePtr = t.getUserName();

			ps.setObject(i++,  userNamePtr);
			Object startDatePtr;
				startDatePtr = t.getStartDate();

			ps.setObject(i++,  startDatePtr);
			Object endDatePtr;
				endDatePtr = t.getEndDate();

			ps.setObject(i++,  endDatePtr);
			Object statePtr;
				statePtr = t.getState();

			ps.setObject(i++,  statePtr);
			Object scorePtr;
				scorePtr = t.getScore();

			ps.setObject(i++,  scorePtr);
			Object paisPtr;
				paisPtr = t.getPais();

			ps.setObject(i++,  paisPtr);
			Object isZhuangPtr;
				isZhuangPtr = t.getIsZhuang();

			ps.setObject(i++,  isZhuangPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(RoomChapterDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `room_chapter` (`chapter_num`,`room_id`,`user_id`,`user_index`,`user_name`,`start_date`,`end_date`,`state`,`score`,`pais`,`isZhuang`) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `room_chapter` (`id`,`chapter_num`,`room_id`,`user_id`,`user_index`,`user_name`,`start_date`,`end_date`,`state`,`score`,`pais`,`isZhuang`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `room_chapter` (`chapter_num`,`room_id`,`user_id`,`user_index`,`user_name`,`start_date`,`end_date`,`state`,`score`,`pais`,`isZhuang`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?,?,?,?,?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `room_chapter` SET `chapter_num`=?,`room_id`=?,`user_id`=?,`user_index`=?,`user_name`=?,`start_date`=?,`end_date`=?,`state`=?,`score`=?,`pais`=?,`isZhuang`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `room_chapter` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `room_chapter`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `room_chapter` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `room_chapter` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `room_chapter` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<RoomChapterDO> getRowMapper(){
			return new RowMapper<RoomChapterDO>() {
				@Override
				public RoomChapterDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					RoomChapterDO o=new RoomChapterDO();
					o.id = rs.getInt("id");
					o.chapterNum = rs.getInt("chapter_num");
					o.roomId = rs.getInt("room_id");
					o.userId = rs.getInt("user_id");
					o.userIndex = rs.getInt("user_index");
					o.userName = rs.getString("user_name");
					o.startDate = rs.getTimestamp("start_date");
					o.endDate = rs.getTimestamp("end_date");
					o.state = rs.getInt("state");
					o.score = rs.getInt("score");
					o.pais = rs.getString("pais");
					o.isZhuang = rs.getBoolean("isZhuang");
					return o;
				}
			};
		}

		@Override public <C extends RoomChapterDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setChapterNum(rs.getInt("chapter_num"));
						o.setRoomId(rs.getInt("room_id"));
						o.setUserId(rs.getInt("user_id"));
						o.setUserIndex(rs.getInt("user_index"));
						o.setUserName(rs.getString("user_name"));
						o.setStartDate(rs.getTimestamp("start_date"));
						o.setEndDate(rs.getTimestamp("end_date"));
						o.setState(rs.getInt("state"));
						o.setScore(rs.getInt("score"));
						o.setPais(rs.getString("pais"));
						o.setIsZhuang(rs.getBoolean("isZhuang"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
