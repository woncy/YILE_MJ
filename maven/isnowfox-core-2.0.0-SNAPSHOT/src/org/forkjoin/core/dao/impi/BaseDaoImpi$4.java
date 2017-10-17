// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BaseDaoImpi.java

package org.forkjoin.core.dao.impi;

import java.sql.*;
import org.forkjoin.core.dao.KeyObject;
import org.forkjoin.core.dao.TableInfo;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.support.JdbcUtils;

// Referenced classes of package org.forkjoin.core.dao.impi:
//			BaseDaoImpi

class BaseDaoImpi$4
	implements ConnectionCallback {

	final KeyObject val$key;
	final BaseDaoImpi this$0;

	public Boolean doInConnection(Connection con) throws SQLException, DataAccessException {
		Exception exception;
		PreparedStatement ps = null;
		Boolean boolean1;
		try {
			if (BaseDaoImpi.log.isDebugEnabled()) {
				BaseDaoImpi.log.debug("del: {}[map:{}]", tableInfo.getKeyDeleteSql(), val$key);
			}
			ps = con.prepareStatement(tableInfo.getKeyDeleteSql());
			tableInfo.setKeyPreparedStatement(val$key, ps, 1);
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

	BaseDaoImpi$4() {
		this.this$0 = this$0;
		val$key = KeyObject.this;
		super();
	}
}
