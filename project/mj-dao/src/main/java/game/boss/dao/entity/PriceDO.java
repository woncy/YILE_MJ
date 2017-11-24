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

public class PriceDO extends EntityObject<PriceDO, PriceDO.Key>{

	private int id;
	/**代理商价格*/
	private Integer price;
	/**用户类型，0为普通用户，1为一级代理，2为二级代理，，，*/
	private Integer level;

	public static class Key implements KeyObject<PriceDO, PriceDO.Key>{
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
			return "Price[id:"+ id+ "]";
		}
	}

	@Override
	public Key getKey() {
		return new Key() {

			public int getId() {
				return id;
			}

			public void setId(int id) {
				PriceDO.this.id  = id;
				PriceDO.this.changeProperty("id",id);
			}

			@Override
			public String toString() {
				return "Price[id:"+ id+ "]";
			}
		};
	}




	public PriceDO() {
    }

	public PriceDO(int id,Integer price,Integer level) {
		this.id = id;
		this.price = price;
		this.level = level;
	}


	public PriceDO newInstance(){
		return new PriceDO();
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
	 * 代理商价格
	 **/
	public Integer getPrice() {
		return price;
	}

	/**
	 * 代理商价格
	 **/
	public void setPrice(Integer price) {
		this.price = price;
		changeProperty("price",price);
	}

	/**
	 * 用户类型，0为普通用户，1为一级代理，2为二级代理，，，
	 **/
	public Integer getLevel() {
		return level;
	}

	/**
	 * 用户类型，0为普通用户，1为一级代理，2为二级代理，，，
	 **/
	public void setLevel(Integer level) {
		this.level = level;
		changeProperty("level",level);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "price":
            return price;
        case "level":
            return level;
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
		case "price":
			price = (Integer)obj;
			return true;
		case "level":
			level = (Integer)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "Price[id:"+ id+",price:"+ price+",level:"+ level+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<PriceDO ,Key>{
		public static final String DB_TABLE_NAME = "price";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String PRICE = "price";
		public static final String LEVEL = "level";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("price", "price", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("level", "level", Integer.class, new TypeReference<Integer>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `price` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `price` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(PriceDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			if(isSetUnique){
				ps.setObject(i++, idPtr);
			}
			Object pricePtr;
			pricePtr = t.getPrice();

			ps.setObject(i++, pricePtr);
			Object levelPtr;
			levelPtr = t.getLevel();

			ps.setObject(i++, levelPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(PriceDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object pricePtr;
				pricePtr = t.getPrice();

			ps.setObject(i++,  pricePtr);
			Object levelPtr;
				levelPtr = t.getLevel();

			ps.setObject(i++,  levelPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(PriceDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `price` (`id`,`price`,`level`) VALUES (?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `price` (`id`,`price`,`level`) VALUES (?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `price` (`id`,`price`,`level`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `price` SET `id`=?,`price`=?,`level`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `price` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `price`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `price` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `price` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `price` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<PriceDO> getRowMapper(){
			return new RowMapper<PriceDO>() {
				@Override
				public PriceDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					PriceDO o=new PriceDO();
					o.id = rs.getInt("id");
					o.price = rs.getInt("price");
					o.level = rs.getInt("level");
					return o;
				}
			};
		}

		@Override public <C extends PriceDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setPrice(rs.getInt("price"));
						o.setLevel(rs.getInt("level"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
