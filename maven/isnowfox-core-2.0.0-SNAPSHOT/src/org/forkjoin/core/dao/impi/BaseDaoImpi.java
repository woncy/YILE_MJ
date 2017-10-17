// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BaseDaoImpi.java

package org.forkjoin.core.dao.impi;

import com.isnowfox.core.PageResult;
import java.sql.*;
import java.util.*;
import org.forkjoin.core.dao.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.JdbcUtils;

// Referenced classes of package org.forkjoin.core.dao.impi:
//			AbstractBaseDao, ReadOnlyDaoImpi

public class BaseDaoImpi extends AbstractBaseDao {

	protected static final Logger log = LoggerFactory.getLogger(org/forkjoin/core/dao/impi/BaseDaoImpi);
	private ReadOnlyDaoImpi readOnlyDaoImpi;

	public BaseDaoImpi(TableInfo tableInfo) {
		super(tableInfo);
		readOnlyDaoImpi = new ReadOnlyDaoImpi(tableInfo);
	}

	protected void initTemplateConfig() {
		super.initTemplateConfig();
		readOnlyDaoImpi.setJdbcTemplate(getJdbcTemplate());
	}

	public long replace(final EntityObject t) {
		return ((Long)getJdbcTemplate().execute(new ConnectionCallback() {

			final EntityObject val$t;
			final BaseDaoImpi this$0;

			public Long doInConnection(Connection con) throws SQLException, DataAccessException {
				PreparedStatement ps;
				Exception exception;
				ps = null;
				ResultSet rs = null;
				Long long1;
				try {
					String sql = tableInfo.getReplaceSql();
					if (BaseDaoImpi.log.isDebugEnabled()) {
						BaseDaoImpi.log.debug("replace: {}[object:{}]", sql, t);
					}
					ps = con.prepareStatement(sql, 1);
					tableInfo.setAllPreparedStatement(t, ps, 1);
					ps.execute();
					long key = 0L;
					rs = ps.getGeneratedKeys();
					if (rs.next()) {
						key = rs.getLong(1);
					}
					long1 = Long.valueOf(key);
				}
				catch (Exception e) {
					BaseDaoImpi.log.error("sql´íÎó{}", t, e);
					throw e;
				}
				finally {
					JdbcUtils.closeResultSet(rs);
				}
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(ps);
				return long1;
				JdbcUtils.closeStatement(ps);
				throw exception;
			}

			public volatile Object doInConnection(Connection connection) throws SQLException, DataAccessException {
				return doInConnection(connection);
			}

			 {
				this.this$0 = BaseDaoImpi.this;
				t = entityobject;
				super();
			}
		})).longValue();
	}

	public long insert(final EntityObject t) {
		return ((Long)getJdbcTemplate().execute(new ConnectionCallback() {

			final EntityObject val$t;
			final BaseDaoImpi this$0;

			public Long doInConnection(Connection con) throws SQLException, DataAccessException {
				PreparedStatement ps;
				Exception exception;
				ps = null;
				ResultSet rs = null;
				Long long1;
				try {
					String sql = tableInfo.getInsertSql();
					if (BaseDaoImpi.log.isDebugEnabled()) {
						BaseDaoImpi.log.debug("insert: {}[object:{}]", sql, t);
					}
					ps = con.prepareStatement(sql, 1);
					tableInfo.setPreparedStatement(t, ps, 1, true);
					ps.execute();
					long key = 0L;
					rs = ps.getGeneratedKeys();
					if (rs.next()) {
						key = rs.getLong(1);
					}
					long1 = Long.valueOf(key);
				}
				catch (Exception e) {
					BaseDaoImpi.log.error("sql´íÎó{}", t, e);
					throw e;
				}
				finally {
					JdbcUtils.closeResultSet(rs);
				}
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(ps);
				return long1;
				JdbcUtils.closeStatement(ps);
				throw exception;
			}

			public volatile Object doInConnection(Connection connection) throws SQLException, DataAccessException {
				return doInConnection(connection);
			}

			 {
				this.this$0 = BaseDaoImpi.this;
				t = entityobject;
				super();
			}
		})).longValue();
	}

	public boolean update(final EntityObject t) {
		return ((Boolean)getJdbcTemplate().execute(new ConnectionCallback() {

			final EntityObject val$t;
			final BaseDaoImpi this$0;

			public Boolean doInConnection(Connection con) throws SQLException, DataAccessException {
				Exception exception;
				PreparedStatement ps = null;
				Boolean boolean1;
				try {
					if (BaseDaoImpi.log.isDebugEnabled()) {
						BaseDaoImpi.log.debug("update: {}[nums:{}]", tableInfo.getUpdateSql(), t.toString());
					}
					ps = con.prepareStatement(tableInfo.getUpdateSql());
					int i = 1;
					i = tableInfo.setPreparedStatement(t, ps, i, true);
					tableInfo.setPreparedStatementKeys(t, ps, i);
					boolean1 = Boolean.valueOf(ps.executeUpdate() > 0);
				}
				catch (Exception e) {
					BaseDaoImpi.log.error("sql´íÎó", e);
					throw e;
				}
				finally {
					JdbcUtils.closeStatement(ps);
				}
				JdbcUtils.closeStatement(ps);
				return boolean1;
				throw exception;
			}

			public volatile Object doInConnection(Connection connection) throws SQLException, DataAccessException {
				return doInConnection(connection);
			}

			 {
				this.this$0 = BaseDaoImpi.this;
				t = entityobject;
				super();
			}
		})).booleanValue();
	}

	public boolean del(final KeyObject key) {
		return ((Boolean)getJdbcTemplate().execute(new ConnectionCallback() {

			final KeyObject val$key;
			final BaseDaoImpi this$0;

			public Boolean doInConnection(Connection con) throws SQLException, DataAccessException {
				Exception exception;
				PreparedStatement ps = null;
				Boolean boolean1;
				try {
					if (BaseDaoImpi.log.isDebugEnabled()) {
						BaseDaoImpi.log.debug("del: {}[map:{}]", tableInfo.getKeyDeleteSql(), key);
					}
					ps = con.prepareStatement(tableInfo.getKeyDeleteSql());
					tableInfo.setKeyPreparedStatement(key, ps, 1);
					boolean1 = Boolean.valueOf(ps.executeUpdate() > 0);
				}
				catch (Exception e) {
					BaseDaoImpi.log.error("sql´íÎó", e);
					throw e;
				}
				finally {
					JdbcUtils.closeStatement(ps);
				}
				JdbcUtils.closeStatement(ps);
				return boolean1;
				throw exception;
			}

			public volatile Object doInConnection(Connection connection) throws SQLException, DataAccessException {
				return doInConnection(connection);
			}

			 {
				this.this$0 = BaseDaoImpi.this;
				key = keyobject;
				super();
			}
		})).booleanValue();
	}

	public boolean del(final String key0, final Object value0) {
		return ((Boolean)getJdbcTemplate().execute(new ConnectionCallback() {

			final String val$key0;
			final Object val$value0;
			final BaseDaoImpi this$0;

			public Boolean doInConnection(Connection con) throws SQLException, DataAccessException {
				Exception exception;
				PreparedStatement ps = null;
				Boolean boolean1;
				try {
					String tableName = tableInfo.getDbTableName();
					String sql = (new StringBuilder()).append("DELETE FROM `").append(tableName).append("` WHERE `").append(SqlUtils.nameFilter(key0)).append("` = ?").toString();
					if (BaseDaoImpi.log.isDebugEnabled()) {
						BaseDaoImpi.log.debug("del by {}: {}[map:{}]", new Object[] {
							tableName, sql, value0
						});
					}
					ps = con.prepareStatement(sql);
					ps.setObject(1, value0);
					boolean1 = Boolean.valueOf(ps.executeUpdate() > 0);
				}
				catch (Exception e) {
					BaseDaoImpi.log.error("sql´íÎó", e);
					throw e;
				}
				finally {
					JdbcUtils.closeStatement(ps);
				}
				JdbcUtils.closeStatement(ps);
				return boolean1;
				throw exception;
			}

			public volatile Object doInConnection(Connection connection) throws SQLException, DataAccessException {
				return doInConnection(connection);
			}

			 {
				this.this$0 = BaseDaoImpi.this;
				key0 = s;
				value0 = obj;
				super();
			}
		})).booleanValue();
	}

	public int insert(final List list) {
		return ((Integer)getJdbcTemplate().execute(new ConnectionCallback() {

			final List val$list;
			final BaseDaoImpi this$0;

			public Integer doInConnection(Connection con) throws SQLException, DataAccessException {
				PreparedStatement ps = null;
				Integer integer1;
				if (list.isEmpty()) {
					break MISSING_BLOCK_LABEL_206;
				}
				StringBuilder sb = new StringBuilder(tableInfo.getFastInsertPrefixSql());
				int i;
				for (i = 0; i < list.size(); i++) {
					if (i != 0) {
						sb.append(",");
					}
					sb.append(tableInfo.getFastInsertValueItemsSql());
				}

				if (BaseDaoImpi.log.isDebugEnabled()) {
					BaseDaoImpi.log.debug("fastInsert: {}; nums:{}", sb.toString(), Integer.valueOf(list.size()));
				}
				ps = con.prepareStatement(sb.toString(), 1);
				i = 1;
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					EntityObject t = (EntityObject)iterator.next();
					i = tableInfo.setPreparedStatement(t, ps, i, true);
				}

				integer1 = Integer.valueOf(ps.executeUpdate());
				JdbcUtils.closeStatement(ps);
				return integer1;
				Exception exception;
				Integer integer;
				try {
					integer = Integer.valueOf(0);
				}
				catch (Exception e) {
					BaseDaoImpi.log.error("sql´íÎó", e);
					throw e;
				}
				finally {
					JdbcUtils.closeStatement(ps);
				}
				JdbcUtils.closeStatement(ps);
				return integer;
				throw exception;
			}

			public volatile Object doInConnection(Connection connection) throws SQLException, DataAccessException {
				return doInConnection(connection);
			}

			 {
				this.this$0 = BaseDaoImpi.this;
				list = list1;
				super();
			}
		})).intValue();
	}

	public boolean incrementUpdatePartial(Map m, KeyObject key) {
		StringBuilder sb = new StringBuilder();
		sb.append(tableInfo.getKeyUpdatePartialPrefixSql());
		ArrayList args = toIncrementSqlSet(sb, m);
		sb.append(tableInfo.getKeyWhereByKeySql());
		String sql = sb.toString();
		Collections.addAll(args, key.getQueryParams());
		return updatePartial(sql, args) > 0;
	}

	public boolean updatePartial(Map m, KeyObject key) {
		StringBuilder sb = new StringBuilder();
		sb.append(tableInfo.getKeyUpdatePartialPrefixSql());
		ArrayList args = toSqlSet(sb, m);
		sb.append(tableInfo.getKeyWhereByKeySql());
		String sql = sb.toString();
		Collections.addAll(args, key.getQueryParams());
		return updatePartial(sql, args) > 0;
	}

	public int updatePartial(Map m, Map key) {
		StringBuilder sb = new StringBuilder();
		sb.append(tableInfo.getKeyUpdatePartialPrefixSql());
		ArrayList args = toSqlSet(sb, m);
		sb.append(" WHERE ");
		ArrayList keyArgs = toSqlWhere(sb, key);
		args.addAll(keyArgs);
		String sql = sb.toString();
		return updatePartial(sql, args);
	}

	private int updatePartial(final String sql, final ArrayList args) {
		return ((Integer)getJdbcTemplate().execute(new ConnectionCallback() {

			final String val$sql;
			final ArrayList val$args;
			final BaseDaoImpi this$0;

			public Integer doInConnection(Connection con) throws SQLException, DataAccessException {
				Exception exception;
				PreparedStatement ps = null;
				Integer integer;
				try {
					if (BaseDaoImpi.log.isDebugEnabled()) {
						BaseDaoImpi.log.debug("updatePartial: {}; args:{}", sql, args);
					}
					ps = con.prepareStatement(sql);
					int i = 1;
					for (Iterator iterator = args.iterator(); iterator.hasNext();) {
						Object arg = iterator.next();
						ps.setObject(i, arg);
						i++;
					}

					integer = Integer.valueOf(ps.executeUpdate());
				}
				catch (Exception e) {
					BaseDaoImpi.log.error("sql´íÎó", e);
					throw e;
				}
				finally {
					JdbcUtils.closeStatement(ps);
				}
				JdbcUtils.closeStatement(ps);
				return integer;
				throw exception;
			}

			public volatile Object doInConnection(Connection connection) throws SQLException, DataAccessException {
				return doInConnection(connection);
			}

			 {
				this.this$0 = BaseDaoImpi.this;
				sql = s;
				args = arraylist;
				super();
			}
		})).intValue();
	}

	private ArrayList toIncrementSqlSet(StringBuilder sb, Map m) {
		boolean isAdd = false;
		ArrayList list = new ArrayList();
		for (Iterator iterator = m.entrySet().iterator(); iterator.hasNext(); sb.append("` + ?")) {
			java.util.Map.Entry e = (java.util.Map.Entry)iterator.next();
			String name = SqlUtils.nameFilter((String)e.getKey());
			list.add(e.getValue());
			if (isAdd) {
				sb.append(',');
			} else {
				isAdd = true;
			}
			sb.append('`');
			sb.append(name);
			sb.append("` = `");
			sb.append(name);
		}

		return list;
	}

	private ArrayList toSqlSet(StringBuilder sb, Map m) {
		return toSqlSet(sb, m, ", ");
	}

	private ArrayList toSqlWhere(StringBuilder sb, Map m) {
		return toSqlSet(sb, m, " AND ");
	}

	private ArrayList toSqlSet(StringBuilder sb, Map m, String split) {
		boolean isAdd = false;
		ArrayList list = new ArrayList();
		for (Iterator iterator = m.entrySet().iterator(); iterator.hasNext(); sb.append("` = ?")) {
			java.util.Map.Entry e = (java.util.Map.Entry)iterator.next();
			String name = SqlUtils.nameFilter((String)e.getKey());
			list.add(e.getValue());
			if (isAdd) {
				sb.append(split);
			} else {
				isAdd = true;
			}
			sb.append('`');
			sb.append(name);
		}

		return list;
	}

	public EntityObject get(KeyObject k) {
		return readOnlyDaoImpi.get(k);
	}

	public long getCount() {
		return readOnlyDaoImpi.getCount();
	}

	public long getCount(QueryParams params) {
		return readOnlyDaoImpi.getCount(params);
	}

	public long getCount(Select select) {
		return readOnlyDaoImpi.getCount(select);
	}

	public PageResult findPage(QueryParams params, Order order, int page, int pageSize) {
		return readOnlyDaoImpi.findPage(params, order, page, pageSize);
	}

	public PageResult findPage(Select select, int page, int pageSize) {
		return readOnlyDaoImpi.findPage(select, page, pageSize);
	}

	public PageResult findPageBySelect(Select select, RowMapper rowMapper, int page, int pageSize) {
		return readOnlyDaoImpi.findPageBySelect(select, rowMapper, page, pageSize);
	}

	public List find(int max, QueryParams params, Order order) {
		return readOnlyDaoImpi.find(max, params, order);
	}

	public List find(int max, Select select) {
		return readOnlyDaoImpi.find(max, select);
	}

	public List findBySelect(int max, Select select, RowMapper rowMapper) {
		return readOnlyDaoImpi.findBySelect(max, select, rowMapper);
	}

	public EntityObject findObject(QueryParams params) {
		return readOnlyDaoImpi.findObject(params);
	}

}
