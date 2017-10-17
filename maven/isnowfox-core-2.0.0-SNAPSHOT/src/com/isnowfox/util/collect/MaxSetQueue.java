// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MaxSetQueue.java

package com.isnowfox.util.collect;

import java.util.*;
import org.apache.commons.collections.set.ListOrderedSet;

public class MaxSetQueue
	implements Iterable {

	private ListOrderedSet set;
	private int max;

	public MaxSetQueue(int max) {
		this.max = max;
		set = ListOrderedSet.decorate(new HashSet(max), new ArrayList(max));
	}

	public void add(Object e) {
		if (set.size() == max) {
			set.remove(set.size() - 1);
		}
		if (set.contains(e)) {
			set.remove(e);
		}
		set.add(0, e);
	}

	public void remove(Object e) {
		set.remove(e);
	}

	public boolean contains(Object e) {
		return set.contains(e);
	}

	public Iterator iterator() {
		return set.iterator();
	}

	public int size() {
		return set.size();
	}
}
