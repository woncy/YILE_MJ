// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BaseDaoImpi.java

package org.forkjoin.core.dao.impi;

import java.sql.*;
import org.forkjoin.core.dao.EntityObject;
import org.forkjoin.core.dao.TableInfo;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.support.JdbcUtils;

// Referenced classes of package org.forkjoin.core.dao.impi:
//			BaseDaoImpi

class BaseDaoImpi$1
	implements ConnectionCallback {

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
				BaseDaoImpi.log.debug("replace: {}[object:{}]", sql, val$t);
			}
			ps = con.prepareStatement(sql, 1);
			tableInfo.setAllPreparedStatement(val$t, ps, 1);
			ps.execute();
			long key = 0L;
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				key = rs.getLong(1);
			}
			long1 = Long.valueOf(key);
		}
		catch (Exception e) {
			BaseDaoImpi.log.error("sql´íÎó{}", val$t, e);
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

	BaseDaoImpi$1() {
		this.this$0 = this$0;
		val$t = EntityObject.this;
		super();
	}
}
