// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   EntityObject.java

package org.forkjoin.core.dao;

import java.util.concurrent.atomic.AtomicInteger;

// Referenced classes of package org.forkjoin.core.dao:
//			KeyObject, TableInfo

public abstract class EntityObject {

	private static final long serialVersionUID = 0x8d1167b903903412L;
	private transient AtomicInteger entityVersion;

	public EntityObject() {
		entityVersion = new AtomicInteger();
	}

	public abstract KeyObject getKey();

	public abstract TableInfo getTableInfo();

	public boolean isEntityChange(int version) {
		return entityVersion.get() != version;
	}

	protected void changeProperty(String name, Object o) {
		entityVersion.incrementAndGet();
	}

	public int getEntityVersion() {
		return entityVersion.get();
	}

	public abstract void setKey(Object obj);

	public abstract Object get(String s);

	public abstract boolean set(String s, Object obj);

	public abstract EntityObject newInstance();
}
