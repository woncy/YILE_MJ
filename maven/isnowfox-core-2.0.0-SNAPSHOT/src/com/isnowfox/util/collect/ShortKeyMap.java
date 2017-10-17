// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ShortKeyMap.java

package com.isnowfox.util.collect;

import java.util.HashMap;
import java.util.Map;

public class ShortKeyMap {

	private final Map map = new HashMap();
	private short max;

	public ShortKeyMap() {
		max = 0;
	}

	public Object get(int i) {
		return map.get(Integer.valueOf(i));
	}

	public short add(Object e) {
		short id;
		for (id = max++; map.get(Short.valueOf(id)) != null; id = max++) { }
		map.put(Short.valueOf(id), e);
		return id;
	}

	public void remove(short id) {
		map.remove(Short.valueOf(id));
	}

	public String toString() {
		return (new StringBuilder()).append("IntKeyMap{map=").append(map).append(", max=").append(max).append('}').toString();
	}
}
