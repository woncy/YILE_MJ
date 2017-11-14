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

public class MjAgentsDO extends EntityObject<MjAgentsDO, MjAgentsDO.Key>{

	private int id;
	/**uid*/
	private int agentId;
	/**上级*/
	private int pid;
	/**公告*/
	private String notice;

	public static class Key implements KeyObject<MjAgentsDO, MjAgentsDO.Key>{
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
			return "MjAgents[id:"+ id+ "]";
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
				return "MjAgents[id:"+ id+ "]";
			}
		};
	}




	public MjAgentsDO() {
    }

	public MjAgentsDO(int agentId,int pid,String notice) {
		this.agentId = agentId;
		this.pid = pid;
		this.notice = notice;
	}


	public MjAgentsDO newInstance(){
		return new MjAgentsDO();
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
	 * uid
	 **/
	public int getAgentId() {
		return agentId;
	}

	/**
	 * uid
	 **/
	public void setAgentId(int agentId) {
		this.agentId = agentId;
		changeProperty("agentId",agentId);
	}

	/**
	 * 上级
	 **/
	public int getPid() {
		return pid;
	}

	/**
	 * 上级
	 **/
	public void setPid(int pid) {
		this.pid = pid;
		changeProperty("pid",pid);
	}

	/**
	 * 公告
	 **/
	public String getNotice() {
		return notice;
	}

	/**
	 * 公告
	 **/
	public void setNotice(String notice) {
		this.notice = notice;
		changeProperty("notice",notice);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "agent_id":
            return agentId;
        case "pid":
            return pid;
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
		case "agent_id":
			agentId = (int)obj;
			return true;
		case "pid":
			pid = (int)obj;
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
		return "MjAgents[id:"+ id+",agentId:"+ agentId+",pid:"+ pid+",notice:"+ (notice == null ?"null":notice.substring(0, Math.min(notice.length(), 64)))+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<MjAgentsDO ,Key>{
		public static final String DB_TABLE_NAME = "mj_agents";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String AGENT_ID = "agent_id";
		public static final String PID = "pid";
		public static final String NOTICE = "notice";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("agent_id", "agentId", int.class, new TypeReference<Integer>() {});
			super.initProperty("pid", "pid", int.class, new TypeReference<Integer>() {});
			super.initProperty("notice", "notice", String.class, new TypeReference<String>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `mj_agents` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `mj_agents` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(MjAgentsDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object agentIdPtr;
			agentIdPtr = t.getAgentId();

			ps.setObject(i++, agentIdPtr);
			Object pidPtr;
			pidPtr = t.getPid();

			ps.setObject(i++, pidPtr);
			Object noticePtr;
			noticePtr = t.getNotice();

			ps.setObject(i++, noticePtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(MjAgentsDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object agentIdPtr;
				agentIdPtr = t.getAgentId();

			ps.setObject(i++,  agentIdPtr);
			Object pidPtr;
				pidPtr = t.getPid();

			ps.setObject(i++,  pidPtr);
			Object noticePtr;
				noticePtr = t.getNotice();

			ps.setObject(i++,  noticePtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(MjAgentsDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `mj_agents` (`agent_id`,`pid`,`notice`) VALUES (?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `mj_agents` (`id`,`agent_id`,`pid`,`notice`) VALUES (?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `mj_agents` (`agent_id`,`pid`,`notice`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `mj_agents` SET `agent_id`=?,`pid`=?,`notice`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `mj_agents` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `mj_agents`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `mj_agents` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `mj_agents` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `mj_agents` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<MjAgentsDO> getRowMapper(){
			return new RowMapper<MjAgentsDO>() {
				@Override
				public MjAgentsDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					MjAgentsDO o=new MjAgentsDO();
					o.id = rs.getInt("id");
					o.agentId = rs.getInt("agent_id");
					o.pid = rs.getInt("pid");
					o.notice = rs.getString("notice");
					return o;
				}
			};
		}

		@Override public <C extends MjAgentsDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setAgentId(rs.getInt("agent_id"));
						o.setPid(rs.getInt("pid"));
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
