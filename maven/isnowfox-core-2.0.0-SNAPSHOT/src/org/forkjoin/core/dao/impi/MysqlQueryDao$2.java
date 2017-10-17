// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MysqlQueryDao.java

package org.forkjoin.core.dao.impi;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.sql.*;
import java.util.*;
import org.forkjoin.core.dao.grid.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.support.JdbcUtils;

// Referenced classes of package org.forkjoin.core.dao.impi:
//			MysqlQueryDao

class MysqlQueryDao$2
	implements ConnectionCallback {

	final String val$sql;
	final int val$page;
	final int val$pageSize;
	final MysqlQueryDao this$0;

	public GridPageResult doInConnection(Connection con) throws SQLException, DataAccessException {
		Statement ps;
		Exception exception;
		ps = null;
		ResultSet rs = null;
		GridPageResult gridpageresult;
		try {
			ps = con.createStatement();
			rs = ps.executeQuery(val$sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int count = metaData.getColumnCount();
			Columns columns = new Columns();
			for (int i = 1; i <= count; i++) {
				columns.add(new Column(metaData.getColumnLabel(i), metaData.getColumnLabel(i), metaData.getColumnType(i), metaData.getColumnClassName(i), metaData.getColumnDisplaySize(i)));
			}

			int start = (val$page - 1) * val$pageSize + 1;
			List list = Lists.newArrayList();
			if (rs.absolute(start)) {
				int i = 0;
				do {
					Map map = Maps.newHashMap();
					Column c;
					for (Iterator iterator = columns.iterator(); iterator.hasNext(); map.put(c.getCode(), rs.getObject(c.getCode()))) {
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
			GridPageResult result = GridPageResult.createGridPage(row, val$page, val$pageSize, columns, list);
			gridpageresult = result;
		}
		catch (ClassNotFoundException e) {
			throw new SQLException("驱动返回错误的Class,通常不可能产生这样的错误.", e);
		}
		finally {
			JdbcUtils.closeResultSet(rs);
		}
		JdbcUtils.closeResultSet(rs);
		JdbcUtils.closeStatement(ps);
		return gridpageresult;
		JdbcUtils.closeStatement(ps);
		throw exception;
	}

	public volatile Object doInConnection(Connection connection) throws SQLException, DataAccessException {
		return doInConnection(connection);
	}

	MysqlQueryDao$2() {
		this.this$0 = this$0;
		val$sql = s;
		val$page = i;
		val$pageSize = I.this;
		super();
	}
}
