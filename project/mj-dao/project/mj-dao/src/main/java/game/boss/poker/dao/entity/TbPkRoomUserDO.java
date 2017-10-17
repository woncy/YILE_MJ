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

public class TbPkRoomUserDO extends EntityObject<TbPkRoomUserDO, TbPkRoomUserDO.Key>{

	private int id;
	/**房间id*/
	private Integer roomId;
	/**最终分数，总得分*/
	private Integer endScore;
	private Integer userId;
	private Integer locationIndex;
	/**赢的次数*/
	private Integer winCount;

	public static class Key implements KeyObject<TbPkRoomUserDO, TbPkRoomUserDO.Key>{
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
			return "TbPkRoomUser[id:"+ id+ "]";
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
				return "TbPkRoomUser[id:"+ id+ "]";
			}
		};
	}




	public TbPkRoomUserDO() {
    }

	public TbPkRoomUserDO(Integer roomId,Integer endScore,Integer userId,Integer locationIndex,Integer winCount) {
		this.roomId = roomId;
		this.endScore = endScore;
		this.userId = userId;
		this.locationIndex = locationIndex;
		this.winCount = winCount;
	}


	public TbPkRoomUserDO newInstance(){
		return new TbPkRoomUserDO();
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
	 * 房间id
	 **/
	public Integer getRoomId() {
		return roomId;
	}

	/**
	 * 房间id
	 **/
	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
		changeProperty("roomId",roomId);
	}

	/**
	 * 最终分数，总得分
	 **/
	public Integer getEndScore() {
		return endScore;
	}

	/**
	 * 最终分数，总得分
	 **/
	public void setEndScore(Integer endScore) {
		this.endScore = endScore;
		changeProperty("endScore",endScore);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
		changeProperty("userId",userId);
	}

	public Integer getLocationIndex() {
		return locationIndex;
	}

	public void setLocationIndex(Integer locationIndex) {
		this.locationIndex = locationIndex;
		changeProperty("locationIndex",locationIndex);
	}

	/**
	 * 赢的次数
	 **/
	public Integer getWinCount() {
		return winCount;
	}

	/**
	 * 赢的次数
	 **/
	public void setWinCount(Integer winCount) {
		this.winCount = winCount;
		changeProperty("winCount",winCount);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "room_id":
            return roomId;
        case "end_score":
            return endScore;
        case "user_id":
            return userId;
        case "locationIndex":
            return locationIndex;
        case "winCount":
            return winCount;
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
		case "room_id":
			roomId = (Integer)obj;
			return true;
		case "end_score":
			endScore = (Integer)obj;
			return true;
		case "user_id":
			userId = (Integer)obj;
			return true;
		case "locationIndex":
			locationIndex = (Integer)obj;
			return true;
		case "winCount":
			winCount = (Integer)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "TbPkRoomUser[id:"+ id+",roomId:"+ roomId+",endScore:"+ endScore+",userId:"+ userId+",locationIndex:"+ locationIndex+",winCount:"+ winCount+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<TbPkRoomUserDO ,Key>{
		public static final String DB_TABLE_NAME = "tb_pk_room_user";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String ROOM_ID = "room_id";
		public static final String END_SCORE = "end_score";
		public static final String USER_ID = "user_id";
		public static final String LOCATIONINDEX = "locationIndex";
		public static final String WINCOUNT = "winCount";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("room_id", "roomId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("end_score", "endScore", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("user_id", "userId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("locationIndex", "locationIndex", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("winCount", "winCount", Integer.class, new TypeReference<Integer>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `tb_pk_room_user` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `tb_pk_room_user` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(TbPkRoomUserDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object roomIdPtr;
			roomIdPtr = t.getRoomId();

			ps.setObject(i++, roomIdPtr);
			Object endScorePtr;
			endScorePtr = t.getEndScore();

			ps.setObject(i++, endScorePtr);
			Object userIdPtr;
			userIdPtr = t.getUserId();

			ps.setObject(i++, userIdPtr);
			Object locationIndexPtr;
			locationIndexPtr = t.getLocationIndex();

			ps.setObject(i++, locationIndexPtr);
			Object winCountPtr;
			winCountPtr = t.getWinCount();

			ps.setObject(i++, winCountPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(TbPkRoomUserDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object roomIdPtr;
				roomIdPtr = t.getRoomId();

			ps.setObject(i++,  roomIdPtr);
			Object endScorePtr;
				endScorePtr = t.getEndScore();

			ps.setObject(i++,  endScorePtr);
			Object userIdPtr;
				userIdPtr = t.getUserId();

			ps.setObject(i++,  userIdPtr);
			Object locationIndexPtr;
				locationIndexPtr = t.getLocationIndex();

			ps.setObject(i++,  locationIndexPtr);
			Object winCountPtr;
				winCountPtr = t.getWinCount();

			ps.setObject(i++,  winCountPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(TbPkRoomUserDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `tb_pk_room_user` (`room_id`,`end_score`,`user_id`,`locationIndex`,`winCount`) VALUES (?,?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `tb_pk_room_user` (`id`,`room_id`,`end_score`,`user_id`,`locationIndex`,`winCount`) VALUES (?,?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `tb_pk_room_user` (`room_id`,`end_score`,`user_id`,`locationIndex`,`winCount`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `tb_pk_room_user` SET `room_id`=?,`end_score`=?,`user_id`=?,`locationIndex`=?,`winCount`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `tb_pk_room_user` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `tb_pk_room_user`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `tb_pk_room_user` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `tb_pk_room_user` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `tb_pk_room_user` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<TbPkRoomUserDO> getRowMapper(){
			return new RowMapper<TbPkRoomUserDO>() {
				@Override
				public TbPkRoomUserDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					TbPkRoomUserDO o=new TbPkRoomUserDO();
					o.id = rs.getInt("id");
					o.roomId = rs.getInt("room_id");
					o.endScore = rs.getInt("end_score");
					o.userId = rs.getInt("user_id");
					o.locationIndex = rs.getInt("locationIndex");
					o.winCount = rs.getInt("winCount");
					return o;
				}
			};
		}

		@Override public <C extends TbPkRoomUserDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setRoomId(rs.getInt("room_id"));
						o.setEndScore(rs.getInt("end_score"));
						o.setUserId(rs.getInt("user_id"));
						o.setLocationIndex(rs.getInt("locationIndex"));
						o.setWinCount(rs.getInt("winCount"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
