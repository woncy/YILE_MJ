// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BaseDaoImpi.java

package org.forkjoin.core.dao.impi;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.support.JdbcUtils;

// Referenced classes of package org.forkjoin.core.dao.impi:
//			BaseDaoImpi

class BaseDaoImpi$7
	implements ConnectionCallback {

	final String val$sql;
	final ArrayList val$args;
	final BaseDaoImpi this$0;

	public Integer doInConnection(Connection con) throws SQLException, DataAccessException {
		Exception exception;
		PreparedStatement ps = null;
		Integer integer;
		try {
			if (BaseDaoImpi.log.isDebugEnabled()) {
				BaseDaoImpi.log.debug("updatePartial: {}; args:{}", val$sql, val$args);
			}
			ps = con.prepareStatement(val$sql);
			int i = 1;
			for (Iterator iterator = val$args.iterator(); iterator.hasNext();) {
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

	BaseDaoImpi$7() {
		this.this$0 = this$0;
		val$sql = s;
		val$args = ArrayList.this;
		super();
	}
}
