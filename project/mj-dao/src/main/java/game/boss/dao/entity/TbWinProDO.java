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

public class TbWinProDO extends EntityObject<TbWinProDO, TbWinProDO.Key>{

	private int id;
	private int userId;
	private int winPro;

	public static class Key implements KeyObject<TbWinProDO, TbWinProDO.Key>{
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
			return "TbWinPro[id:"+ id+ "]";
		}
	}

	@Override
	public Key getKey() {
		return new Key() {

			public int getId() {
				return id;
			}

			public void setId(int id) {
				TbWinProDO.this.id  = id;
				TbWinProDO.this.changeProperty("id",id);
			}

			@Override
			public String toString() {
				return "TbWinPro[id:"+ id+ "]";
			}
		};
	}




	public TbWinProDO() {
    }

	public TbWinProDO(int id,int userId,int winPro) {
		this.id = id;
		this.userId = userId;
		this.winPro = winPro;
	}


	public TbWinProDO newInstance(){
		return new TbWinProDO();
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
		changeProperty("userId",userId);
	}

	public int getWinPro() {
		return winPro;
	}

	public void setWinPro(int winPro) {
		this.winPro = winPro;
		changeProperty("winPro",winPro);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "user_id":
            return userId;
        case "win_pro":
            return winPro;
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
		case "user_id":
			userId = (int)obj;
			return true;
		case "win_pro":
			winPro = (int)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "TbWinPro[id:"+ id+",userId:"+ userId+",winPro:"+ winPro+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<TbWinProDO ,Key>{
		public static final String DB_TABLE_NAME = "tb_win_pro";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String USER_ID = "user_id";
		public static final String WIN_PRO = "win_pro";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("user_id", "userId", int.class, new TypeReference<Integer>() {});
			super.initProperty("win_pro", "winPro", int.class, new TypeReference<Integer>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `tb_win_pro` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `tb_win_pro` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(TbWinProDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			if(isSetUnique){
				ps.setObject(i++, idPtr);
			}
			Object userIdPtr;
			userIdPtr = t.getUserId();

			ps.setObject(i++, userIdPtr);
			Object winProPtr;
			winProPtr = t.getWinPro();

			ps.setObject(i++, winProPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(TbWinProDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object userIdPtr;
				userIdPtr = t.getUserId();

			ps.setObject(i++,  userIdPtr);
			Object winProPtr;
				winProPtr = t.getWinPro();

			ps.setObject(i++,  winProPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(TbWinProDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `tb_win_pro` (`id`,`user_id`,`win_pro`) VALUES (?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `tb_win_pro` (`id`,`user_id`,`win_pro`) VALUES (?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `tb_win_pro` (`id`,`user_id`,`win_pro`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `tb_win_pro` SET `id`=?,`user_id`=?,`win_pro`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `tb_win_pro` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `tb_win_pro`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `tb_win_pro` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `tb_win_pro` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `tb_win_pro` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<TbWinProDO> getRowMapper(){
			return new RowMapper<TbWinProDO>() {
				@Override
				public TbWinProDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					TbWinProDO o=new TbWinProDO();
					o.id = rs.getInt("id");
					o.userId = rs.getInt("user_id");
					o.winPro = rs.getInt("win_pro");
					return o;
				}
			};
		}

		@Override public <C extends TbWinProDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setUserId(rs.getInt("user_id"));
						o.setWinPro(rs.getInt("win_pro"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
