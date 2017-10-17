// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MysqlJdbcDaoSupport.java

package org.forkjoin.core.dao;

import com.google.common.collect.Lists;
import com.isnowfox.core.PageResult;
import java.sql.*;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

// Referenced classes of package org.forkjoin.core.dao:
//			MysqlJdbcDaoSupport, ResultPageDataAccessException

class MysqlJdbcDaoSupport$1
	implements ConnectionCallback {

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
		ps = con.prepareStatement(String.format(val$sql, new Object[] {
			" count(1) "
		}));
		int i = 1;
		Object aobj[] = val$objs;
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
		PageResult pageResult = PageResult.createPage(count, val$page, val$pageSize, null);
		List list = Lists.newArrayListWithCapacity(pageResult.getPageCount());
		int start = pageResult.getStart();
		ps = con.prepareStatement((new StringBuilder()).append(String.format(val$sql, new Object[] {
			" * "
		})).append(" LIMIT ").append(start).append(",").append(val$pageSize).toString());
		i = 1;
		Object aobj1[] = val$objs;
		int l = aobj1.length;
		for (int i1 = 0; i1 < l; i1++) {
			Object o = aobj1[i1];
			ps.setObject(i++, o);
		}

		rs = ps.executeQuery();
		int rowNum = 0;
		for (; rs.next(); list.add(val$rowMapper.mapRow(rs, rowNum++))) { }
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

	MysqlJdbcDaoSupport$1() {
		this.this$0 = this$0;
		val$sql = s;
		val$objs = aobj;
		val$page = i;
		val$pageSize = j;
		val$rowMapper = RowMapper.this;
		super();
	}
}
