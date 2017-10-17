// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CacheConfig.java

package com.isnowfox.web.config;

import com.isnowfox.web.CacheType;
import com.isnowfox.web.annotation.Cache;

public class CacheConfig {

	private CacheType type;
	private String value;

	public CacheConfig(Cache cache) {
		type = cache.type();
		value = cache.value();
	}

	public CacheConfig(CacheType type, String value) {
		this.type = type;
		this.value = value;
	}

	public CacheType getType() {
		return type;
	}

	public void setType(CacheType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return (new StringBuilder()).append("CacheConfig [type=").append(type).append(", value=").append(value).append("]").toString();
	}
}
