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

public class TbPkChapterUserDO extends EntityObject<TbPkChapterUserDO, TbPkChapterUserDO.Key>{

	private int id;
	private String pais;
	private Integer score;
	private Integer userId;
	private Integer chapterId;
	private Integer winnerId;

	public static class Key implements KeyObject<TbPkChapterUserDO, TbPkChapterUserDO.Key>{
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
			return "TbPkChapterUser[id:"+ id+ "]";
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
				return "TbPkChapterUser[id:"+ id+ "]";
			}
		};
	}




	public TbPkChapterUserDO() {
    }

	public TbPkChapterUserDO(String pais,Integer score,Integer userId,Integer chapterId,Integer winnerId) {
		this.pais = pais;
		this.score = score;
		this.userId = userId;
		this.chapterId = chapterId;
		this.winnerId = winnerId;
	}


	public TbPkChapterUserDO newInstance(){
		return new TbPkChapterUserDO();
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

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
		changeProperty("pais",pais);
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
		changeProperty("score",score);
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
		changeProperty("userId",userId);
	}

	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
		changeProperty("chapterId",chapterId);
	}

	public Integer getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(Integer winnerId) {
		this.winnerId = winnerId;
		changeProperty("winnerId",winnerId);
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
        case "winnerId":
            return winnerId;
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
		case "winnerId":
			winnerId = (Integer)obj;
			return true;
		default :
			return false;
		}
	}

	@Override
	public String toString() {
		return "TbPkChapterUser[id:"+ id+",pais:"+ (pais == null ?"null":pais.substring(0, Math.min(pais.length(), 64)))+",score:"+ score+",userId:"+ userId+",chapterId:"+ chapterId+",winnerId:"+ winnerId+ "]";
	}

	@Override
	@JsonIgnore
    @Transient
	public Table getTableInfo() {
		return TABLE_INFO;
	}


	public static final Table TABLE_INFO= new Table();

	public static final class Table extends TableInfo<TbPkChapterUserDO ,Key>{
		public static final String DB_TABLE_NAME = "tb_pk_chapter_user";
		private Map<String, UniqueInfo> uniqueMap;

		public static final String ID = "id";
		public static final String PAIS = "pais";
		public static final String SCORE = "score";
		public static final String USERID = "userId";
		public static final String CHAPTERID = "chapterId";
		public static final String WINNERID = "winnerId";

        public static final String UNIQUE_PRIMARY = "PRIMARY";

		private Table(){
		    uniqueMap = new HashMap<>();
			super.initProperty("id", "id", int.class, new TypeReference<Integer>() {});
			super.initProperty("pais", "pais", String.class, new TypeReference<String>() {});
			super.initProperty("score", "score", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("userId", "userId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("chapterId", "chapterId", Integer.class, new TypeReference<Integer>() {});
			super.initProperty("winnerId", "winnerId", Integer.class, new TypeReference<Integer>() {});
		}

		@Override public String getKeyUpdatePartialPrefixSql(){
			return "UPDATE `tb_pk_chapter_user` SET ";
		}

		@Override public String getKeyWhereByKeySql(){
			return " WHERE `id`=?";
		}

		@Override public String getKeyDeleteSql(){
			return "DELETE FROM `tb_pk_chapter_user` WHERE `id`=?";
		}

		@Override public Map<String, UniqueInfo> getUniques(){
            return uniqueMap;
		}

		@Override
        public UniqueInfo getUniques(String uniqueName){
            return uniqueMap.get(uniqueName);
        }

		@Override
		public int setPreparedStatement(TbPkChapterUserDO t, PreparedStatement ps, int i, boolean isSetUnique) throws SQLException {
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
			Object winnerIdPtr;
			winnerIdPtr = t.getWinnerId();

			ps.setObject(i++, winnerIdPtr);
			return i;
		}

		@Override
        public int setAllPreparedStatement(TbPkChapterUserDO t, PreparedStatement ps, int i) throws SQLException {
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
			Object winnerIdPtr;
				winnerIdPtr = t.getWinnerId();

			ps.setObject(i++,  winnerIdPtr);
        	return i;
        }

		@Override
		public int setPreparedStatementKeys(TbPkChapterUserDO t, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, t.getId());
			return i;
		}

		@Override
		public int setKeyPreparedStatement(Key k, PreparedStatement ps, int i) throws SQLException {
			ps.setObject(i++, k.getId());
			return i;
		}

		@Override public String getInsertSql(){
			return "INSERT INTO `tb_pk_chapter_user` (`pais`,`score`,`userId`,`chapterId`,`winnerId`) VALUES (?,?,?,?,?)";
		}

		@Override public String getReplaceSql(){
        	return "REPLACE INTO `tb_pk_chapter_user` (`id`,`pais`,`score`,`userId`,`chapterId`,`winnerId`) VALUES (?,?,?,?,?,?)";
        }

		@Override public String getFastInsertPrefixSql(){
			return "INSERT INTO `tb_pk_chapter_user` (`pais`,`score`,`userId`,`chapterId`,`winnerId`) VALUES ";
		}
		@Override public String getFastInsertValueItemsSql(){
			return " (?,?,?,?,?) ";
		}
		@Override public String getUpdateSql(){
			return "UPDATE `tb_pk_chapter_user` SET `pais`=?,`score`=?,`userId`=?,`chapterId`=?,`winnerId`=? WHERE `id`=?";
		}

		@Override public String getSelectByKeySql(){
			return "SELECT * FROM `tb_pk_chapter_user` WHERE `id`=? ORDER BY `id` DESC";
		}
		@Override public String getSelectCountSql(){
			return "SELECT count(*) FROM `tb_pk_chapter_user`";
		}
		@Override public String getFormatSelectSql(){
			return "SELECT %s FROM `tb_pk_chapter_user` ORDER BY `id` DESC";
		}
		@Override public String getFormatSelectPrefixSql(){
			return "SELECT %s FROM `tb_pk_chapter_user` ";
		}
		@Override public String getSelectPrefixSql(){
			return "SELECT * FROM `tb_pk_chapter_user` ";
		}
		@Override public String getOrderByIdDescSql(){
			return " ORDER BY `id` DESC";
		}
		@Override public String getDbTableName(){
			return DB_TABLE_NAME;
		}

		@Override public RowMapper<TbPkChapterUserDO> getRowMapper(){
			return new RowMapper<TbPkChapterUserDO>() {
				@Override
				public TbPkChapterUserDO mapRow(ResultSet rs, int rowNum) throws SQLException {
					TbPkChapterUserDO o=new TbPkChapterUserDO();
					o.id = rs.getInt("id");
					o.pais = rs.getString("pais");
					o.score = rs.getInt("score");
					o.userId = rs.getInt("userId");
					o.chapterId = rs.getInt("chapterId");
					o.winnerId = rs.getInt("winnerId");
					return o;
				}
			};
		}

		@Override public <C extends TbPkChapterUserDO> RowMapper<C>  getRowMapper(final Class<C> cls){
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
						o.setWinnerId(rs.getInt("winnerId"));
                        return o;
					} catch (InstantiationException | IllegalAccessException e) {
						throw new SQLException("必须实现默认构造函数",e);
					}
				}
			};
		}
	}
}
