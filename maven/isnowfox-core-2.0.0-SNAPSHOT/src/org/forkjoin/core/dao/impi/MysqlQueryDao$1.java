// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MysqlQueryDao.java

package org.forkjoin.core.dao.impi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.isnowfox.core.PageResult;
import java.sql.*;
import java.util.*;
import org.forkjoin.core.dao.grid.Column;
import org.forkjoin.core.dao.grid.Columns;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.support.JdbcUtils;

// Referenced classes of package org.forkjoin.core.dao.impi:
//			MysqlQueryDao

class MysqlQueryDao$1
	implements ConnectionCallback {

	final String val$sql;
	final int val$page;
	final int val$pageSize;
	final Columns val$columns;
	final MysqlQueryDao this$0;

	public PageResult doInConnection(Connection con) throws SQLException, DataAccessException {
		Statement ps;
		ResultSet rs;
		ps = null;
		rs = null;
		PageResult pageresult;
		ps = con.createStatement();
		rs = ps.executeQuery(val$sql);
		int start = (val$page - 1) * val$pageSize + 1;
		List list = Lists.newArrayList();
		if (rs.absolute(start)) {
			int i = 0;
			do {
				Map map = Maps.newHashMap();
				Column c;
				for (Iterator iterator = val$columns.iterator(); iterator.hasNext(); map.put(c.getCode(), rs.getObject(c.getCode()))) {
					c = (Column)iterator.next();
				}

				list.add(map);
				i++;
			} while (rs.next() && i < val$pageSize);
		}
		if (!rs.isLast()) {
			rs.last();
		}
		int row = rs.getRow();
		PageResult result = PageResult.createPage(row, val$page, val$pageSize, list);
		pageresult = result;
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

	MysqlQueryDao$1() {
		this.this$0 = this$0;
		val$sql = s;
		val$page = i;
		val$pageSize = j;
		val$columns = Columns.this;
		super();
	}
}
