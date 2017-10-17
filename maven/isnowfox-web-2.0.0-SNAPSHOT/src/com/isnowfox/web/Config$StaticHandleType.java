// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Config.java

package com.isnowfox.web;


// Referenced classes of package com.isnowfox.web:
//			Config

public static final class Config$StaticHandleType extends Enum {

	public static final Config$StaticHandleType NONE;
	public static final Config$StaticHandleType CACHE;
	public static final Config$StaticHandleType NOT_CACHE;
	private static final Config$StaticHandleType $VALUES[];

	public static Config$StaticHandleType[] values() {
		return (Config$StaticHandleType[])$VALUES.clone();
	}

	public static Config$StaticHandleType valueOf(String name) {
		return (Config$StaticHandleType)Enum.valueOf(com/isnowfox/web/Config$StaticHandleType, name);
	}

	static  {
		NONE = new Config$StaticHandleType("NONE", 0);
		CACHE = new Config$StaticHandleType("CACHE", 1);
		NOT_CACHE = new Config$StaticHandleType("NOT_CACHE", 2);
		$VALUES = (new Config$StaticHandleType[] {
			NONE, CACHE, NOT_CACHE
		});
	}

	private Config$StaticHandleType(String s, int i) {
		super(s, i);
	}
}
