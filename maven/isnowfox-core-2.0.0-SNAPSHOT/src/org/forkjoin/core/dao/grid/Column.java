// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Column.java

package org.forkjoin.core.dao.grid;


public class Column {

	public String text;
	public String code;
	public int sqlType;
	public Class javaType;
	private int columnDisplaySize;

	public Column(String text, String code, int sqlType, Class javaType) {
		this.text = text;
		this.code = code;
		this.sqlType = sqlType;
		this.javaType = javaType;
	}

	public Column(String text, String code, int sqlType, String javaType, int columnDisplaySize) throws ClassNotFoundException {
		this.text = text;
		this.code = code;
		this.sqlType = sqlType;
		this.javaType = ClassLoader.getSystemClassLoader().loadClass(javaType);
		this.columnDisplaySize = columnDisplaySize;
	}

	public void setColumnDisplaySize(int columnDisplaySize) {
		this.columnDisplaySize = columnDisplaySize;
	}

	public int getColumnDisplaySize() {
		return columnDisplaySize;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSqlType() {
		return sqlType;
	}

	public void setSqlType(int sqlType) {
		this.sqlType = sqlType;
	}

	public Class getJavaType() {
		return javaType;
	}

	public void setJavaType(Class javaType) {
		this.javaType = javaType;
	}

	public String toString() {
		return (new StringBuilder()).append("Column [text=").append(text).append(", code=").append(code).append(", sqlType=").append(sqlType).append(", javaType=").append(javaType).append(", columnDisplaySize=").append(columnDisplaySize).append("]").toString();
	}
}
