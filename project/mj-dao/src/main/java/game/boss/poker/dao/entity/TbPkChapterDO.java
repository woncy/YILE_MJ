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
	/**房间id*/
	private int roomId;
	private Integer num;
	private java.util.Date startDate;
	private java.util.Date endDate;

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

	public TbPkChapterDO(int roomId,Integer num,java.util.Date startDate,java.util.Date endDate) {
		this.roomId = roomId;
		this.num = num;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
		changeProperty("num",num);
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

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "room_id":
            return roomId;
        case "num":
            return num;
        case "startDate":
            return startDate;
        case "endDate":
            return endDate;
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
			roomId = (int)obj;
			return true;
		case "num":
			num = (Integer)obj;
			return true;
		case "startDate":
			startDate = (java.util.Date)obj;
			return true;
		case "endDate":
			endDate = (java.util.Date)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "TbPkChapter[id:"+ id+",roomId:"+ roomId+",num:"+ num+",startDate:"+ startDate+",endDate:"+ endDate+ "]";
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
		public static final String ROOM_ID = "room_id";
		public static final String NUM = "num";
		public static final String STARTDATE = "startDate";
		public static final String ENDDATE = "endDate";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("room_id", "roomId", int.class, new TypeReference<Integer>() {});
			super.initProperty("num", "num", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("startDate", "startDate", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("endDate", "endDate", java.util.Date.class, new TypeReference<java.util.Date>() {});
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

			Object roomIdPtr;
			roomIdPtr = t.getRoomId();

			ps.setObject(i++, roomIdPtr);
			Object numPtr;
			numPtr = t.getNum();

			ps.setObject(i++, numPtr);
			Object startDatePtr;
			startDatePtr = t.getStartDate();

			ps.setObject(i++, startDatePtr);
			Object endDatePtr;
			endDatePtr = t.getEndDate();

			ps.setObject(i++, endDatePtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(TbPkChapterDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object roomIdPtr;
				roomIdPtr = t.getRoomId();

			ps.setObject(i++,  roomIdPtr);
			Object numPtr;
				numPtr = t.getNum();

			ps.setObject(i++,  numPtr);
			Object startDatePtr;
				startDatePtr = t.getStartDate();

			ps.setObject(i++,  startDatePtr);
			Object endDatePtr;
				endDatePtr = t.getEndDate();

			ps.setObject(i++,  endDatePtr);
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
			return "INSERT INTO `tb_pk_chapter` (`room_id`,`num`,`startDate`,`endDate`) VALUES (?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `tb_pk_chapter` (`id`,`room_id`,`num`,`startDate`,`endDate`) VALUES (?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `tb_pk_chapter` (`room_id`,`num`,`startDate`,`endDate`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `tb_pk_chapter` SET `room_id`=?,`num`=?,`startDate`=?,`endDate`=? WHERE `id`=?";
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
					o.roomId = rs.getInt("room_id");
					o.num = rs.getInt("num");
					o.startDate = rs.getTimestamp("startDate");
					o.endDate = rs.getTimestamp("endDate");
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
						o.setRoomId(rs.getInt("room_id"));
						o.setNum(rs.getInt("num"));
						o.setStartDate(rs.getTimestamp("startDate"));
						o.setEndDate(rs.getTimestamp("endDate"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
