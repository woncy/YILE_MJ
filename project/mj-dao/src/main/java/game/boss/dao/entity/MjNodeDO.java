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

public class MjNodeDO extends EntityObject<MjNodeDO, MjNodeDO.Key>{

	/**权限id*/
	private int nodeId;
	/**对应的模块名*/
	private String moduleName;
	/**控制器名*/
	private String controllerName;
	/**方法名*/
	private String actionName;
	/**权限标题*/
	private String title;
	/**上级权限*/
	private int parentId;

	public static class Key implements KeyObject<MjNodeDO, MjNodeDO.Key>{
    	private int nodeId;

		public Key() {
   		}

		public Key(int nodeId) {
			this.nodeId = nodeId;
		}

		@JsonIgnore
		@Transient
		@Override
		public Object[] getQueryParams() {
			return new Object[]{
				getNodeId()
			};
		}

		public int getNodeId() {
			return nodeId;
		}
		public void setNodeId(int nodeId) {
			this.nodeId = nodeId;
		}

        @Override
        public String toStringKey(){
            return String.valueOf(getNodeId());

        }

		@Override
		public Table getTableInfo() {
			return TABLE_INFO;
		}

		@Override
		public String toString() {
			return "MjNode[nodeId:"+ nodeId+ "]";
		}
	}

	@Override
	public Key getKey() {
		return new Key() {

			public int getNodeId() {
				return nodeId;
			}

			@Override
			public String toString() {
				return "MjNode[nodeId:"+ nodeId+ "]";
			}
		};
	}




	public MjNodeDO() {
    }

	public MjNodeDO(String moduleName,String controllerName,String actionName,String title,int parentId) {
		this.moduleName = moduleName;
		this.controllerName = controllerName;
		this.actionName = actionName;
		this.title = title;
		this.parentId = parentId;
	}


	public MjNodeDO newInstance(){
		return new MjNodeDO();
	}

    public void setKey(Object key){
        this.nodeId = ((Number)key).intValue();
    }

	/**
	 * 权限id
	 **/
	public int getNodeId() {
		return nodeId;
	}

	/**
	 * 权限id
	 **/
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
		changeProperty("nodeId",nodeId);
	}

	/**
	 * 对应的模块名
	 **/
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * 对应的模块名
	 **/
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
		changeProperty("moduleName",moduleName);
	}

	/**
	 * 控制器名
	 **/
	public String getControllerName() {
		return controllerName;
	}

	/**
	 * 控制器名
	 **/
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
		changeProperty("controllerName",controllerName);
	}

	/**
	 * 方法名
	 **/
	public String getActionName() {
		return actionName;
	}

	/**
	 * 方法名
	 **/
	public void setActionName(String actionName) {
		this.actionName = actionName;
		changeProperty("actionName",actionName);
	}

	/**
	 * 权限标题
	 **/
	public String getTitle() {
		return title;
	}

	/**
	 * 权限标题
	 **/
	public void setTitle(String title) {
		this.title = title;
		changeProperty("title",title);
	}

	/**
	 * 上级权限
	 **/
	public int getParentId() {
		return parentId;
	}

	/**
	 * 上级权限
	 **/
	public void setParentId(int parentId) {
		this.parentId = parentId;
		changeProperty("parentId",parentId);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "node_id":
            return nodeId;
        case "module_name":
            return moduleName;
        case "controller_name":
            return controllerName;
        case "action_name":
            return actionName;
        case "title":
            return title;
        case "parent_id":
            return parentId;
        default :
            return null;
        }
    }


	@Override
	public boolean set(String dbName, Object obj) {
		switch(dbName){
		case "node_id":
			nodeId = (int)obj;
			return true;
		case "module_name":
			moduleName = (String)obj;
			return true;
		case "controller_name":
			controllerName = (String)obj;
			return true;
		case "action_name":
			actionName = (String)obj;
			return true;
		case "title":
			title = (String)obj;
			return true;
		case "parent_id":
			parentId = (int)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "MjNode[nodeId:"+ nodeId+",moduleName:"+ (moduleName == null ?"null":moduleName.substring(0, Math.min(moduleName.length(), 64)))+",controllerName:"+ (controllerName == null ?"null":controllerName.substring(0, Math.min(controllerName.length(), 64)))+",actionName:"+ (actionName == null ?"null":actionName.substring(0, Math.min(actionName.length(), 64)))+",title:"+ (title == null ?"null":title.substring(0, Math.min(title.length(), 64)))+",parentId:"+ parentId+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<MjNodeDO ,Key>{
		public static final String DB_TABLE_NAME = "mj_node";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String NODE_ID = "node_id";
		public static final String MODULE_NAME = "module_name";
		public static final String CONTROLLER_NAME = "controller_name";
		public static final String ACTION_NAME = "action_name";
		public static final String TITLE = "title";
		public static final String PARENT_ID = "parent_id";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("node_id", "nodeId", int.class, new TypeReference<Integer>() {});
			super.initProperty("module_name", "moduleName", String.class, new TypeReference<String>() {});
			super.initProperty("controller_name", "controllerName", String.class, new TypeReference<String>() {});
			super.initProperty("action_name", "actionName", String.class, new TypeReference<String>() {});
			super.initProperty("title", "title", String.class, new TypeReference<String>() {});
			super.initProperty("parent_id", "parentId", int.class, new TypeReference<Integer>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `mj_node` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `node_id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `mj_node` WHERE `node_id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(MjNodeDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object nodeIdPtr;
			nodeIdPtr = t.getNodeId();

			Object moduleNamePtr;
			moduleNamePtr = t.getModuleName();

			ps.setObject(i++, moduleNamePtr);
			Object controllerNamePtr;
			controllerNamePtr = t.getControllerName();

			ps.setObject(i++, controllerNamePtr);
			Object actionNamePtr;
			actionNamePtr = t.getActionName();

			ps.setObject(i++, actionNamePtr);
			Object titlePtr;
			titlePtr = t.getTitle();

			ps.setObject(i++, titlePtr);
			Object parentIdPtr;
			parentIdPtr = t.getParentId();

			ps.setObject(i++, parentIdPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(MjNodeDO t, PreparedStatement ps, int i) throws SQLException {
			Object nodeIdPtr;
				nodeIdPtr = t.getNodeId();

			ps.setObject(i++,  nodeIdPtr);
			Object moduleNamePtr;
				moduleNamePtr = t.getModuleName();

			ps.setObject(i++,  moduleNamePtr);
			Object controllerNamePtr;
				controllerNamePtr = t.getControllerName();

			ps.setObject(i++,  controllerNamePtr);
			Object actionNamePtr;
				actionNamePtr = t.getActionName();

			ps.setObject(i++,  actionNamePtr);
			Object titlePtr;
				titlePtr = t.getTitle();

			ps.setObject(i++,  titlePtr);
			Object parentIdPtr;
				parentIdPtr = t.getParentId();

			ps.setObject(i++,  parentIdPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(MjNodeDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getNodeId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getNodeId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `mj_node` (`module_name`,`controller_name`,`action_name`,`title`,`parent_id`) VALUES (?,?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `mj_node` (`node_id`,`module_name`,`controller_name`,`action_name`,`title`,`parent_id`) VALUES (?,?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `mj_node` (`module_name`,`controller_name`,`action_name`,`title`,`parent_id`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `mj_node` SET `module_name`=?,`controller_name`=?,`action_name`=?,`title`=?,`parent_id`=? WHERE `node_id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `mj_node` WHERE `node_id`=? ORDER BY `node_id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `mj_node`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `mj_node` ORDER BY `node_id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `mj_node` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `mj_node` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `node_id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<MjNodeDO> getRowMapper(){
			return new RowMapper<MjNodeDO>() {
				@Override
				public MjNodeDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					MjNodeDO o=new MjNodeDO();
					o.nodeId = rs.getInt("node_id");
					o.moduleName = rs.getString("module_name");
					o.controllerName = rs.getString("controller_name");
					o.actionName = rs.getString("action_name");
					o.title = rs.getString("title");
					o.parentId = rs.getInt("parent_id");
					return o;
				}
			};
		}

		@Override public <C extends MjNodeDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setNodeId(rs.getInt("node_id"));
						o.setModuleName(rs.getString("module_name"));
						o.setControllerName(rs.getString("controller_name"));
						o.setActionName(rs.getString("action_name"));
						o.setTitle(rs.getString("title"));
						o.setParentId(rs.getInt("parent_id"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
