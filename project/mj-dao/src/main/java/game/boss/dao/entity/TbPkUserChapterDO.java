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

public class TbPkUserChapterDO extends EntityObject<TbPkUserChapterDO, TbPkUserChapterDO.Key>{

	private int id;
	/**剩余的手牌*/
	private String pais;
	/**本局得分情况*/
	private Integer score;
	/**用户的id*/
	private Integer userId;
	/**牌局的id*/
	private Integer chapterId;
	/**用户在牌局中的位置*/
	private Integer locationIndex;
	/**赢的人的id，没有人赢的时候为-1*/
	private Integer winId;
	/**房炮人的id,没有放炮时为-1*/
	private Integer fangpaoId;
	/**赢的类型，*/
	private Integer wintype;
	/**打出去的手牌*/
	private String outpais;

	public static class Key implements KeyObject<TbPkUserChapterDO, TbPkUserChapterDO.Key>{
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
			return "TbPkUserChapter[id:"+ id+ "]";
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
				return "TbPkUserChapter[id:"+ id+ "]";
			}
		};
	}




	public TbPkUserChapterDO() {
    }

	public TbPkUserChapterDO(String pais,Integer score,Integer userId,Integer chapterId,Integer locationIndex,Integer winId,Integer fangpaoId,Integer wintype,String outpais) {
		this.pais = pais;
		this.score = score;
		this.userId = userId;
		this.chapterId = chapterId;
		this.locationIndex = locationIndex;
		this.winId = winId;
		this.fangpaoId = fangpaoId;
		this.wintype = wintype;
		this.outpais = outpais;
	}


	public TbPkUserChapterDO newInstance(){
		return new TbPkUserChapterDO();
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
	 * 剩余的手牌
	 **/
	public String getPais() {
		return pais;
	}

	/**
	 * 剩余的手牌
	 **/
	public void setPais(String pais) {
		this.pais = pais;
		changeProperty("pais",pais);
	}

	/**
	 * 本局得分情况
	 **/
	public Integer getScore() {
		return score;
	}

	/**
	 * 本局得分情况
	 **/
	public void setScore(Integer score) {
		this.score = score;
		changeProperty("score",score);
	}

	/**
	 * 用户的id
	 **/
	public Integer getUserId() {
		return userId;
	}

	/**
	 * 用户的id
	 **/
	public void setUserId(Integer userId) {
		this.userId = userId;
		changeProperty("userId",userId);
	}

	/**
	 * 牌局的id
	 **/
	public Integer getChapterId() {
		return chapterId;
	}

	/**
	 * 牌局的id
	 **/
	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
		changeProperty("chapterId",chapterId);
	}

	/**
	 * 用户在牌局中的位置
	 **/
	public Integer getLocationIndex() {
		return locationIndex;
	}

	/**
	 * 用户在牌局中的位置
	 **/
	public void setLocationIndex(Integer locationIndex) {
		this.locationIndex = locationIndex;
		changeProperty("locationIndex",locationIndex);
	}

	/**
	 * 赢的人的id，没有人赢的时候为-1
	 **/
	public Integer getWinId() {
		return winId;
	}

	/**
	 * 赢的人的id，没有人赢的时候为-1
	 **/
	public void setWinId(Integer winId) {
		this.winId = winId;
		changeProperty("winId",winId);
	}

	/**
	 * 房炮人的id,没有放炮时为-1
	 **/
	public Integer getFangpaoId() {
		return fangpaoId;
	}

	/**
	 * 房炮人的id,没有放炮时为-1
	 **/
	public void setFangpaoId(Integer fangpaoId) {
		this.fangpaoId = fangpaoId;
		changeProperty("fangpaoId",fangpaoId);
	}

	/**
	 * 赢的类型，
	 **/
	public Integer getWintype() {
		return wintype;
	}

	/**
	 * 赢的类型，
	 **/
	public void setWintype(Integer wintype) {
		this.wintype = wintype;
		changeProperty("wintype",wintype);
	}

	/**
	 * 打出去的手牌
	 **/
	public String getOutpais() {
		return outpais;
	}

	/**
	 * 打出去的手牌
	 **/
	public void setOutpais(String outpais) {
		this.outpais = outpais;
		changeProperty("outpais",outpais);
	}

    @Override
    public Object get(String dbName){
        switch(dbName){
        case "id":
            return id;
        case "pais":
            return pais;
        case "score":
            return score;
        case "userId":
            return userId;
        case "chapterId":
            return chapterId;
        case "locationIndex":
            return locationIndex;
        case "win_id":
            return winId;
        case "fangpao_id":
            return fangpaoId;
        case "wintype":
            return wintype;
        case "outpais":
            return outpais;
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
		case "pais":
			pais = (String)obj;
			return true;
		case "score":
			score = (Integer)obj;
			return true;
		case "userId":
			userId = (Integer)obj;
			return true;
		case "chapterId":
			chapterId = (Integer)obj;
			return true;
		case "locationIndex":
			locationIndex = (Integer)obj;
			return true;
		case "win_id":
			winId = (Integer)obj;
			return true;
		case "fangpao_id":
			fangpaoId = (Integer)obj;
			return true;
		case "wintype":
			wintype = (Integer)obj;
			return true;
		case "outpais":
			outpais = (String)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "TbPkUserChapter[id:"+ id+",pais:"+ (pais == null ?"null":pais.substring(0, Math.min(pais.length(), 64)))+",score:"+ score+",userId:"+ userId+",chapterId:"+ chapterId+",locationIndex:"+ locationIndex+",winId:"+ winId+",fangpaoId:"+ fangpaoId+",wintype:"+ wintype+",outpais:"+ (outpais == null ?"null":outpais.substring(0, Math.min(outpais.length(), 64)))+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<TbPkUserChapterDO ,Key>{
		public static final String DB_TABLE_NAME = "tb_pk_user_chapter";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String PAIS = "pais";
		public static final String SCORE = "score";
		public static final String USERID = "userId";
		public static final String CHAPTERID = "chapterId";
		public static final String LOCATIONINDEX = "locationIndex";
		public static final String WIN_ID = "win_id";
		public static final String FANGPAO_ID = "fangpao_id";
		public static final String WINTYPE = "wintype";
		public static final String OUTPAIS = "outpais";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("pais", "pais", String.class, new TypeReference<String>() {});
			super.initProperty("score", "score", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("userId", "userId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("chapterId", "chapterId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("locationIndex", "locationIndex", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("win_id", "winId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("fangpao_id", "fangpaoId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("wintype", "wintype", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("outpais", "outpais", String.class, new TypeReference<String>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `tb_pk_user_chapter` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `tb_pk_user_chapter` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(TbPkUserChapterDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
			Object idPtr;
			idPtr = t.getId();

			Object paisPtr;
			paisPtr = t.getPais();

			ps.setObject(i++, paisPtr);
			Object scorePtr;
			scorePtr = t.getScore();

			ps.setObject(i++, scorePtr);
			Object userIdPtr;
			userIdPtr = t.getUserId();

			ps.setObject(i++, userIdPtr);
			Object chapterIdPtr;
			chapterIdPtr = t.getChapterId();

			ps.setObject(i++, chapterIdPtr);
			Object locationIndexPtr;
			locationIndexPtr = t.getLocationIndex();

			ps.setObject(i++, locationIndexPtr);
			Object winIdPtr;
			winIdPtr = t.getWinId();

			ps.setObject(i++, winIdPtr);
			Object fangpaoIdPtr;
			fangpaoIdPtr = t.getFangpaoId();

			ps.setObject(i++, fangpaoIdPtr);
			Object wintypePtr;
			wintypePtr = t.getWintype();

			ps.setObject(i++, wintypePtr);
			Object outpaisPtr;
			outpaisPtr = t.getOutpais();

			ps.setObject(i++, outpaisPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(TbPkUserChapterDO t, PreparedStatement ps, int i) throws SQLException {
			Object idPtr;
				idPtr = t.getId();

			ps.setObject(i++,  idPtr);
			Object paisPtr;
				paisPtr = t.getPais();

			ps.setObject(i++,  paisPtr);
			Object scorePtr;
				scorePtr = t.getScore();

			ps.setObject(i++,  scorePtr);
			Object userIdPtr;
				userIdPtr = t.getUserId();

			ps.setObject(i++,  userIdPtr);
			Object chapterIdPtr;
				chapterIdPtr = t.getChapterId();

			ps.setObject(i++,  chapterIdPtr);
			Object locationIndexPtr;
				locationIndexPtr = t.getLocationIndex();

			ps.setObject(i++,  locationIndexPtr);
			Object winIdPtr;
				winIdPtr = t.getWinId();

			ps.setObject(i++,  winIdPtr);
			Object fangpaoIdPtr;
				fangpaoIdPtr = t.getFangpaoId();

			ps.setObject(i++,  fangpaoIdPtr);
			Object wintypePtr;
				wintypePtr = t.getWintype();

			ps.setObject(i++,  wintypePtr);
			Object outpaisPtr;
				outpaisPtr = t.getOutpais();

			ps.setObject(i++,  outpaisPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(TbPkUserChapterDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `tb_pk_user_chapter` (`pais`,`score`,`userId`,`chapterId`,`locationIndex`,`win_id`,`fangpao_id`,`wintype`,`outpais`) VALUES (?,?,?,?,?,?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `tb_pk_user_chapter` (`id`,`pais`,`score`,`userId`,`chapterId`,`locationIndex`,`win_id`,`fangpao_id`,`wintype`,`outpais`) VALUES (?,?,?,?,?,?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `tb_pk_user_chapter` (`pais`,`score`,`userId`,`chapterId`,`locationIndex`,`win_id`,`fangpao_id`,`wintype`,`outpais`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?,?,?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `tb_pk_user_chapter` SET `pais`=?,`score`=?,`userId`=?,`chapterId`=?,`locationIndex`=?,`win_id`=?,`fangpao_id`=?,`wintype`=?,`outpais`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `tb_pk_user_chapter` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `tb_pk_user_chapter`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `tb_pk_user_chapter` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `tb_pk_user_chapter` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `tb_pk_user_chapter` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<TbPkUserChapterDO> getRowMapper(){
			return new RowMapper<TbPkUserChapterDO>() {
				@Override
				public TbPkUserChapterDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					TbPkUserChapterDO o=new TbPkUserChapterDO();
					o.id = rs.getInt("id");
					o.pais = rs.getString("pais");
					o.score = rs.getInt("score");
					o.userId = rs.getInt("userId");
					o.chapterId = rs.getInt("chapterId");
					o.locationIndex = rs.getInt("locationIndex");
					o.winId = rs.getInt("win_id");
					o.fangpaoId = rs.getInt("fangpao_id");
					o.wintype = rs.getInt("wintype");
					o.outpais = rs.getString("outpais");
					return o;
				}
			};
		}

		@Override public <C extends TbPkUserChapterDO> RowMapper<C>  getRowMapper(final Class<C> cls){
			return new RowMapper<C>() {
				@Override
				public C mapRow(ResultSet rs, int rowNum) throws SQLException {
					C o;
					try{
						o = cls.newInstance();
						o.setId(rs.getInt("id"));
						o.setPais(rs.getString("pais"));
						o.setScore(rs.getInt("score"));
						o.setUserId(rs.getInt("userId"));
						o.setChapterId(rs.getInt("chapterId"));
						o.setLocationIndex(rs.getInt("locationIndex"));
						o.setWinId(rs.getInt("win_id"));
						o.setFangpaoId(rs.getInt("fangpao_id"));
						o.setWintype(rs.getInt("wintype"));
						o.setOutpais(rs.getString("outpais"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
