// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   QueryParams.java

package org.forkjoin.core.dao;

import com.google.common.collect.Lists;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

// Referenced classes of package org.forkjoin.core.dao:
//			QueryParam, SqlUtils

public class QueryParams {

	private List params;

	public static QueryParams create() {
		return new QueryParams();
	}

	public static QueryParams single(String key, Object value) {
		return single(key, value, QueryParam.OperatorType.EQ, false);
	}

	public static QueryParams single(String key, Object value, QueryParam.OperatorType opt) {
		return single(key, value, opt, false);
	}

	public static QueryParams single(String key, Object value, QueryParam.OperatorType opt, boolean not) {
		return new QueryParams(not, key, value, opt) {

			final boolean val$not;
			final String val$key;
			final Object val$value;
			final QueryParam.OperatorType val$opt;

			public void toSql(StringBuilder sb) {
				sb.append(" WHERE ");
				if (not) {
					sb.append(" NOT (");
				}
				sb.append('`');
				sb.append(key);
				sb.append('`');
				QueryParams.toSqlWhereOpt(sb, value, opt);
				if (not) {
					sb.append(") ");
				}
			}

			public int setPreparedStatement(PreparedStatement ps, int i) throws SQLException {
				if (opt != QueryParam.OperatorType.IS_NULL && opt != QueryParam.OperatorType.IS_NOT_NULL) {
					ps.setObject(i++, value);
				}
				return i;
			}

			public Object[] toParams() {
				return (new Object[] {
					value
				});
			}

			public boolean isEmpty() {
				return false;
			}

			 {
				not = flag;
				key = s;
				value = obj;
				opt = operatortype;
				super(null);
			}
		};
	}

	private QueryParams() {
		params = Lists.newArrayList();
	}

	public QueryParams add(String key, Object value) {
		QueryParam p = new QueryParam(key, value);
		params.add(p);
		return this;
	}

	public QueryParams add(String key, Object value, QueryParam.OperatorType opt) {
		QueryParam p = new QueryParam(key, value, opt);
		params.add(p);
		return this;
	}

	public QueryParams add(String key, Object value, QueryParam.OperatorType opt, boolean not) {
		QueryParam p = new QueryParam(key, value, opt, true, not);
		params.add(p);
		return this;
	}

	public QueryParams or(String key, Object value) {
		QueryParam p = new QueryParam(key, value);
		params.add(p);
		p.and = false;
		return this;
	}

	public QueryParams or(String key, Object value, QueryParam.OperatorType opt) {
		QueryParam p = new QueryParam(key, value, opt, false);
		params.add(p);
		p.and = false;
		return this;
	}

	public QueryParams or(String key, Object value, QueryParam.OperatorType opt, boolean not) {
		QueryParam p = new QueryParam(key, value, opt, false, not);
		params.add(p);
		p.and = false;
		return this;
	}

	private static final void toSqlWhereOpt(StringBuilder sb, Object value, QueryParam.OperatorType opt) {
		static class _cls2 {

			static final int $SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[];

			static  {
				$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType = new int[QueryParam.OperatorType.values().length];
				try {
					$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[QueryParam.OperatorType.EQ.ordinal()] = 1;
				}
				catch (NoSuchFieldError nosuchfielderror) { }
				try {
					$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[QueryParam.OperatorType.LIKE.ordinal()] = 2;
				}
				catch (NoSuchFieldError nosuchfielderror1) { }
				try {
					$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[QueryParam.OperatorType.LIKE_BOTH.ordinal()] = 3;
				}
				catch (NoSuchFieldError nosuchfielderror2) { }
				try {
					$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[QueryParam.OperatorType.LT.ordinal()] = 4;
				}
				catch (NoSuchFieldError nosuchfielderror3) { }
				try {
					$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[QueryParam.OperatorType.GT.ordinal()] = 5;
				}
				catch (NoSuchFieldError nosuchfielderror4) { }
				try {
					$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[QueryParam.OperatorType.LE.ordinal()] = 6;
				}
				catch (NoSuchFieldError nosuchfielderror5) { }
				try {
					$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[QueryParam.OperatorType.NOT.ordinal()] = 7;
				}
				catch (NoSuchFieldError nosuchfielderror6) { }
				try {
					$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[QueryParam.OperatorType.GE.ordinal()] = 8;
				}
				catch (NoSuchFieldError nosuchfielderror7) { }
				try {
					$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[QueryParam.OperatorType.IS_NULL.ordinal()] = 9;
				}
				catch (NoSuchFieldError nosuchfielderror8) { }
				try {
					$SwitchMap$org$forkjoin$core$dao$QueryParam$OperatorType[QueryParam.OperatorType.IS_NOT_NULL.ordinal()] = 10;
				}
				catch (NoSuchFieldError nosuchfielderror9) { }
			}
		}

		switch (_cls2..SwitchMap.org.forkjoin.core.dao.QueryParam.OperatorType[opt.ordinal()]) {
		case 1: // '\001'
			sb.append(" = ? ");
			break;

		case 2: // '\002'
			sb.append(" LIKE ? ");
			// fall through

		case 3: // '\003'
			sb.append(" LIKE '%").append(SqlUtils.fieldFilter(value)).append("%'");
			break;

		case 4: // '\004'
			sb.append(" < ? ");
			break;

		case 5: // '\005'
			sb.append(" > ? ");
			break;

		case 6: // '\006'
			sb.append(" <= ? ");
			break;

		case 7: // '\007'
			sb.append(" != ? ");
			break;

		case 8: // '\b'
			sb.append(" >= ? ");
			break;

		case 9: // '\t'
			sb.append(" IS NULL ");
			break;

		case 10: // '\n'
			sb.append(" IS NOT NULL ");
			break;
		}
	}

	public String toSql() {
		StringBuilder sb = new StringBuilder();
		toSql(sb);
		return sb.toString();
	}

	public void toSql(StringBuilder sb) {
		if (params.isEmpty()) {
			return;
		}
		sb.append(" WHERE ");
		boolean fist = true;
		Iterator iterator = params.iterator();
		do {
			if (!iterator.hasNext()) {
				break;
			}
			QueryParam p = (QueryParam)iterator.next();
			String key = p.key;
			if (fist) {
				fist = false;
			} else
			if (p.and) {
				sb.append(" AND ");
			} else {
				sb.append(" OR ");
			}
			if (p.not) {
				sb.append(" NOT (");
			}
			sb.append('`');
			sb.append(key);
			sb.append('`');
			toSqlWhereOpt(sb, p.getValue(), p.getOpt());
			if (p.not) {
				sb.append(") ");
			}
		} while (true);
	}

	public int setPreparedStatement(PreparedStatement ps, int i) throws SQLException {
		Iterator iterator = params.iterator();
		do {
			if (!iterator.hasNext()) {
				break;
			}
			QueryParam p = (QueryParam)iterator.next();
			if (p.opt != QueryParam.OperatorType.IS_NULL && p.opt != QueryParam.OperatorType.IS_NOT_NULL && p.opt != QueryParam.OperatorType.LIKE_BOTH) {
				ps.setObject(i++, p.getValue());
			}
		} while (true);
		return i;
	}

	public Object[] toParams() {
		List list = Lists.newArrayListWithCapacity(params.size());
		Iterator iterator = params.iterator();
		do {
			if (!iterator.hasNext()) {
				break;
			}
			QueryParam p = (QueryParam)iterator.next();
			if (p.opt != QueryParam.OperatorType.IS_NULL && p.opt != QueryParam.OperatorType.IS_NOT_NULL && p.opt != QueryParam.OperatorType.LIKE_BOTH) {
				list.add(p.value);
			}
		} while (true);
		return list.toArray();
	}

	public boolean isEmpty() {
		return params.isEmpty();
	}


}
