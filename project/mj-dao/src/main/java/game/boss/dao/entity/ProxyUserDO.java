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

public class ProxyUserDO extends EntityObject<ProxyUserDO, ProxyUserDO.Key>{

	private int id;
	/**代理手机号*/
	private String proxyPhone;
	/**代理游戏id*/
	private String proxyGameid;
	/**代理人密码*/
	private String proxyPassword;
	/**1正在处理，2已通过，0驳回*/
	private String proxyStatus;
	/**创建时间*/
	private java.util.Date createDate;
	/**修改时间*/
	private java.util.Date updateDate;
	/**真实姓名*/
	private String proxyTruename;
	/**微信号*/
	private String proxyWeixin;

	public static class Key implements KeyObject<ProxyUserDO, ProxyUserDO.Key>{
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
			return "ProxyUser[id:"+ id+ "]";
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
				return "ProxyUser[id:"+ id+ "]";
			}
		};
	}




	public ProxyUserDO() {
    }

	public ProxyUserDO(String proxyPhone,String proxyGameid,String proxyPassword,String proxyStatus,java.util.Date createDate,java.util.Date updateDate,String proxyTruename,String proxyWeixin) {
		this.proxyPhone = proxyPhone;
		this.proxyGameid = proxyGameid;
		this.proxyPassword = proxyPassword;
		this.proxyStatus = proxyStatus;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.proxyTruename = proxyTruename;
		this.proxyWeixin = proxyWeixin;
	}


	public ProxyUserDO newInstance(){
		return new ProxyUserDO();
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
	 * 代理手机号
	 **/
	public String getProxyPhone() {
		return proxyPhone;
	}

	/**
	 * 代理手机号
	 **/
	public void setProxyPhone(String proxyPhone) {
		this.proxyPhone = proxyPhone;
		changeProperty("proxyPhone",proxyPhone);
	}

	/**
	 * 代理游戏id
	 **/
	public String getProxyGameid() {
		return proxyGameid;
	}

	/**
	 * 代理游戏id
	 **/
	public void setProxyGameid(String proxyGameid) {
		this.proxyGameid = proxyGameid;
		changeProperty("proxyGameid",proxyGameid);
	}

	/**
	 * 代理人密码
	 **/
	public String getProxyPassword() {
		return proxyPassword;
	}

	/**
	 * 代理人密码
	 **/
	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
		changeProperty("proxyPassword",proxyPassword);
	}

	/**
	 * 1正在处理，2已通过，0驳回
	 **/
	public String getProxyStatus() {
		return proxyStatus;
	}

	/**
	 * 1正在处理，2已通过，0驳回
	 **/
	public void setProxyStatus(String proxyStatus) {
		this.proxyStatus = proxyStatus;
		changeProperty("proxyStatus",proxyStatus);
	}

	/**
	 * 创建时间
	 **/
	public java.util.Date getCreateDate() {
		return createDate;
	}

	/**
	 * 创建时间
	 **/
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
		changeProperty("createDate",createDate);
	}

	/**
	 * 修改时间
	 **/
	public java.util.Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * 修改时间
	 **/
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
		changeProperty("updateDate",updateDate);
	}

	/**
	 * 真实姓名
	 **/
	public String getProxyTruename() {
		return proxyTruename;
	}

	/**
	 * 真实姓名
	 **/
	public void setProxyTruename(String proxyTruename) {
		this.proxyTruename = proxyTruename;
		changeProperty("proxyTruename",proxyTruename);
	}

	/**
	 * 微信号
	 **/
	public String getProxyWeixin() {
		return proxyWeixin;
	}

	/**
	 * 微信号
	 **/
	public void setProxyWeixin(String proxyWeixin) {
		this.proxyWeixin = proxyWeixin;
		changeProperty("proxyWeixin",proxyWeixin);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "proxy_phone":
            return proxyPhone;
        case "proxy_gameid":
            return proxyGameid;
        case "proxy_password":
            return proxyPassword;
        case "proxy_status":
            return proxyStatus;
        case "create_date":
            return createDate;
        case "update_date":
            return updateDate;
        case "proxy_truename":
            return proxyTruename;
        case "proxy_weixin":
            return proxyWeixin;
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
		case "proxy_phone":
			proxyPhone = (String)obj;
			return true;
		case "proxy_gameid":
			proxyGameid = (String)obj;
			return true;
		case "proxy_password":
			proxyPassword = (String)obj;
			return true;
		case "proxy_status":
			proxyStatus = (String)obj;
			return true;
		case "create_date":
			createDate = (java.util.Date)obj;
			return true;
		case "update_date":
			updateDate = (java.util.Date)obj;
			return true;
		case "proxy_truename":
			proxyTruename = (String)obj;
			return true;
		case "proxy_weixin":
			proxyWeixin = (String)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "ProxyUser[id:"+ id+",proxyPhone:"+ (proxyPhone == null ?"null":proxyPhone.substring(0, Math.min(proxyPhone.length(), 64)))+",proxyGameid:"+ (proxyGameid == null ?"null":proxyGameid.substring(0, Math.min(proxyGameid.length(), 64)))+",proxyPassword:"+ (proxyPassword == null ?"null":proxyPassword.substring(0, Math.min(proxyPassword.length(), 64)))+",proxyStatus:"+ (proxyStatus == null ?"null":proxyStatus.substring(0, Math.min(proxyStatus.length(), 64)))+",createDate:"+ createDate+",updateDate:"+ updateDate+",proxyTruename:"+ (proxyTruename == null ?"null":proxyTruename.substring(0, Math.min(proxyTruename.length(), 64)))+",proxyWeixin:"+ (proxyWeixin == null ?"null":proxyWeixin.substring(0, Math.min(proxyWeixin.length(), 64)))+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<ProxyUserDO ,Key>{
		public static final String DB_TABLE_NAME = "proxy_user";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String PROXY_PHONE = "proxy_phone";
		public static final String PROXY_GAMEID = "proxy_gameid";
		public static final String PROXY_PASSWORD = "proxy_password";
		public static final String PROXY_STATUS = "proxy_status";
		public static final String CREATE_DATE = "create_date";
		public static final String UPDATE_DATE = "update_date";
		public static final String PROXY_TRUENAME = "proxy_truename";
		public static final String PROXY_WEIXIN = "proxy_weixin";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("proxy_phone", "proxyPhone", String.class, new TypeReference<String>() {});
			super.initProperty("proxy_gameid", "proxyGameid", String.class, new TypeReference<String>() {});
			super.initProperty("proxy_password", "proxyPassword", String.class, new TypeReference<String>() {});
			super.initProperty("proxy_status", "proxyStatus", String.class, new TypeReference<String>() {});
			super.initProperty("create_date", "createDate", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("update_date", "updateDate", java.util.Date.class, new TypeReference<java.util.Date>() {});
			super.initProperty("proxy_truename", "proxyTruename", String.class, new TypeReference<String>() {});
			super.initProperty("proxy_weixin", "proxyWeixin", String.class, new TypeReference<String>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `proxy_user` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `proxy_user` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(ProxyUserDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object proxyPhonePtr;
			proxyPhonePtr = t.getProxyPhone();

			ps.setObject(i++, proxyPhonePtr);
			Object proxyGameidPtr;
			proxyGameidPtr = t.getProxyGameid();

			ps.setObject(i++, proxyGameidPtr);
			Object proxyPasswordPtr;
			proxyPasswordPtr = t.getProxyPassword();

			ps.setObject(i++, proxyPasswordPtr);
			Object proxyStatusPtr;
			proxyStatusPtr = t.getProxyStatus();

			ps.setObject(i++, proxyStatusPtr);
			Object createDatePtr;
			createDatePtr = t.getCreateDate();

			ps.setObject(i++, createDatePtr);
			Object updateDatePtr;
			updateDatePtr = t.getUpdateDate();

			ps.setObject(i++, updateDatePtr);
			Object proxyTruenamePtr;
			proxyTruenamePtr = t.getProxyTruename();

			ps.setObject(i++, proxyTruenamePtr);
			Object proxyWeixinPtr;
			proxyWeixinPtr = t.getProxyWeixin();

			ps.setObject(i++, proxyWeixinPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(ProxyUserDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object proxyPhonePtr;
				proxyPhonePtr = t.getProxyPhone();

			ps.setObject(i++,  proxyPhonePtr);
			Object proxyGameidPtr;
				proxyGameidPtr = t.getProxyGameid();

			ps.setObject(i++,  proxyGameidPtr);
			Object proxyPasswordPtr;
				proxyPasswordPtr = t.getProxyPassword();

			ps.setObject(i++,  proxyPasswordPtr);
			Object proxyStatusPtr;
				proxyStatusPtr = t.getProxyStatus();

			ps.setObject(i++,  proxyStatusPtr);
			Object createDatePtr;
				createDatePtr = t.getCreateDate();

			ps.setObject(i++,  createDatePtr);
			Object updateDatePtr;
				updateDatePtr = t.getUpdateDate();

			ps.setObject(i++,  updateDatePtr);
			Object proxyTruenamePtr;
				proxyTruenamePtr = t.getProxyTruename();

			ps.setObject(i++,  proxyTruenamePtr);
			Object proxyWeixinPtr;
				proxyWeixinPtr = t.getProxyWeixin();

			ps.setObject(i++,  proxyWeixinPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(ProxyUserDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `proxy_user` (`proxy_phone`,`proxy_gameid`,`proxy_password`,`proxy_status`,`create_date`,`update_date`,`proxy_truename`,`proxy_weixin`) VALUES (?,?,?,?,?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `proxy_user` (`id`,`proxy_phone`,`proxy_gameid`,`proxy_password`,`proxy_status`,`create_date`,`update_date`,`proxy_truename`,`proxy_weixin`) VALUES (?,?,?,?,?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `proxy_user` (`proxy_phone`,`proxy_gameid`,`proxy_password`,`proxy_status`,`create_date`,`update_date`,`proxy_truename`,`proxy_weixin`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?,?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `proxy_user` SET `proxy_phone`=?,`proxy_gameid`=?,`proxy_password`=?,`proxy_status`=?,`create_date`=?,`update_date`=?,`proxy_truename`=?,`proxy_weixin`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `proxy_user` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `proxy_user`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `proxy_user` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `proxy_user` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `proxy_user` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<ProxyUserDO> getRowMapper(){
			return new RowMapper<ProxyUserDO>() {
				@Override
				public ProxyUserDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					ProxyUserDO o=new ProxyUserDO();
					o.id = rs.getInt("id");
					o.proxyPhone = rs.getString("proxy_phone");
					o.proxyGameid = rs.getString("proxy_gameid");
					o.proxyPassword = rs.getString("proxy_password");
					o.proxyStatus = rs.getString("proxy_status");
					o.createDate = rs.getTimestamp("create_date");
					o.updateDate = rs.getTimestamp("update_date");
					o.proxyTruename = rs.getString("proxy_truename");
					o.proxyWeixin = rs.getString("proxy_weixin");
					return o;
				}
			};
		}

		@Override public <C extends ProxyUserDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setProxyPhone(rs.getString("proxy_phone"));
						o.setProxyGameid(rs.getString("proxy_gameid"));
						o.setProxyPassword(rs.getString("proxy_password"));
						o.setProxyStatus(rs.getString("proxy_status"));
						o.setCreateDate(rs.getTimestamp("create_date"));
						o.setUpdateDate(rs.getTimestamp("update_date"));
						o.setProxyTruename(rs.getString("proxy_truename"));
						o.setProxyWeixin(rs.getString("proxy_weixin"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
