package  game.boss.dao.entity;

import java.beans.Transient;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import  org.forkjoin.core.dao.EntityObject;
import  org.forkjoin.core.dao.KeyObject;
import  org.forkjoin.core.dao.TableInfo;
import org.forkjoin.core.dao.UniqueInfo;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;

public class RoomDetailDO extends EntityObject<RoomDetailDO, RoomDetailDO.Key>{

	private int id;
	private String chepterIndex;
	private String checkRoomId;
	private String actionDetail;
	private String playBackCode;

	public static class Key implements KeyObject<RoomDetailDO, RoomDetailDO.Key>{
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
			return "RoomDetail[id:"+ id+ "]";
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
				return "RoomDetail[id:"+ id+ "]";
			}
		};
	}




	public RoomDetailDO() {
    }

	public RoomDetailDO(String chepterIndex,String checkRoomId,String actionDetail,String playBackCode) {
		this.chepterIndex = chepterIndex;
		this.checkRoomId = checkRoomId;
		this.actionDetail = actionDetail;
		this.playBackCode = playBackCode;
	}


	public RoomDetailDO newInstance(){
		return new RoomDetailDO();
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

	public String getChepterIndex() {
		return chepterIndex;
	}

	public void setChepterIndex(String chepterIndex) {
		this.chepterIndex = chepterIndex;
		changeProperty("chepterIndex",chepterIndex);
	}

	public String getCheckRoomId() {
		return checkRoomId;
	}

	public void setCheckRoomId(String checkRoomId) {
		this.checkRoomId = checkRoomId;
		changeProperty("checkRoomId",checkRoomId);
	}

	public String getActionDetail() {
		return actionDetail;
	}

	public void setActionDetail(String actionDetail) {
		this.actionDetail = actionDetail;
		changeProperty("actionDetail",actionDetail);
	}

	public String getPlayBackCode() {
		return playBackCode;
	}

	public void setPlayBackCode(String playBackCode) {
		this.playBackCode = playBackCode;
		changeProperty("playBackCode",playBackCode);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "chepter_index":
            return chepterIndex;
        case "check_room_id":
            return checkRoomId;
        case "action_detail":
            return actionDetail;
        case "play_back_code":
            return playBackCode;
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
		case "chepter_index":
			chepterIndex = (String)obj;
			return true;
		case "check_room_id":
			checkRoomId = (String)obj;
			return true;
		case "action_detail":
			actionDetail = (String)obj;
			return true;
		case "play_back_code":
			playBackCode = (String)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "RoomDetail[id:"+ id+",chepterIndex:"+ (chepterIndex == null ?"null":chepterIndex.substring(0, Math.min(chepterIndex.length(), 64)))+",checkRoomId:"+ (checkRoomId == null ?"null":checkRoomId.substring(0, Math.min(checkRoomId.length(), 64)))+",actionDetail:"+ (actionDetail == null ?"null":actionDetail.substring(0, Math.min(actionDetail.length(), 64)))+",playBackCode:"+ (playBackCode == null ?"null":playBackCode.substring(0, Math.min(playBackCode.length(), 64)))+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<RoomDetailDO ,Key>{
		public static final String DB_TABLE_NAME = "room_detail";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String CHEPTER_INDEX = "chepter_index";
		public static final String CHECK_ROOM_ID = "check_room_id";
		public static final String ACTION_DETAIL = "action_detail";
		public static final String PLAY_BACK_CODE = "play_back_code";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("chepter_index", "chepterIndex", String.class, new TypeReference<String>() {});
			super.initProperty("check_room_id", "checkRoomId", String.class, new TypeReference<String>() {});
			super.initProperty("action_detail", "actionDetail", String.class, new TypeReference<String>() {});
			super.initProperty("play_back_code", "playBackCode", String.class, new TypeReference<String>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `room_detail` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `room_detail` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(RoomDetailDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object chepterIndexPtr;
			chepterIndexPtr = t.getChepterIndex();

			ps.setObject(i++, chepterIndexPtr);
			Object checkRoomIdPtr;
			checkRoomIdPtr = t.getCheckRoomId();

			ps.setObject(i++, checkRoomIdPtr);
			Object actionDetailPtr;
			actionDetailPtr = t.getActionDetail();

			ps.setObject(i++, actionDetailPtr);
			Object playBackCodePtr;
			playBackCodePtr = t.getPlayBackCode();

			ps.setObject(i++, playBackCodePtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(RoomDetailDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object chepterIndexPtr;
				chepterIndexPtr = t.getChepterIndex();

			ps.setObject(i++,  chepterIndexPtr);
			Object checkRoomIdPtr;
				checkRoomIdPtr = t.getCheckRoomId();

			ps.setObject(i++,  checkRoomIdPtr);
			Object actionDetailPtr;
				actionDetailPtr = t.getActionDetail();

			ps.setObject(i++,  actionDetailPtr);
			Object playBackCodePtr;
				playBackCodePtr = t.getPlayBackCode();

			ps.setObject(i++,  playBackCodePtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(RoomDetailDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `room_detail` (`chepter_index`,`check_room_id`,`action_detail`,`play_back_code`) VALUES (?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `room_detail` (`id`,`chepter_index`,`check_room_id`,`action_detail`,`play_back_code`) VALUES (?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `room_detail` (`chepter_index`,`check_room_id`,`action_detail`,`play_back_code`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `room_detail` SET `chepter_index`=?,`check_room_id`=?,`action_detail`=?,`play_back_code`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `room_detail` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `room_detail`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `room_detail` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `room_detail` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `room_detail` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<RoomDetailDO> getRowMapper(){
			return new RowMapper<RoomDetailDO>() {
				@Override
				public RoomDetailDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					RoomDetailDO o=new RoomDetailDO();
					o.id = rs.getInt("id");
					o.chepterIndex = rs.getString("chepter_index");
					o.checkRoomId = rs.getString("check_room_id");
					o.actionDetail = rs.getString("action_detail");
					o.playBackCode = rs.getString("play_back_code");
					return o;
				}
			};
		}

		@Override public <C extends RoomDetailDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setChepterIndex(rs.getString("chepter_index"));
						o.setCheckRoomId(rs.getString("check_room_id"));
						o.setActionDetail(rs.getString("action_detail"));
						o.setPlayBackCode(rs.getString("play_back_code"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
