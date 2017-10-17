// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BaseDaoImpi.java

package org.forkjoin.core.dao.impi;

import java.sql.*;
import org.forkjoin.core.dao.SqlUtils;
import org.forkjoin.core.dao.TableInfo;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.support.JdbcUtils;

// Referenced classes of package org.forkjoin.core.dao.impi:
//			BaseDaoImpi

class BaseDaoImpi$5
	implements ConnectionCallback {

	final String val$key0;
	final Object val$value0;
	final BaseDaoImpi this$0;

	public Boolean doInConnection(Connection con) throws SQLException, DataAccessException {
		Exception exception;
		PreparedStatement ps = null;
		Boolean boolean1;
		try {
			String tableName = tableInfo.getDbTableName();
			String sql = (new StringBuilder()).append("DELETE FROM `").append(tableName).append("` WHERE `").append(SqlUtils.nameFilter(val$key0)).append("` = ?").toString();
			if (BaseDaoImpi.log.isDebugEnabled()) {
				BaseDaoImpi.log.debug("del by {}: {}[map:{}]", new Object[] {
					tableName, sql, val$value0
				});
			}
			ps = con.prepareStatement(sql);
			ps.setObject(1, val$value0);
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

	BaseDaoImpi$5() {
		this.this$0 = this$0;
		val$key0 = s;
		val$value0 = Object.this;
		super();
	}
}
