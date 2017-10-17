// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Select.java

package org.forkjoin.core.dao;

import org.apache.commons.lang3.ArrayUtils;

// Referenced classes of package org.forkjoin.core.dao:
//			Field, SqlUtils, QueryParams, Order, 
//			QueryParam

public class Select {

	private final Field fields[];
	private String tableName;
	private String tableAliasName;
	private QueryParams queryParams;
	private Order order;
	private Field groupFields[];

	public Select() {
		this(new Field[] {
			Field.ALL_FIELDS
		});
	}

	public transient Select(Field fields[]) {
		this.fields = fields;
	}

	public transient Select(String fields[]) {
		this.fields = Field.forms(fields);
	}

	public Select from(String tableName) {
		this.tableName = tableName;
		return this;
	}

	public Select from(String tableName, String tableAliasName) {
		this.tableName = tableName;
		this.tableAliasName = tableAliasName;
		return this;
	}

	public Select where(QueryParams params) {
		queryParams = params;
		return this;
	}

	public Select where(String key, Object value) {
		return where(QueryParams.single(key, value, QueryParam.OperatorType.EQ, false));
	}

	public Select where(String key, Object value, QueryParam.OperatorType opt) {
		return where(QueryParams.single(key, value, opt, false));
	}

	public Select where(String key, Object value, QueryParam.OperatorType opt, boolean not) {
		return where(QueryParams.single(key, value, opt, not));
	}

	public Select orderBy(Order order) {
		this.order = order;
		return this;
	}

	public Select orderBy(String name) {
		order = Order.createSingleton(name, true);
		return this;
	}

	public Select orderBy(String name, boolean isDesc) {
		order = Order.createSingleton(name, isDesc);
		return this;
	}

	public Select orderByDesc(String name) {
		order = Order.desc(name);
		return this;
	}

	public Select orderByAsc(String name) {
		order = Order.asc(name);
		return this;
	}

	public transient Select groupBy(Field fields[]) {
		groupFields = fields;
		return this;
	}

	public transient Select groupByNames(String fields[]) {
		groupFields = Field.forms(fields);
		return this;
	}

	public Select groupBy(String name) {
		groupFields = Field.forms(new String[] {
			name
		});
		return this;
	}

	public Select groupBy(String tableName, String name) {
		return groupBy(Field.forms(new String[] {
			tableName, name
		}));
	}

	public Select groupByAliasName(String name, String filedAliasName) {
		return groupBy(new Field[] {
			Field.formAliasName(name, filedAliasName)
		});
	}

	public Select groupBy(String tableName, String name, String filedAliasName) {
		return groupBy(Field.forms(new String[] {
			tableName, name, filedAliasName
		}));
	}

	public String toSql() {
		return toSql(new StringBuilder()).toString();
	}

	public StringBuilder toSql(StringBuilder sb) {
		return toSql(sb, false);
	}

	public String toCountSql() {
		return toCountSql(new StringBuilder()).toString();
	}

	public StringBuilder toCountSql(StringBuilder sb) {
		return toSql(sb, true);
	}

	public StringBuilder toSql(StringBuilder sb, boolean isCount) {
		boolean isGroup = ArrayUtils.isNotEmpty(groupFields);
		sb.append("SELECT ");
		if (isCount) {
			if (isGroup) {
				sb.append("count(distinct ");
				for (int i = 0; i < groupFields.length; i++) {
					if (i > 0) {
						sb.append(", ");
					}
					sb.append(groupFields[i].getValue());
				}

				sb.append(")");
			} else {
				sb.append(" count(1) ");
			}
		} else {
			for (int i = 0; i < fields.length; i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(fields[i].getValue());
			}

		}
		sb.append(" FROM ");
		sb.append("`").append(SqlUtils.nameFilter(tableName)).append("` ");
		if (tableAliasName != null) {
			sb.append("`").append(SqlUtils.nameFilter(tableAliasName)).append("` ");
		}
		if (queryParams != null) {
			queryParams.toSql(sb);
		}
		if (isCount) {
			return sb;
		}
		if (isGroup) {
			sb.append(" GROUP BY ");
			for (int i = 0; i < groupFields.length; i++) {
				if (i > 0) {
					sb.append(", ");
				}
				sb.append(groupFields[i].getValue());
			}

		}
		if (order != null) {
			order.toSql(sb);
		}
		return sb;
	}

	public Object[] toParams() {
		return queryParams != null ? queryParams.toParams() : ArrayUtils.EMPTY_OBJECT_ARRAY;
	}
}
