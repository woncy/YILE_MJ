// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Field.java

package org.forkjoin.core.dao;


// Referenced classes of package org.forkjoin.core.dao:
//			SqlUtils

public final class Field {

	public static final Field ALL_FIELDS = new Field("*");
	private final String value;

	private Field(String value) {
		this.value = value;
	}

	public static transient Field[] forms(String names[]) {
		Field fields[] = new Field[names.length];
		for (int i = 0; i < names.length; i++) {
			fields[i] = form(names[i]);
		}

		return fields;
	}

	public static Field form(String name) {
		return new Field((new StringBuilder()).append("`").append(SqlUtils.nameFilter(name)).append("`").toString());
	}

	public static Field form(String tableName, String name) {
		return new Field((new StringBuilder()).append("`").append(SqlUtils.nameFilter(tableName)).append("`.`").append(SqlUtils.nameFilter(name)).append("`").toString());
	}

	public static Field formAliasName(String name, String filedAliasName) {
		return new Field((new StringBuilder()).append("`").append(SqlUtils.nameFilter(name)).append("` `").append(SqlUtils.nameFilter(filedAliasName)).append("`").toString());
	}

	public static Field form(String tableName, String name, String filedAliasName) {
		return new Field((new StringBuilder()).append("`").append(SqlUtils.nameFilter(tableName)).append("`.`").append(SqlUtils.nameFilter(name)).append("` `").append(SqlUtils.nameFilter(filedAliasName)).append("`").toString());
	}

	public String getValue() {
		return value;
	}

	public static Field sum(String name) {
		return fun("SUM", name);
	}

	public static Field sum(String tableName, String name) {
		return fun("SUM", tableName, name);
	}

	public static Field sumAliasName(String name, String filedAliasName) {
		return funAliasName("SUM", name, filedAliasName);
	}

	public static Field sum(String tableName, String name, String filedAliasName) {
		return fun("SUM", tableName, name, filedAliasName);
	}

	public static Field count(String name) {
		return fun("COUNT", name);
	}

	public static Field count(String tableName, String name) {
		return fun("COUNT", tableName, name);
	}

	public static Field countAliasName(String name, String filedAliasName) {
		return funAliasName("COUNT", name, filedAliasName);
	}

	public static Field count(String tableName, String name, String filedAliasName) {
		return fun("COUNT", tableName, name, filedAliasName);
	}

	public static Field max(String name) {
		return fun("MAX", name);
	}

	public static Field max(String tableName, String name) {
		return fun("MAX", tableName, name);
	}

	public static Field maxAliasName(String name, String filedAliasName) {
		return funAliasName("MAX", name, filedAliasName);
	}

	public static Field max(String tableName, String name, String filedAliasName) {
		return fun("MAX", tableName, name, filedAliasName);
	}

	public static Field min(String name) {
		return fun("MIN", name);
	}

	public static Field min(String tableName, String name) {
		return fun("MIN", tableName, name);
	}

	public static Field minAliasName(String name, String filedAliasName) {
		return funAliasName("MIN", name, filedAliasName);
	}

	public static Field min(String tableName, String name, String filedAliasName) {
		return fun("MIN", tableName, name, filedAliasName);
	}

	public static Field fun(String fun, String name) {
		return new Field((new StringBuilder()).append(fun).append("(`").append(SqlUtils.nameFilter(name)).append("`)").toString());
	}

	public static Field fun(String fun, String tableName, String name) {
		return new Field((new StringBuilder()).append(fun).append("(`").append(SqlUtils.nameFilter(tableName)).append("`.`").append(SqlUtils.nameFilter(name)).append("`)").toString());
	}

	public static Field funAliasName(String fun, String name, String filedAliasName) {
		return new Field((new StringBuilder()).append(fun).append("(`").append(SqlUtils.nameFilter(name)).append("`) `").append(SqlUtils.nameFilter(filedAliasName)).append("`").toString());
	}

	public static Field fun(String fun, String tableName, String name, String filedAliasName) {
		return new Field((new StringBuilder()).append(fun).append("(`").append(SqlUtils.nameFilter(tableName)).append("`.`").append(SqlUtils.nameFilter(name)).append("`) `").append(SqlUtils.nameFilter(filedAliasName)).append("`").toString());
	}

}
