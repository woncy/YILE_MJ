// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   QueryParams.java

package org.forkjoin.core.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

// Referenced classes of package org.forkjoin.core.dao:
//			QueryParams, QueryParam

static class QueryParams$1 extends QueryParams {

	final boolean val$not;
	final String val$key;
	final Object val$value;
	final ratorType val$opt;

	public void toSql(StringBuilder sb) {
		sb.append(" WHERE ");
		if (val$not) {
			sb.append(" NOT (");
		}
		sb.append('`');
		sb.append(val$key);
		sb.append('`');
		QueryParams.access$100(sb, val$value, val$opt);
		if (val$not) {
			sb.append(") ");
		}
	}

	public int setPreparedStatement(PreparedStatement ps, int i) throws SQLException {
		if (val$opt != ratorType.IS_NULL && val$opt != ratorType.IS_NOT_NULL) {
			ps.setObject(i++, val$value);
		}
		return i;
	}

	public Object[] toParams() {
		return (new Object[] {
			val$value
		});
	}

	public boolean isEmpty() {
		return false;
	}

	QueryParams$1(boolean flag, String s, Object obj, ratorType ratortype) {
		val$not = flag;
		val$key = s;
		val$value = obj;
		val$opt = ratortype;
		super(null);
	}
}
