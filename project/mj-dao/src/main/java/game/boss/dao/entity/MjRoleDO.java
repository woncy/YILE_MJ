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

public class MjRoleDO extends EntityObject<MjRoleDO, MjRoleDO.Key>{

	/**角色id*/
	private int roleId;
	/**角色名称*/
	private String roleName;

	public static class Key implements KeyObject<MjRoleDO, MjRoleDO.Key>{
    	private int roleId;

		public Key() {
   		}

		public Key(int roleId) {
			this.roleId = roleId;
		}

		@JsonIgnore
		@Transient
		@Override
		public Object[] getQueryParams() {
			return new Object[]{
				getRoleId()
			};
		}

		public int getRoleId() {
			return roleId;
		}
		public void setRoleId(int roleId) {
			this.roleId = roleId;
		}

        @Override
        public String toStringKey(){
            return String.valueOf(getRoleId());

        }

		@Override
		public Table getTableInfo() {
			return TABLE_INFO;
		}

		@Override
		public String toString() {
			return "MjRole[roleId:"+ roleId+ "]";
		}
	}

	@Override
	public Key getKey() {
		return new Key() {

			public int getRoleId() {
				return roleId;
			}

			@Override
			public String toString() {
				return "MjRole[roleId:"+ roleId+ "]";
			}
		};
	}




	public MjRoleDO() {
    }

	public MjRoleDO(String roleName) {
		this.roleName = roleName;
	}


	public MjRoleDO newInstance(){
		return new MjRoleDO();
	}

    public void setKey(Object key){
        this.roleId = ((Number)key).intValue();
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
	 * 角色名称
	 **/
	public String getRoleName() {
		return roleName;
	}

	/**
	 * 角色名称
	 **/
	public void setRoleName(String roleName) {
		this.roleName = roleName;
		changeProperty("roleName",roleName);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "role_id":
            return roleId;
        case "role_name":
            return roleName;
        default :
            return null;
        }
    }


	@Override
	public boolean set(String dbName, Object obj) {
		switch(dbName){
		case "role_id":
			roleId = (int)obj;
			return true;
		case "role_name":
			roleName = (String)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "MjRole[roleId:"+ roleId+",roleName:"+ (roleName == null ?"null":roleName.substring(0, Math.min(roleName.length(), 64)))+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<MjRoleDO ,Key>{
		public static final String DB_TABLE_NAME = "mj_role";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ROLE_ID = "role_id";
		public static final String ROLE_NAME = "role_name";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("role_id", "roleId", int.class, new TypeReference<Integer>() {});
			super.initProperty("role_name", "roleName", String.class, new TypeReference<String>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `mj_role` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `role_id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `mj_role` WHERE `role_id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(MjRoleDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object roleIdPtr;
			roleIdPtr = t.getRoleId();

			Object roleNamePtr;
			roleNamePtr = t.getRoleName();

			ps.setObject(i++, roleNamePtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(MjRoleDO t, PreparedStatement ps, int i) throws SQLException {
			Object roleIdPtr;
				roleIdPtr = t.getRoleId();

			ps.setObject(i++,  roleIdPtr);
			Object roleNamePtr;
				roleNamePtr = t.getRoleName();

			ps.setObject(i++,  roleNamePtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(MjRoleDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getRoleId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getRoleId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `mj_role` (`role_name`) VALUES (?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `mj_role` (`role_id`,`role_name`) VALUES (?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `mj_role` (`role_name`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `mj_role` SET `role_name`=? WHERE `role_id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `mj_role` WHERE `role_id`=? ORDER BY `role_id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `mj_role`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `mj_role` ORDER BY `role_id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `mj_role` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `mj_role` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `role_id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<MjRoleDO> getRowMapper(){
			return new RowMapper<MjRoleDO>() {
				@Override
				public MjRoleDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					MjRoleDO o=new MjRoleDO();
					o.roleId = rs.getInt("role_id");
					o.roleName = rs.getString("role_name");
					return o;
				}
			};
		}

		@Override public <C extends MjRoleDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setRoleId(rs.getInt("role_id"));
						o.setRoleName(rs.getString("role_name"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
