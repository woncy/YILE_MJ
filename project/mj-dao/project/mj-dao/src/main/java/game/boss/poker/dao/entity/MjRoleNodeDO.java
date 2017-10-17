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

public class MjRoleNodeDO extends EntityObject<MjRoleNodeDO, MjRoleNodeDO.Key>{

	private int id;
	/**角色id*/
	private Integer roleId;
	/**权限id*/
	private Integer nodeId;

	public static class Key implements KeyObject<MjRoleNodeDO, MjRoleNodeDO.Key>{
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
			return "MjRoleNode[id:"+ id+ "]";
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
				return "MjRoleNode[id:"+ id+ "]";
			}
		};
	}




	public MjRoleNodeDO() {
    }

	public MjRoleNodeDO(Integer roleId,Integer nodeId) {
		this.roleId = roleId;
		this.nodeId = nodeId;
	}


	public MjRoleNodeDO newInstance(){
		return new MjRoleNodeDO();
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
	public Integer getRoleId() {
		return roleId;
	}

	/**
	 * 角色id
	 **/
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
		changeProperty("roleId",roleId);
	}

	/**
	 * 权限id
	 **/
	public Integer getNodeId() {
		return nodeId;
	}

	/**
	 * 权限id
	 **/
	public void setNodeId(Integer nodeId) {
		this.nodeId = nodeId;
		changeProperty("nodeId",nodeId);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "role_id":
            return roleId;
        case "node_id":
            return nodeId;
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
			roleId = (Integer)obj;
			return true;
		case "node_id":
			nodeId = (Integer)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "MjRoleNode[id:"+ id+",roleId:"+ roleId+",nodeId:"+ nodeId+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<MjRoleNodeDO ,Key>{
		public static final String DB_TABLE_NAME = "mj_role_node";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String ROLE_ID = "role_id";
		public static final String NODE_ID = "node_id";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("role_id", "roleId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("node_id", "nodeId", Integer.class, new TypeReference<Integer>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `mj_role_node` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `mj_role_node` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(MjRoleNodeDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object roleIdPtr;
			roleIdPtr = t.getRoleId();

			ps.setObject(i++, roleIdPtr);
			Object nodeIdPtr;
			nodeIdPtr = t.getNodeId();

			ps.setObject(i++, nodeIdPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(MjRoleNodeDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object roleIdPtr;
				roleIdPtr = t.getRoleId();

			ps.setObject(i++,  roleIdPtr);
			Object nodeIdPtr;
				nodeIdPtr = t.getNodeId();

			ps.setObject(i++,  nodeIdPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(MjRoleNodeDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `mj_role_node` (`role_id`,`node_id`) VALUES (?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `mj_role_node` (`id`,`role_id`,`node_id`) VALUES (?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `mj_role_node` (`role_id`,`node_id`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `mj_role_node` SET `role_id`=?,`node_id`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `mj_role_node` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `mj_role_node`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `mj_role_node` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `mj_role_node` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `mj_role_node` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<MjRoleNodeDO> getRowMapper(){
			return new RowMapper<MjRoleNodeDO>() {
				@Override
				public MjRoleNodeDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					MjRoleNodeDO o=new MjRoleNodeDO();
					o.id = rs.getInt("id");
					o.roleId = rs.getInt("role_id");
					o.nodeId = rs.getInt("node_id");
					return o;
				}
			};
		}

		@Override public <C extends MjRoleNodeDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setRoleId(rs.getInt("role_id"));
						o.setNodeId(rs.getInt("node_id"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
