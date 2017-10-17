// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SpringXmlCreate.java

package org.forkjoin.jdbckit.mysql;


// Referenced classes of package org.forkjoin.jdbckit.mysql:
//			SpringXmlCreate

public static final class SpringXmlCreate$Type extends Enum {

	public static final SpringXmlCreate$Type BASE;
	public static final SpringXmlCreate$Type READ_ONLY;
	public static final SpringXmlCreate$Type CACHE;
	final String contextFileName;
	final String packName;
	final String anotherName;
	private static final SpringXmlCreate$Type $VALUES[];

	public static SpringXmlCreate$Type[] values() {
		return (SpringXmlCreate$Type[])$VALUES.clone();
	}

	public static SpringXmlCreate$Type valueOf(String name) {
		return (SpringXmlCreate$Type)Enum.valueOf(org/forkjoin/jdbckit/mysql/SpringXmlCreate$Type, name);
	}

	static  {
		BASE = new SpringXmlCreate$Type("BASE", 0, "DaoContext.xml", "", "");
		READ_ONLY = new SpringXmlCreate$Type("READ_ONLY", 1, "ReadOnlyDaoContext.xml", ".readonly", "ReadOnly");
		CACHE = new SpringXmlCreate$Type("CACHE", 2, "CacheDaoContext.xml", ".cache", "Cache");
		$VALUES = (new SpringXmlCreate$Type[] {
			BASE, READ_ONLY, CACHE
		});
	}

	private SpringXmlCreate$Type(String s, int i, String contextFileName, String packName, String anotherName) {
		super(s, i);
		this.contextFileName = contextFileName;
		this.packName = packName;
		this.anotherName = anotherName;
	}
}
