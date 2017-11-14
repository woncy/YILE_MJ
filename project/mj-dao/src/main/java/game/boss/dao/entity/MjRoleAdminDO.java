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

public class MjRoleAdminDO extends EntityObject<MjRoleAdminDO, MjRoleAdminDO.Key>{

	private int id;
	/**角色id*/
	private int roleId;
	/**管理员id*/
	private int adminId;

	public static class Key implements KeyObject<MjRoleAdminDO, MjRoleAdminDO.Key>{
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
			return "MjRoleAdmin[id:"+ id+ "]";
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
				return "MjRoleAdmin[id:"+ id+ "]";
			}
		};
	}




	public MjRoleAdminDO() {
    }

	public MjRoleAdminDO(int roleId,int adminId) {
		this.roleId = roleId;
		this.adminId = adminId;
	}


	public MjRoleAdminDO newInstance(){
		return new MjRoleAdminDO();
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
	 * 角色id
	 **/
	public int getRoleId() {
		return roleId;
	}

	/**
	 * 角色id
	 **/
	public void setRoleId(int roleId) {
		this.roleId = roleId;
		changeProperty("roleId",roleId);
	}

	/**
	 * 管理员id
	 **/
	public int getAdminId() {
		return adminId;
	}

	/**
	 * 管理员id
	 **/
	public void setAdminId(int adminId) {
		this.adminId = adminId;
		changeProperty("adminId",adminId);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "role_id":
            return roleId;
        case "admin_id":
            return adminId;
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
		case "role_id":
			roleId = (int)obj;
			return true;
		case "admin_id":
			adminId = (int)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "MjRoleAdmin[id:"+ id+",roleId:"+ roleId+",adminId:"+ adminId+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<MjRoleAdminDO ,Key>{
		public static final String DB_TABLE_NAME = "mj_role_admin";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String ROLE_ID = "role_id";
		public static final String ADMIN_ID = "admin_id";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("role_id", "roleId", int.class, new TypeReference<Integer>() {});
			super.initProperty("admin_id", "adminId", int.class, new TypeReference<Integer>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `mj_role_admin` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `mj_role_admin` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(MjRoleAdminDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object roleIdPtr;
			roleIdPtr = t.getRoleId();

			ps.setObject(i++, roleIdPtr);
			Object adminIdPtr;
			adminIdPtr = t.getAdminId();

			ps.setObject(i++, adminIdPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(MjRoleAdminDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object roleIdPtr;
				roleIdPtr = t.getRoleId();

			ps.setObject(i++,  roleIdPtr);
			Object adminIdPtr;
				adminIdPtr = t.getAdminId();

			ps.setObject(i++,  adminIdPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(MjRoleAdminDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `mj_role_admin` (`role_id`,`admin_id`) VALUES (?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `mj_role_admin` (`id`,`role_id`,`admin_id`) VALUES (?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `mj_role_admin` (`role_id`,`admin_id`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `mj_role_admin` SET `role_id`=?,`admin_id`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `mj_role_admin` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `mj_role_admin`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `mj_role_admin` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `mj_role_admin` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `mj_role_admin` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<MjRoleAdminDO> getRowMapper(){
			return new RowMapper<MjRoleAdminDO>() {
				@Override
				public MjRoleAdminDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					MjRoleAdminDO o=new MjRoleAdminDO();
					o.id = rs.getInt("id");
					o.roleId = rs.getInt("role_id");
					o.adminId = rs.getInt("admin_id");
					return o;
				}
			};
		}

		@Override public <C extends MjRoleAdminDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setRoleId(rs.getInt("role_id"));
						o.setAdminId(rs.getInt("admin_id"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
