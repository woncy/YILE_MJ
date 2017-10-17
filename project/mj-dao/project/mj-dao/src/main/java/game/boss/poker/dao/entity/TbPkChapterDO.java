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

public class TbPkChapterDO extends EntityObject<TbPkChapterDO, TbPkChapterDO.Key>{

	private int id;
	private Integer num;
	private Integer roomId;
	private java.util.Date statTime;
	private java.util.Date endTime;

	public static class Key implements KeyObject<TbPkChapterDO, TbPkChapterDO.Key>{
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
			return "TbPkChapter[id:"+ id+ "]";
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
				return "TbPkChapter[id:"+ id+ "]";
			}
		};
	}




	public TbPkChapterDO() {
    }

	public TbPkChapterDO(Integer num,Integer roomId,java.util.Date statTime,java.util.Date endTime) {
		this.num = num;
		this.roomId = roomId;
		this.statTime = statTime;
		this.endTime = endTime;
	}


	public TbPkChapterDO newInstance(){
		return new TbPkChapterDO();
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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
		changeProperty("num",num);
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
		changeProperty("roomId",roomId);
	}

	public java.util.Date getStatTime() {
		return statTime;
	}

	public void setStatTime(java.util.Date statTime) {
		this.statTime = statTime;
		changeProperty("statTime",statTime);
	}

	public java.util.Date getEndTime() {
		return endTime;
	}

	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
		changeProperty("endTime",endTime);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "num":
            return num;
        case "room_id":
            return roomId;
        case "statTime":
            return statTime;
        case "endTime":
            return endTime;
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
		case "num":
			num = (Integer)obj;
			return true;
		case "room_id":
			roomId = (Integer)obj;
			return true;
		case "statTime":
			statTime = (java.util.Date)obj;
			return true;
		case "endTime":
			endTime = (java.util.Date)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "TbPkChapter[id:"+ id+",num:"+ num+",roomId:"+ roomId+",statTime:"+ statTime+",endTime:"+ endTime+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<TbPkChapterDO ,Key>{
		public static final String DB_TABLE_NAME = "tb_pk_chapter";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String NUM = "num";
		public static final String ROOM_ID = "room_id";
		public static final String STATTIME = "statTime";
		public static final String ENDTIME = "endTime";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("num", "num", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("room_id", "roomId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("statTime", "statTime", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("endTime", "endTime", java.util.Date.class, new TypeReference<java.util.Date>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `tb_pk_chapter` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `tb_pk_chapter` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(TbPkChapterDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object numPtr;
			numPtr = t.getNum();

			ps.setObject(i++, numPtr);
			Object roomIdPtr;
			roomIdPtr = t.getRoomId();

			ps.setObject(i++, roomIdPtr);
			Object statTimePtr;
			statTimePtr = t.getStatTime();

			ps.setObject(i++, statTimePtr);
			Object endTimePtr;
			endTimePtr = t.getEndTime();

			ps.setObject(i++, endTimePtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(TbPkChapterDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object numPtr;
				numPtr = t.getNum();

			ps.setObject(i++,  numPtr);
			Object roomIdPtr;
				roomIdPtr = t.getRoomId();

			ps.setObject(i++,  roomIdPtr);
			Object statTimePtr;
				statTimePtr = t.getStatTime();

			ps.setObject(i++,  statTimePtr);
			Object endTimePtr;
				endTimePtr = t.getEndTime();

			ps.setObject(i++,  endTimePtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(TbPkChapterDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `tb_pk_chapter` (`num`,`room_id`,`statTime`,`endTime`) VALUES (?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `tb_pk_chapter` (`id`,`num`,`room_id`,`statTime`,`endTime`) VALUES (?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `tb_pk_chapter` (`num`,`room_id`,`statTime`,`endTime`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `tb_pk_chapter` SET `num`=?,`room_id`=?,`statTime`=?,`endTime`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `tb_pk_chapter` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `tb_pk_chapter`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `tb_pk_chapter` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `tb_pk_chapter` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `tb_pk_chapter` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<TbPkChapterDO> getRowMapper(){
			return new RowMapper<TbPkChapterDO>() {
				@Override
				public TbPkChapterDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					TbPkChapterDO o=new TbPkChapterDO();
					o.id = rs.getInt("id");
					o.num = rs.getInt("num");
					o.roomId = rs.getInt("room_id");
					o.statTime = rs.getTimestamp("statTime");
					o.endTime = rs.getTimestamp("endTime");
					return o;
				}
			};
		}

		@Override public <C extends TbPkChapterDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setNum(rs.getInt("num"));
						o.setRoomId(rs.getInt("room_id"));
						o.setStatTime(rs.getTimestamp("statTime"));
						o.setEndTime(rs.getTimestamp("endTime"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
