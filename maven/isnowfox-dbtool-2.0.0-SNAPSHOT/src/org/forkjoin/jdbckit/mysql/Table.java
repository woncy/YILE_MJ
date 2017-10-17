// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Table.java

package org.forkjoin.jdbckit.mysql;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package org.forkjoin.jdbckit.mysql:
//			Column, UniqueIndex, NameUtils

public class Table {

	private static final Pattern XML_PATTERN = Pattern.compile("XML:([^;]+);{1}(.*)");
	private static final Pattern JSON_PATTERN = Pattern.compile("JSON:([^;]+);{1}(.*)");
	private static final Logger log = LoggerFactory.getLogger(org/forkjoin/jdbckit/mysql/Table);
	private String name;
	private String type;
	private String schema;
	private String remark;
	private List allColumns;
	private Map allMap;
	private List noIdcolumns;
	private List keyColumns;
	private Column keyColumn;
	private boolean oneKey;
	private boolean key;
	private Map uniqueIndexes;
	private String tablePrefix;
	private static Random ran = new Random(System.nanoTime());

	public Table(String tablePrefix, Connection con, DatabaseMetaData dm, String name, String type, String remark) throws SQLException {
		Statement st;
		ResultSet rs;
		ResultSet r;
		ResultSet indexRs;
		allColumns = Lists.newArrayList();
		allMap = Maps.newHashMap();
		noIdcolumns = Lists.newArrayList();
		keyColumns = Lists.newArrayList();
		uniqueIndexes = new HashMap();
		this.tablePrefix = tablePrefix;
		this.name = name;
		this.type = type;
		this.remark = remark;
		st = con.createStatement();
		rs = null;
		r = null;
		indexRs = null;
		log.debug("获取主键信息");
		Column c;
		for (r = dm.getPrimaryKeys(null, null, name); r.next(); allMap.put(c.getName(), c)) {
			c = new Column();
			c.setName(String.valueOf(r.getObject("COLUMN_NAME")));
			c.setSeq(Integer.valueOf(String.valueOf(r.getObject("KEY_SEQ"))).intValue());
			keyColumns.add(c);
		}

		r.close();
		log.debug("执行查询语句:select * from `{}` limit 1", name);
		rs = st.executeQuery((new StringBuilder()).append("select * from `").append(name).append("` limit 1").toString());
		ResultSetMetaData ms = rs.getMetaData();
		schema = ms.getSchemaName(1);
		if (schema == null || schema.length() < 1) {
			schema = ms.getCatalogName(1);
		}
		r = dm.getColumns(null, null, name, null);
		int i = 1;
		Column c;
		for (; r.next(); log.debug("字段信息:{}", c)) {
			String cname = String.valueOf(r.getObject("COLUMN_NAME"));
			c = (Column)allMap.get(cname);
			if (c == null) {
				c = new Column();
				c.setName(cname);
				allMap.put(cname, c);
			}
			allColumns.add(c);
			c.setAutoIncrement(ms.isAutoIncrement(i));
			c.setNullable("yes".equalsIgnoreCase(r.getString("IS_NULLABLE")));
			String cr = r.getString("REMARKS");
			c.setType(r.getInt("DATA_TYPE"));
			c.setSize(r.getInt("COLUMN_SIZE"));
			c.setTypeName(r.getString("TYPE_NAME"));
			Matcher m = XML_PATTERN.matcher(cr);
			if (m.find()) {
				c.setMapClassName(m.group(1));
				c.setRemark(m.group(2));
				c.setObjectType(Column.OBJECT_TYPE.XML);
			} else {
				m = JSON_PATTERN.matcher(cr);
				if (m.find()) {
					c.setMapClassName(m.group(1));
					c.setRemark(m.group(2));
					c.setObjectType(Column.OBJECT_TYPE.JSON);
				} else {
					c.setRemark(cr);
					c.setObjectType(Column.OBJECT_TYPE.NORMAL);
				}
			}
			if (!c.isAutoIncrement()) {
				noIdcolumns.add(c);
			}
			i++;
		}

		if (!keyColumns.isEmpty()) {
			Collections.sort(keyColumns, new Comparator() {

				final Table this$0;

				public int compare(Column o1, Column o2) {
					return Integer.valueOf(o1.getSeq()).compareTo(Integer.valueOf(o2.getSeq()));
				}

				public volatile int compare(Object obj, Object obj1) {
					return compare((Column)obj, (Column)obj1);
				}

			 {
				this.this$0 = Table.this;
				super();
			}
			});
			key = true;
		} else {
			key = false;
		}
		if (keyColumns.size() == 1) {
			oneKey = true;
			keyColumn = (Column)keyColumns.get(0);
		} else
		if (keyColumns.size() > 1) {
			oneKey = false;
			keyColumn = null;
		} else {
			oneKey = false;
			keyColumn = null;
		}
		indexRs = dm.getIndexInfo(null, null, name, true, false);
		do {
			if (!indexRs.next()) {
				break;
			}
			String cname = String.valueOf(indexRs.getObject("COLUMN_NAME"));
			String indexName = String.valueOf(indexRs.getObject("INDEX_NAME"));
			int ordinalPosition = indexRs.getInt("ORDINAL_POSITION");
			addUniqueIndex(cname, indexName, ordinalPosition);
			Column c = (Column)allMap.get(cname);
			if (c != null) {
				c.setUnique(true);
			}
		} while (true);
		if (rs != null) {
			rs.close();
		}
		if (r != null) {
			r.close();
		}
		if (indexRs != null) {
			indexRs.close();
		}
		st.close();
		break MISSING_BLOCK_LABEL_955;
		Exception exception;
		exception;
		if (rs != null) {
			rs.close();
		}
		if (r != null) {
			r.close();
		}
		if (indexRs != null) {
			indexRs.close();
		}
		st.close();
		throw exception;
	}

	private void addUniqueIndex(String cname, String indexName, int ordinalPosition) {
		UniqueIndex uniqueIndex = (UniqueIndex)uniqueIndexes.get(indexName);
		if (uniqueIndex == null) {
			uniqueIndex = new UniqueIndex(indexName);
			uniqueIndexes.put(indexName, uniqueIndex);
		}
		uniqueIndex.addField(cname, ordinalPosition);
	}

	public boolean isHasKeyConstructorArgs() {
		return !keyColumns.isEmpty();
	}

	public String getKeyConstructorArgs() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keyColumns.size(); i++) {
			Column c = (Column)keyColumns.get(i);
			if (sb.length() > 0) {
				sb.append(',');
			}
			sb.append(c.getClassName());
			sb.append(' ');
			sb.append(c.getFieldName());
		}

		return sb.toString();
	}

	public boolean isHasConstructorArgs() {
		return isHasConstructorArgs(allColumns);
	}

	public String getConstructorArgs() {
		return getConstructorArgsString(allColumns);
	}

	public boolean isHasConstructorArgs(List list) {
		for (int i = 0; i < list.size(); i++) {
			Column c = (Column)list.get(i);
			if (!c.isAutoIncrement()) {
				return true;
			}
		}

		return false;
	}

	private String getConstructorArgsString(List list) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			Column c = (Column)list.get(i);
			if (c.isAutoIncrement()) {
				continue;
			}
			if (sb.length() > 0) {
				sb.append(',');
			}
			sb.append(c.getClassName());
			sb.append(' ');
			sb.append(c.getFieldName());
		}

		return sb.toString();
	}

	public String getToString() {
		return getToStringString(allColumns);
	}

	public String getKeyToString() {
		return getToStringString(keyColumns);
	}

	private String getToStringString(List list) {
		StringBuilder sb = new StringBuilder("\"");
		sb.append(getClassName());
		sb.append("[");
		for (int i = 0; i < list.size(); i++) {
			Column c = (Column)list.get(i);
			if (i != 0) {
				sb.append("+\",");
			}
			sb.append(c.getFieldName());
			sb.append(":\"+ ");
			if (java/lang/String.getSimpleName().equals(c.getClassName())) {
				sb.append("(").append(c.getFieldName()).append(" == null ?\"null\":");
				sb.append(c.getFieldName()).append(".substring(0, Math.min(");
				sb.append(c.getFieldName()).append(".length(), 64)))");
			} else {
				sb.append(c.getFieldName());
			}
		}

		sb.append("+ \"]\"");
		return sb.toString();
	}

	public String getColumnNameString() {
		StringBuilder sb = new StringBuilder();
		for (Iterator iterator = noIdcolumns.iterator(); iterator.hasNext(); sb.append(',')) {
			Column c = (Column)iterator.next();
			sb.append('`');
			sb.append(c.getName());
			sb.append('`');
		}

		if (sb.length() > 1) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	public String getColumnDbNameString() {
		StringBuilder sb = new StringBuilder();
		for (Iterator iterator = noIdcolumns.iterator(); iterator.hasNext(); sb.append(',')) {
			Column c = (Column)iterator.next();
			sb.append('#');
			sb.append(c.getDbName());
			sb.append('#');
		}

		if (sb.length() > 1) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	public List getKeyColumns() {
		return keyColumns;
	}

	public boolean isHasDateColumns() {
		for (Iterator iterator = allColumns.iterator(); iterator.hasNext();) {
			Column c = (Column)iterator.next();
			if (c.isDateColumn()) {
				return true;
			}
		}

		return false;
	}

	public String getColumnValuesDbNameString() {
		StringBuilder sb = new StringBuilder();
		for (Iterator iterator = noIdcolumns.iterator(); iterator.hasNext(); sb.append(',')) {
			Column c = (Column)iterator.next();
			sb.append("#values[].");
			sb.append(c.getDbName());
			sb.append("#");
		}

		if (sb.length() > 1) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	public String getColumnUpdateString() {
		StringBuilder sb = new StringBuilder();
		for (Iterator iterator = noIdcolumns.iterator(); iterator.hasNext(); sb.append(',')) {
			Column c = (Column)iterator.next();
			sb.append('`');
			sb.append(c.getName());
			sb.append('`');
			sb.append("=#");
			sb.append(c.getDbName());
			sb.append('#');
		}

		if (sb.length() > 1) {
			sb.setLength(sb.length() - 1);
		}
		return sb.toString();
	}

	public String getClassName() {
		if (tablePrefix != null && name.startsWith(tablePrefix)) {
			return NameUtils.toClassName(name.substring(tablePrefix.length()));
		} else {
			return NameUtils.toClassName(name);
		}
	}

	public String getFieldName() {
		if (tablePrefix != null && name.startsWith(tablePrefix)) {
			return NameUtils.toFieldName(name.substring(tablePrefix.length()));
		} else {
			return NameUtils.toFieldName(name);
		}
	}

	public String getKeysParameters() {
		if (keyColumns.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Column c;
		for (Iterator iterator = keyColumns.iterator(); iterator.hasNext(); sb.append(c.getFieldName())) {
			c = (Column)iterator.next();
			if (sb.length() > 0) {
				sb.append(',');
			}
			sb.append(c.getClassName());
			sb.append(' ');
		}

		return sb.toString();
	}

	public String getMethodParameters() {
		if (keyColumns.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Column c;
		for (Iterator iterator = keyColumns.iterator(); iterator.hasNext(); sb.append(c.getFieldName())) {
			c = (Column)iterator.next();
			if (sb.length() > 0) {
				sb.append(',');
			}
		}

		return sb.toString();
	}

	public String getFinalKeysParameters() {
		if (keyColumns.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		Column c;
		for (Iterator iterator = keyColumns.iterator(); iterator.hasNext(); sb.append(c.getFieldName())) {
			c = (Column)iterator.next();
			if (sb.length() > 0) {
				sb.append(',');
			}
			sb.append("final ");
			sb.append(c.getClassName());
			sb.append(' ');
		}

		return sb.toString();
	}

	public String getDbName() {
		return name;
	}

	public String getSerialVersionUID() {
		return String.valueOf(ran.nextLong());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setAllColumns(List allColumns) {
		this.allColumns = allColumns;
	}

	public List getAllColumns() {
		return allColumns;
	}

	public List getNoIdColumns() {
		return noIdcolumns;
	}

	public Column getKeyColumn() {
		return keyColumn;
	}

	public void setKeyColumn(Column keyColumn) {
		this.keyColumn = keyColumn;
	}

	public boolean isKey() {
		return key;
	}

	public void setKey(boolean key) {
		this.key = key;
	}

	public boolean isOneKey() {
		return oneKey;
	}

	public void setOneKey(boolean oneKey) {
		this.oneKey = oneKey;
	}

	public Map getUniqueIndexes() {
		return uniqueIndexes;
	}

}
