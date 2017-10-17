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

public class MjAdminDO extends EntityObject<MjAdminDO, MjAdminDO.Key>{

	private int id;
	/**用户名*/
	private String name;
	/**密码*/
	private String password;
	/**登录token*/
	private String token;
	private String ip;
	private java.util.Date date;

	public static class Key implements KeyObject<MjAdminDO, MjAdminDO.Key>{
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
			return "MjAdmin[id:"+ id+ "]";
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
				return "MjAdmin[id:"+ id+ "]";
			}
		};
	}




	public MjAdminDO() {
    }

	public MjAdminDO(String name,String password,String token,String ip,java.util.Date date) {
		this.name = name;
		this.password = password;
		this.token = token;
		this.ip = ip;
		this.date = date;
	}


	public MjAdminDO newInstance(){
		return new MjAdminDO();
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
	 * 用户名
	 **/
	public String getName() {
		return name;
	}

	/**
	 * 用户名
	 **/
	public void setName(String name) {
		this.name = name;
		changeProperty("name",name);
	}

	/**
	 * 密码
	 **/
	public String getPassword() {
		return password;
	}

	/**
	 * 密码
	 **/
	public void setPassword(String password) {
		this.password = password;
		changeProperty("password",password);
	}

	/**
	 * 登录token
	 **/
	public String getToken() {
		return token;
	}

	/**
	 * 登录token
	 **/
	public void setToken(String token) {
		this.token = token;
		changeProperty("token",token);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
		changeProperty("ip",ip);
	}

	public java.util.Date getDate() {
		return date;
	}

	public void setDate(java.util.Date date) {
		this.date = date;
		changeProperty("date",date);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "name":
            return name;
        case "password":
            return password;
        case "token":
            return token;
        case "ip":
            return ip;
        case "date":
            return date;
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
		case "name":
			name = (String)obj;
			return true;
		case "password":
			password = (String)obj;
			return true;
		case "token":
			token = (String)obj;
			return true;
		case "ip":
			ip = (String)obj;
			return true;
		case "date":
			date = (java.util.Date)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "MjAdmin[id:"+ id+",name:"+ (name == null ?"null":name.substring(0, Math.min(name.length(), 64)))+",password:"+ (password == null ?"null":password.substring(0, Math.min(password.length(), 64)))+",token:"+ (token == null ?"null":token.substring(0, Math.min(token.length(), 64)))+",ip:"+ (ip == null ?"null":ip.substring(0, Math.min(ip.length(), 64)))+",date:"+ date+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<MjAdminDO ,Key>{
		public static final String DB_TABLE_NAME = "mj_admin";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String NAME = "name";
		public static final String PASSWORD = "password";
		public static final String TOKEN = "token";
		public static final String IP = "ip";
		public static final String DATE = "date";

        public static final String UNIQUE_NAME = "name";
        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
		    uniqueMap.put("name", new UniqueInfo("name", "name"));
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("name", "name", String.class, new TypeReference<String>() {});
			super.initProperty("password", "password", String.class, new TypeReference<String>() {});
			super.initProperty("token", "token", String.class, new TypeReference<String>() {});
			super.initProperty("ip", "ip", String.class, new TypeReference<String>() {});
			super.initProperty("date", "date", java.util.Date.class, new TypeReference<java.util.Date>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `mj_admin` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `mj_admin` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(MjAdminDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object namePtr;
			namePtr = t.getName();

			if(isSetUnique){
				ps.setObject(i++, namePtr);
			}
			Object passwordPtr;
			passwordPtr = t.getPassword();

			ps.setObject(i++, passwordPtr);
			Object tokenPtr;
			tokenPtr = t.getToken();

			ps.setObject(i++, tokenPtr);
			Object ipPtr;
			ipPtr = t.getIp();

			ps.setObject(i++, ipPtr);
			Object datePtr;
			datePtr = t.getDate();

			ps.setObject(i++, datePtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(MjAdminDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object namePtr;
				namePtr = t.getName();

			ps.setObject(i++,  namePtr);
			Object passwordPtr;
				passwordPtr = t.getPassword();

			ps.setObject(i++,  passwordPtr);
			Object tokenPtr;
				tokenPtr = t.getToken();

			ps.setObject(i++,  tokenPtr);
			Object ipPtr;
				ipPtr = t.getIp();

			ps.setObject(i++,  ipPtr);
			Object datePtr;
				datePtr = t.getDate();

			ps.setObject(i++,  datePtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(MjAdminDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `mj_admin` (`name`,`password`,`token`,`ip`,`date`) VALUES (?,?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `mj_admin` (`id`,`name`,`password`,`token`,`ip`,`date`) VALUES (?,?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `mj_admin` (`name`,`password`,`token`,`ip`,`date`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `mj_admin` SET `name`=?,`password`=?,`token`=?,`ip`=?,`date`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `mj_admin` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `mj_admin`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `mj_admin` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `mj_admin` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `mj_admin` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<MjAdminDO> getRowMapper(){
			return new RowMapper<MjAdminDO>() {
				@Override
				public MjAdminDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					MjAdminDO o=new MjAdminDO();
					o.id = rs.getInt("id");
					o.name = rs.getString("name");
					o.password = rs.getString("password");
					o.token = rs.getString("token");
					o.ip = rs.getString("ip");
					o.date = rs.getTimestamp("date");
					return o;
				}
			};
		}

		@Override public <C extends MjAdminDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setName(rs.getString("name"));
						o.setPassword(rs.getString("password"));
						o.setToken(rs.getString("token"));
						o.setIp(rs.getString("ip"));
						o.setDate(rs.getTimestamp("date"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
