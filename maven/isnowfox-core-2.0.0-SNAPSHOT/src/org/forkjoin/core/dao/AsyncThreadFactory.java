// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AsyncThreadFactory.java

package org.forkjoin.core.dao;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncThreadFactory
	implements ThreadFactory {

	static final AtomicInteger poolNumber = new AtomicInteger(1);
	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;
	private boolean daemon;

	public AsyncThreadFactory(String nameP, boolean daemon) {
		this.daemon = true;
		SecurityManager s = System.getSecurityManager();
		group = s == null ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
		namePrefix = (new StringBuilder()).append(nameP).append(".pool-").append(poolNumber.getAndIncrement()).append("-thread-").toString();
		this.daemon = daemon;
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, (new StringBuilder()).append(namePrefix).append(threadNumber.getAndIncrement()).toString(), 0L);
		t.setDaemon(daemon);
		if (t.getPriority() != 5) {
			t.setPriority(5);
		}
		return t;
	}

}
