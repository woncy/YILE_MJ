// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MaxConcurrentQueue.java

package com.isnowfox.util.collect;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class MaxConcurrentQueue {

	private AtomicInteger size;
	private int max;
	private ConcurrentLinkedQueue q;

	public MaxConcurrentQueue() {
		size = new AtomicInteger();
		q = new ConcurrentLinkedQueue();
		max = 20;
	}

	public MaxConcurrentQueue(int max) {
		size = new AtomicInteger();
		q = new ConcurrentLinkedQueue();
		this.max = max;
	}

	public void add(Object t) {
		q.add(t);
		int s = size.incrementAndGet();
		if (s > max) {
			q.poll();
		}
	}
}
