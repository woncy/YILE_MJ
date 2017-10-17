// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Column.java

package org.forkjoin.jdbckit.mysql;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

// Referenced classes of package org.forkjoin.jdbckit.mysql:
//			NameUtils

public class Column {
	public static final class OBJECT_TYPE extends Enum {

		public static final OBJECT_TYPE NORMAL;
		public static final OBJECT_TYPE XML;
		public static final OBJECT_TYPE JSON;
		private static final OBJECT_TYPE $VALUES[];

		public static OBJECT_TYPE[] values() {
			return (OBJECT_TYPE[])$VALUES.clone();
		}

		public static OBJECT_TYPE valueOf(String name) {
			return (OBJECT_TYPE)Enum.valueOf(org/forkjoin/jdbckit/mysql/Column$OBJECT_TYPE, name);
		}

		static  {
			NORMAL = new OBJECT_TYPE("NORMAL", 0);
			XML = new OBJECT_TYPE("XML", 1);
			JSON = new OBJECT_TYPE("JSON", 2);
			$VALUES = (new OBJECT_TYPE[] {
				NORMAL, XML, JSON
			});
		}

		private OBJECT_TYPE(String s, int i) {
			super(s, i);
		}
	}


	private String name;
	private String remark;
	private String typeName;
	private int type;
	private boolean autoIncrement;
	private boolean nullable;
	private int size;
	private boolean isUnique;
	private String mapClassName;
	private String mapClassNameGeneric;
	private OBJECT_TYPE objectType;
	private int seq;
	private static final Pattern GENERIC_PATTERN = Pattern.compile("([^<]+)<[^>]+>.*");

	public Column() {
	}

	public String getSetName() {
		return (new StringBuilder()).append("set").append(NameUtils.toClassName(name)).toString();
	}

	public String getConstantName() {
		return name.toUpperCase();
	}

	public String getFieldName() {
		return NameUtils.toFieldName(name);
	}

	public String getGetName() {
		return (new StringBuilder()).append("get").append(NameUtils.toClassName(name)).toString();
	}

	public String getDbName() {
		return name;
	}

	public boolean isDateColumn() {
		if (!StringUtils.isNotBlank(remark));
		return false;
	}

	public String getResultGetMethod() {
		switch (type) {
		case -6: 
			return "getByte";

		case 5: // '\005'
			return "getShort";

		case -5: 
			return "getLong";

		case -7: 
			return "getBoolean";

		case -4: 
		case 2004: 
			return "getBlob";

		case 16: // '\020'
			return "getBoolean";

		case 1: // '\001'
			return "getString";

		case 2005: 
			return "getClob";

		case 91: // '['
			return "getDate";

		case 3: // '\003'
			return "getBigDecimal";

		case 2: // '\002'
			return "getBigDecimal";

		case 8: // '\b'
			return "getDouble";

		case 6: // '\006'
			return "getFloat";

		case 4: // '\004'
			return "getInt";

		case 92: // '\\'
			return "getTime";

		case 93: // ']'
			return "getTimestamp";

		case 12: // '\f'
			return "getString";

		case -16: 
			return "getString";

		case -1: 
			return "getString";

		case 7: // '\007'
			if (typeName.equals("FLOAT")) {
				return "getFloat";
			} else {
				throw new RuntimeException("未实现");
			}
		}
		return "getObject";
	}

	public void setMapClassNameGeneric(String mapClassNameGeneric) {
		this.mapClassNameGeneric = mapClassNameGeneric;
	}

	public String getInitString() {
		if (nullable) {
			return "null";
		}
		switch (type) {
		case -6: 
		case -5: 
		case 4: // '\004'
		case 5: // '\005'
		case 6: // '\006'
		case 8: // '\b'
			return "0";

		case 16: // '\020'
			return "false";

		case 7: // '\007'
			if (typeName.equals("FLOAT")) {
				return "0";
			} else {
				throw new RuntimeException("未实现");
			}

		case -4: 
		case -3: 
		case -2: 
		case -1: 
		case 0: // '\0'
		case 1: // '\001'
		case 2: // '\002'
		case 3: // '\003'
		case 9: // '\t'
		case 10: // '\n'
		case 11: // '\013'
		case 12: // '\f'
		case 13: // '\r'
		case 14: // '\016'
		case 15: // '\017'
		default:
			return "null";
		}
	}

	public String getWrapClassName() {
		if (mapClassName != null) {
			return mapClassName;
		}
		switch (type) {
		case -6: 
			return "Byte";

		case 5: // '\005'
			return "Short";

		case -5: 
			return "Long";

		case -7: 
			return "Boolean";

		case -4: 
		case 2004: 
			return "java.sql.Blob";

		case 16: // '\020'
			return "Boolean";

		case 1: // '\001'
			return "String";

		case 2005: 
			return "java.sql.Clob";

		case 91: // '['
			return "java.sql.Date";

		case 3: // '\003'
			return "java.math.BigDecimal";

		case 2: // '\002'
			return "java.math.BigDecimal";

		case 8: // '\b'
			return "Double";

		case 6: // '\006'
			return "Float";

		case 4: // '\004'
			return "Integer";

		case 92: // '\\'
			return "java.sql.Time";

		case 93: // ']'
			return "java.util.Date";

		case 12: // '\f'
			return "String";

		case -16: 
			return "String";

		case -1: 
			return "String";

		case 7: // '\007'
			if (typeName.equals("FLOAT")) {
				return "Float";
			} else {
				throw new RuntimeException("未实现");
			}
		}
		return "Object";
	}

	public String getClassNameNoGeneric() {
		if (mapClassNameGeneric != null) {
			return mapClassNameGeneric;
		} else {
			return getClassName();
		}
	}

	public String getClassName() {
		if (mapClassName != null) {
			return mapClassName;
		}
		switch (type) {
		case -6: 
			return nullable ? "Byte" : "byte";

		case 5: // '\005'
			return nullable ? "Short" : "short";

		case -5: 
			return nullable ? "Long" : "long";

		case -7: 
			return nullable ? "Boolean" : "boolean";

		case -4: 
		case 2004: 
			return "java.sql.Blob";

		case 16: // '\020'
			return nullable ? "Boolean" : "boolean";

		case 1: // '\001'
			return "String";

		case 2005: 
			return "java.sql.Clob";

		case 91: // '['
			return "java.sql.Date";

		case 3: // '\003'
			return "java.math.BigDecimal";

		case 2: // '\002'
			return "java.math.BigDecimal";

		case 8: // '\b'
			return nullable ? "Double" : "double";

		case 6: // '\006'
			return nullable ? "Float" : "float";

		case 4: // '\004'
			return nullable ? "Integer" : "int";

		case 92: // '\\'
			return "java.sql.Time";

		case 93: // ']'
			return "java.util.Date";

		case 12: // '\f'
			return "String";

		case -16: 
			return "String";

		case -1: 
			return "String";

		case 7: // '\007'
			if (typeName.equals("FLOAT")) {
				return nullable ? "Float" : "float";
			} else {
				throw new RuntimeException("未实现");
			}
		}
		return "Object";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public boolean isUnique() {
		return isUnique;
	}

	public void setUnique(boolean isUnique) {
		this.isUnique = isUnique;
	}

	public String getMapClassName() {
		return mapClassName;
	}

	public void setMapClassName(String mapClassName) {
		this.mapClassName = mapClassName;
		Matcher m = GENERIC_PATTERN.matcher(mapClassName);
		if (m.find()) {
			setMapClassNameGeneric(m.group(1));
		}
	}

	public int getObjectType() {
		return objectType.ordinal();
	}

	public void setObjectType(OBJECT_TYPE objectType) {
		this.objectType = objectType;
	}

	public String toString() {
		return (new StringBuilder()).append("Column [name=").append(name).append(", remark=").append(remark).append(", typeName=").append(typeName).append(", type=").append(type).append(", autoIncrement=").append(autoIncrement).append(", nullable=").append(nullable).append(", size=").append(size).append(", isUnique=").append(isUnique).append(", mapClassName=").append(mapClassName).append(", objectType=").append(objectType).append(", seq=").append(seq).append("]").toString();
	}

}
