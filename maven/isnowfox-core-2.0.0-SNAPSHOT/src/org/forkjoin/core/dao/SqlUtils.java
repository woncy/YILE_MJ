// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SqlUtils.java

package org.forkjoin.core.dao;


public class SqlUtils {

	public static final String STRING_COUNT = " count(1) ";

	public SqlUtils() {
	}

	public static String fieldFilter(String sql) {
		return sql.trim().replace("'", "''");
	}

	public static String fieldFilter(Object obj) {
		return obj != null ? fieldFilter(obj.toString()) : "";
	}

	public static String nameFilter(String sql) {
		return sql.trim().replace("`", "``");
	}
}
