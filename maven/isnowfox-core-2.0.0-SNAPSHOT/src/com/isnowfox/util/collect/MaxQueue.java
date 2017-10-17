// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MaxQueue.java

package com.isnowfox.util.collect;

import java.util.*;

public class MaxQueue
	implements Iterable {

	private Deque queue;
	private int max;

	public MaxQueue(int max) {
		this.max = max;
		queue = new ArrayDeque(max);
	}

	public void add(Collection collect) {
		int i = 0;
		Iterator iterator1 = collect.iterator();
		do {
			if (!iterator1.hasNext()) {
				break;
			}
			Object t = iterator1.next();
			if (i >= max) {
				break;
			}
			queue.addLast(t);
			i++;
		} while (true);
	}

	public void add(Object e) {
		if (queue.size() == max) {
			queue.removeLast();
		}
		queue.addFirst(e);
	}

	public Iterator iterator() {
		return queue.iterator();
	}

	public int size() {
		return queue.size();
	}

	public void clear() {
		queue.clear();
	}

	public Object poll() {
		return queue.poll();
	}

	public boolean isNotEmpty() {
		return !queue.isEmpty();
	}

	public boolean isEmpty() {
		return queue.isEmpty();
	}
}
