// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MaxMapQueue.java

package com.isnowfox.util.collect;

import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.collections.map.ListOrderedMap;

public class MaxMapQueue
	implements Iterable {

	private ListOrderedMap map;
	private int max;

	public MaxMapQueue(int max) {
		this.max = max;
		map = new ListOrderedMap();
	}

	public void put(Object key, Object v) {
		if (map.size() == max) {
			map.remove(map.size() - 1);
		}
		map.put(0, key, v);
	}

	public void remove(Object e) {
		map.remove(e);
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public Object get(Object k) {
		return map.get(k);
	}

	public boolean containsValue(Object key) {
		return map.containsValue(key);
	}

	public Iterator iterator() {
		return map.values().iterator();
	}

	public int size() {
		return map.size();
	}

	public void clear() {
		map.clear();
	}
}
