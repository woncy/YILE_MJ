// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MysqlJdbcDaoSupport.java

package org.forkjoin.core.dao;

import com.google.common.collect.Lists;
import com.isnowfox.core.PageResult;
import java.sql.*;
import java.util.*;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.JdbcUtils;

// Referenced classes of package org.forkjoin.core.dao:
//			ResultPageDataAccessException

public class MysqlJdbcDaoSupport extends JdbcDaoSupport {

	public static final int SQL_MAX = 1000;
	public static final String STRING_COUNT = " count(1) ";

	public MysqlJdbcDaoSupport() {
	}

	public void toSqlSet(StringBuilder sb, Map m) {
		boolean isAdd = false;
		for (Iterator iterator = m.entrySet().iterator(); iterator.hasNext(); sb.append("` = ?")) {
			java.util.Map.Entry e = (java.util.Map.Entry)iterator.next();
			String name = (String)e.getKey();
			if (isAdd) {
				sb.append(',');
			} else {
				isAdd = true;
			}
			sb.append('`');
			sb.append(name);
		}

	}

	public transient Object querySingle(String sql, RowMapper rowMapper, Object args[]) {
		List results = (List)getJdbcTemplate().query(sql, args, new RowMapperResultSetExtractor(rowMapper, 1));
		return DataAccessUtils.singleResult(results);
	}

	protected transient PageResult fastQueryPage(final String sql, final RowMapper rowMapper, final int page, final int pageSize, final Object objs[]) {
		return (PageResult)getJdbcTemplate().execute(new ConnectionCallback() {

			final String val$sql;
			final Object val$objs[];
			final int val$page;
			final int val$pageSize;
			final RowMapper val$rowMapper;
			final MysqlJdbcDaoSupport this$0;

			public PageResult doInConnection(Connection con) throws SQLException, DataAccessException {
				PreparedStatement ps;
				ResultSet rs;
				ps = null;
				rs = null;
				PageResult pageresult;
				ps = con.prepareStatement(String.format(sql, new Object[] {
					" count(1) "
				}));
				int i = 1;
				Object aobj[] = objs;
				int j = aobj.length;
				for (int k = 0; k < j; k++) {
					Object o = aobj[k];
					ps.setObject(i++, o);
				}

				rs = ps.executeQuery();
				int count;
				if (rs.next()) {
					count = Integer.valueOf(rs.getObject(1).toString()).intValue();
				} else {
					throw new ResultPageDataAccessException("²éÑ¯countÊ§°Ü!");
				}
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(ps);
				PageResult pageResult = PageResult.createPage(count, page, pageSize, null);
				List list = Lists.newArrayListWithCapacity(pageResult.getPageCount());
				int start = pageResult.getStart();
				ps = con.prepareStatement((new StringBuilder()).append(String.format(sql, new Object[] {
					" * "
				})).append(" LIMIT ").append(start).append(",").append(pageSize).toString());
				i = 1;
				Object aobj1[] = objs;
				int l = aobj1.length;
				for (int i1 = 0; i1 < l; i1++) {
					Object o = aobj1[i1];
					ps.setObject(i++, o);
				}

				rs = ps.executeQuery();
				int rowNum = 0;
				for (; rs.next(); list.add(rowMapper.mapRow(rs, rowNum++))) { }
				pageResult.setValue(list);
				pageresult = pageResult;
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(ps);
				return pageresult;
				Exception exception;
				exception;
				JdbcUtils.closeResultSet(rs);
				JdbcUtils.closeStatement(ps);
				throw exception;
			}

			public volatile Object doInConnection(Connection connection) throws SQLException, DataAccessException {
				return doInConnection(connection);
			}

			 {
				this.this$0 = MysqlJdbcDaoSupport.this;
				sql = s;
				objs = aobj;
				page = i;
				pageSize = j;
				rowMapper = rowmapper;
				super();
			}
		});
	}
}
