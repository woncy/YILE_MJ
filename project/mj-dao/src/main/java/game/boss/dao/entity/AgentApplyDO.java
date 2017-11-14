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

public class AgentApplyDO extends EntityObject<AgentApplyDO, AgentApplyDO.Key>{

	private int id;
	/**代理商id*/
	private int agentId;
	/**充值数量*/
	private int count;
	private java.util.Date date;
	/**申请状态 0为未通过，1为已通过*/
	private byte status;

	public static class Key implements KeyObject<AgentApplyDO, AgentApplyDO.Key>{
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
			return "AgentApply[id:"+ id+ "]";
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
				return "AgentApply[id:"+ id+ "]";
			}
		};
	}




	public AgentApplyDO() {
    }

	public AgentApplyDO(int agentId,int count,java.util.Date date,byte status) {
		this.agentId = agentId;
		this.count = count;
		this.date = date;
		this.status = status;
	}


	public AgentApplyDO newInstance(){
		return new AgentApplyDO();
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
	 * 代理商id
	 **/
	public int getAgentId() {
		return agentId;
	}

	/**
	 * 代理商id
	 **/
	public void setAgentId(int agentId) {
		this.agentId = agentId;
		changeProperty("agentId",agentId);
	}

	/**
	 * 充值数量
	 **/
	public int getCount() {
		return count;
	}

	/**
	 * 充值数量
	 **/
	public void setCount(int count) {
		this.count = count;
		changeProperty("count",count);
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
		changeProperty("date",date);
	}

	/**
	 * 申请状态 0为未通过，1为已通过
	 **/
	public byte getStatus() {
		return status;
	}

	/**
	 * 申请状态 0为未通过，1为已通过
	 **/
	public void setStatus(byte status) {
		this.status = status;
		changeProperty("status",status);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "agent_id":
            return agentId;
        case "count":
            return count;
        case "date":
            return date;
        case "status":
            return status;
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
		case "agent_id":
			agentId = (int)obj;
			return true;
		case "count":
			count = (int)obj;
			return true;
		case "date":
			date = (java.util.Date)obj;
			return true;
		case "status":
			status = (byte)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "AgentApply[id:"+ id+",agentId:"+ agentId+",count:"+ count+",date:"+ date+",status:"+ status+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<AgentApplyDO ,Key>{
		public static final String DB_TABLE_NAME = "agent_apply";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String AGENT_ID = "agent_id";
		public static final String COUNT = "count";
		public static final String DATE = "date";
		public static final String STATUS = "status";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("agent_id", "agentId", int.class, new TypeReference<Integer>() {});
			super.initProperty("count", "count", int.class, new TypeReference<Integer>() {});
			super.initProperty("date", "date", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("status", "status", byte.class, new TypeReference<Byte>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `agent_apply` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `agent_apply` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(AgentApplyDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object agentIdPtr;
			agentIdPtr = t.getAgentId();

			ps.setObject(i++, agentIdPtr);
			Object countPtr;
			countPtr = t.getCount();

			ps.setObject(i++, countPtr);
			Object datePtr;
			datePtr = t.getDate();

			ps.setObject(i++, datePtr);
			Object statusPtr;
			statusPtr = t.getStatus();

			ps.setObject(i++, statusPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(AgentApplyDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object agentIdPtr;
				agentIdPtr = t.getAgentId();

			ps.setObject(i++,  agentIdPtr);
			Object countPtr;
				countPtr = t.getCount();

			ps.setObject(i++,  countPtr);
			Object datePtr;
				datePtr = t.getDate();

			ps.setObject(i++,  datePtr);
			Object statusPtr;
				statusPtr = t.getStatus();

			ps.setObject(i++,  statusPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(AgentApplyDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `agent_apply` (`agent_id`,`count`,`date`,`status`) VALUES (?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `agent_apply` (`id`,`agent_id`,`count`,`date`,`status`) VALUES (?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `agent_apply` (`agent_id`,`count`,`date`,`status`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `agent_apply` SET `agent_id`=?,`count`=?,`date`=?,`status`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `agent_apply` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `agent_apply`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `agent_apply` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `agent_apply` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `agent_apply` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<AgentApplyDO> getRowMapper(){
			return new RowMapper<AgentApplyDO>() {
				@Override
				public AgentApplyDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					AgentApplyDO o=new AgentApplyDO();
					o.id = rs.getInt("id");
					o.agentId = rs.getInt("agent_id");
					o.count = rs.getInt("count");
					o.date = rs.getTimestamp("date");
					o.status = rs.getByte("status");
					return o;
				}
			};
		}

		@Override public <C extends AgentApplyDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setAgentId(rs.getInt("agent_id"));
						o.setCount(rs.getInt("count"));
						o.setDate(rs.getTimestamp("date"));
						o.setStatus(rs.getByte("status"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
