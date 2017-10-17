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

public class TbNoticeDO extends EntityObject<TbNoticeDO, TbNoticeDO.Key>{

	private int id;
	private String notice;

	public static class Key implements KeyObject<TbNoticeDO, TbNoticeDO.Key>{
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
			return "TbNotice[id:"+ id+ "]";
		}
	}

	@Override
	public Key getKey() {
		return new Key() {

			public int getId() {
				return id;
			}

			public void setId(int id) {
				TbNoticeDO.this.id  = id;
				TbNoticeDO.this.changeProperty("id",id);
			}

			@Override
			public String toString() {
				return "TbNotice[id:"+ id+ "]";
			}
		};
	}




	public TbNoticeDO() {
    }

	public TbNoticeDO(int id,String notice) {
		this.id = id;
		this.notice = notice;
	}


	public TbNoticeDO newInstance(){
		return new TbNoticeDO();
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

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
		changeProperty("notice",notice);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "notice":
            return notice;
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
		case "notice":
			notice = (String)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "TbNotice[id:"+ id+",notice:"+ (notice == null ?"null":notice.substring(0, Math.min(notice.length(), 64)))+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<TbNoticeDO ,Key>{
		public static final String DB_TABLE_NAME = "tb_notice";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String NOTICE = "notice";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("notice", "notice", String.class, new TypeReference<String>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `tb_notice` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `tb_notice` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(TbNoticeDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			if(isSetUnique){
				ps.setObject(i++, idPtr);
			}
			Object noticePtr;
			noticePtr = t.getNotice();

			ps.setObject(i++, noticePtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(TbNoticeDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object noticePtr;
				noticePtr = t.getNotice();

			ps.setObject(i++,  noticePtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(TbNoticeDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `tb_notice` (`id`,`notice`) VALUES (?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `tb_notice` (`id`,`notice`) VALUES (?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `tb_notice` (`id`,`notice`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `tb_notice` SET `id`=?,`notice`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `tb_notice` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `tb_notice`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `tb_notice` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `tb_notice` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `tb_notice` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<TbNoticeDO> getRowMapper(){
			return new RowMapper<TbNoticeDO>() {
				@Override
				public TbNoticeDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					TbNoticeDO o=new TbNoticeDO();
					o.id = rs.getInt("id");
					o.notice = rs.getString("notice");
					return o;
				}
			};
		}

		@Override public <C extends TbNoticeDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setNotice(rs.getString("notice"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
