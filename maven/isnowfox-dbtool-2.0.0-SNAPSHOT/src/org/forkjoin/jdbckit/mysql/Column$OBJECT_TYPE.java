// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Column.java

package org.forkjoin.jdbckit.mysql;


// Referenced classes of package org.forkjoin.jdbckit.mysql:
//			Column

public static final class Column$OBJECT_TYPE extends Enum {

	public static final Column$OBJECT_TYPE NORMAL;
	public static final Column$OBJECT_TYPE XML;
	public static final Column$OBJECT_TYPE JSON;
	private static final Column$OBJECT_TYPE $VALUES[];

	public static Column$OBJECT_TYPE[] values() {
		return (Column$OBJECT_TYPE[])$VALUES.clone();
	}

	public static Column$OBJECT_TYPE valueOf(String name) {
		return (Column$OBJECT_TYPE)Enum.valueOf(org/forkjoin/jdbckit/mysql/Column$OBJECT_TYPE, name);
	}

	static  {
		NORMAL = new Column$OBJECT_TYPE("NORMAL", 0);
		XML = new Column$OBJECT_TYPE("XML", 1);
		JSON = new Column$OBJECT_TYPE("JSON", 2);
		$VALUES = (new Column$OBJECT_TYPE[] {
			NORMAL, XML, JSON
		});
	}

	private Column$OBJECT_TYPE(String s, int i) {
		super(s, i);
	}
}
