// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CacheType.java

package com.isnowfox.web;


public final class CacheType extends Enum {

	public static final CacheType EXPIRES;
	public static final CacheType CACHE_CONTROL;
	public static final CacheType PUBLIC;
	public static final CacheType PRIVATE;
	public static final CacheType NO_CACHE;
	public static final CacheType NO_STORE;
	public static final CacheType MAX_AGE;
	public static final CacheType MIN_FRESH;
	public static final CacheType MAX_STALE;
	private static final CacheType $VALUES[];

	public static CacheType[] values() {
		return (CacheType[])$VALUES.clone();
	}

	public static CacheType valueOf(String name) {
		return (CacheType)Enum.valueOf(com/isnowfox/web/CacheType, name);
	}

	private CacheType(String s, int i) {
		super(s, i);
	}

	static  {
		EXPIRES = new CacheType("EXPIRES", 0);
		CACHE_CONTROL = new CacheType("CACHE_CONTROL", 1);
		PUBLIC = new CacheType("PUBLIC", 2);
		PRIVATE = new CacheType("PRIVATE", 3);
		NO_CACHE = new CacheType("NO_CACHE", 4);
		NO_STORE = new CacheType("NO_STORE", 5);
		MAX_AGE = new CacheType("MAX_AGE", 6);
		MIN_FRESH = new CacheType("MIN_FRESH", 7);
		MAX_STALE = new CacheType("MAX_STALE", 8);
		$VALUES = (new CacheType[] {
			EXPIRES, CACHE_CONTROL, PUBLIC, PRIVATE, NO_CACHE, NO_STORE, MAX_AGE, MIN_FRESH, MAX_STALE
		});
	}
}
