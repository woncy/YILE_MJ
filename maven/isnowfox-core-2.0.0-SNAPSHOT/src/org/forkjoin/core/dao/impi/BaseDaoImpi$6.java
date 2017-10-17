// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BaseDaoImpi.java

package org.forkjoin.core.dao.impi;

import java.sql.*;
import java.util.Iterator;
import java.util.List;
import org.forkjoin.core.dao.EntityObject;
import org.forkjoin.core.dao.TableInfo;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.support.JdbcUtils;

// Referenced classes of package org.forkjoin.core.dao.impi:
//			BaseDaoImpi

class BaseDaoImpi$6
	implements ConnectionCallback {

	final List val$list;
	final BaseDaoImpi this$0;

	public Integer doInConnection(Connection con) throws SQLException, DataAccessException {
		PreparedStatement ps = null;
		Integer integer1;
		if (val$list.isEmpty()) {
			break MISSING_BLOCK_LABEL_206;
		}
		StringBuilder sb = new StringBuilder(tableInfo.getFastInsertPrefixSql());
		int i;
		for (i = 0; i < val$list.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(tableInfo.getFastInsertValueItemsSql());
		}

		if (BaseDaoImpi.log.isDebugEnabled()) {
			BaseDaoImpi.log.debug("fastInsert: {}; nums:{}", sb.toString(), Integer.valueOf(val$list.size()));
		}
		ps = con.prepareStatement(sb.toString(), 1);
		i = 1;
		for (Iterator iterator = val$list.iterator(); iterator.hasNext();) {
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

	BaseDaoImpi$6() {
		this.this$0 = this$0;
		val$list = List.this;
		super();
	}
}
