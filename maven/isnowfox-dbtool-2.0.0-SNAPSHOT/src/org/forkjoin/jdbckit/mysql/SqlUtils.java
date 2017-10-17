// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SqlUtils.java

package org.forkjoin.jdbckit.mysql;

import java.util.Iterator;
import java.util.List;

// Referenced classes of package org.forkjoin.jdbckit.mysql:
//			Column, Table

public class SqlUtils {

	public SqlUtils() {
	}

	public String toInsertSql(Table t) {
		StringBuilder sb = new StringBuilder("INSERT INTO `");
		sb.append(t.getDbName());
		sb.append("` (");
		toSqlColumn(t, sb);
		sb.append(") VALUES (");
		toSqlColumnPlaceholder(t, sb);
		sb.append(')');
		return sb.toString();
	}

	public String toReplaceSql(Table t) {
		StringBuilder sb = new StringBuilder("REPLACE INTO `");
		sb.append(t.getDbName());
		sb.append("` (");
		toSqlAllColumn(t, sb);
		sb.append(") VALUES (");
		toSqlAllColumnPlaceholder(t, sb);
		sb.append(')');
		return sb.toString();
	}

	public String toInsertPrefix(Table t) {
		StringBuilder sb = new StringBuilder("INSERT INTO `");
		sb.append(t.getDbName());
		sb.append("` (");
		toSqlColumn(t, sb);
		sb.append(") VALUES ");
		return sb.toString();
	}

	public String toInsertValueItems(Table t) {
		StringBuilder sb = new StringBuilder(" (");
		toSqlColumnPlaceholder(t, sb);
		sb.append(") ");
		return sb.toString();
	}

	public String toUpdateTable(Table t) {
		StringBuilder sb = new StringBuilder("UPDATE `");
		sb.append(t.getDbName());
		sb.append("` SET ");
		toSqlSetlColumn(t, sb);
		sb.append(" WHERE ");
		toSqlKeyWhere(t, sb);
		return sb.toString();
	}

	public String toUpdatePartialPrefix(Table t) {
		StringBuilder sb = new StringBuilder("UPDATE `");
		sb.append(t.getDbName());
		sb.append("` SET ");
		return sb.toString();
	}

	public String toWhereByKey(Table t) {
		StringBuilder sb = new StringBuilder(" WHERE ");
		toSqlKeyWhere(t, sb);
		return sb.toString();
	}

	public String toDelete(Table t) {
		StringBuilder sb = new StringBuilder("DELETE FROM `");
		sb.append(t.getDbName());
		sb.append("` WHERE ");
		toSqlKeyWhere(t, sb);
		return sb.toString();
	}

	public String toSelectByKey(Table t) {
		StringBuilder sb = new StringBuilder("SELECT * FROM `");
		sb.append(t.getDbName());
		sb.append("` WHERE ");
		toSqlKeyWhere(t, sb);
		if (t.isKey()) {
			sb.append(" ORDER BY ");
		}
		toSqlOrderByKey(t, sb);
		return sb.toString();
	}

	public String toSelectCount(Table t) {
		StringBuilder sb = new StringBuilder("SELECT count(*) FROM `");
		sb.append(t.getDbName());
		sb.append('`');
		return sb.toString();
	}

	public String toFormatSelectAll(Table t) {
		StringBuilder sb = new StringBuilder("SELECT %s FROM `");
		sb.append(t.getDbName());
		sb.append('`');
		if (t.isKey()) {
			sb.append(" ORDER BY ");
		}
		toSqlOrderByKey(t, sb);
		return sb.toString();
	}

	public String toFormatSelectPrefix(Table t) {
		StringBuilder sb = new StringBuilder("SELECT %s FROM `");
		sb.append(t.getDbName());
		sb.append("` ");
		return sb.toString();
	}

	public String toSelectPrefix(Table t) {
		StringBuilder sb = new StringBuilder("SELECT * FROM `");
		sb.append(t.getDbName());
		sb.append("` ");
		return sb.toString();
	}

	public String toOrderBykey(Table t) {
		StringBuilder sb = new StringBuilder(" ORDER BY ");
		toSqlOrderByKey(t, sb);
		return sb.toString();
	}

	public void toSqlOrderByKey(Table t, StringBuilder sb) {
		boolean first = true;
		for (Iterator iterator = t.getKeyColumns().iterator(); iterator.hasNext(); sb.append("` DESC")) {
			Column c = (Column)iterator.next();
			if (first) {
				first = false;
			} else {
				sb.append(",");
			}
			sb.append('`');
			sb.append(c.getDbName());
		}

	}

	public void toSqlKeyWhere(Table t, StringBuilder sb) {
		boolean first = true;
		for (Iterator iterator = t.getKeyColumns().iterator(); iterator.hasNext(); sb.append("`=?")) {
			Column c = (Column)iterator.next();
			if (first) {
				first = false;
			} else {
				sb.append(" AND ");
			}
			sb.append('`');
			sb.append(c.getDbName());
		}

	}

	public void toSqlSetlColumn(Table t, StringBuilder sb) {
		if (t.getAllColumns().isEmpty()) {
			return;
		}
		boolean del = false;
		for (Iterator iterator = t.getNoIdColumns().iterator(); iterator.hasNext();) {
			Column c = (Column)iterator.next();
			sb.append('`');
			sb.append(c.getDbName());
			sb.append("`=?,");
			del = true;
		}

		if (del) {
			sb.setLength(sb.length() - 1);
		}
	}

	public void toSqlColumn(Table t, StringBuilder sb) {
		if (t.getAllColumns().isEmpty()) {
			return;
		}
		boolean del = false;
		Iterator iterator = t.getAllColumns().iterator();
		do {
			if (!iterator.hasNext()) {
				break;
			}
			Column c = (Column)iterator.next();
			if (!c.isAutoIncrement()) {
				sb.append('`');
				sb.append(c.getDbName());
				sb.append("`,");
				del = true;
			}
		} while (true);
		if (del) {
			sb.setLength(sb.length() - 1);
		}
	}

	public void toSqlAllColumn(Table t, StringBuilder sb) {
		if (t.getAllColumns().isEmpty()) {
			return;
		}
		boolean del = false;
		for (Iterator iterator = t.getAllColumns().iterator(); iterator.hasNext();) {
			Column c = (Column)iterator.next();
			sb.append('`');
			sb.append(c.getDbName());
			sb.append("`,");
			del = true;
		}

		if (del) {
			sb.setLength(sb.length() - 1);
		}
	}

	public void toSqlColumnPlaceholder(Table t, StringBuilder sb) {
		if (t.getAllColumns().isEmpty()) {
			return;
		}
		boolean del = false;
		Iterator iterator = t.getAllColumns().iterator();
		do {
			if (!iterator.hasNext()) {
				break;
			}
			Column c = (Column)iterator.next();
			if (!c.isAutoIncrement()) {
				sb.append("?,");
				del = true;
			}
		} while (true);
		if (del) {
			sb.setLength(sb.length() - 1);
		}
	}

	public void toSqlAllColumnPlaceholder(Table t, StringBuilder sb) {
		if (t.getAllColumns().isEmpty()) {
			return;
		}
		boolean del = false;
		for (Iterator iterator = t.getAllColumns().iterator(); iterator.hasNext();) {
			Column c = (Column)iterator.next();
			sb.append("?,");
			del = true;
		}

		if (del) {
			sb.setLength(sb.length() - 1);
		}
	}
}
