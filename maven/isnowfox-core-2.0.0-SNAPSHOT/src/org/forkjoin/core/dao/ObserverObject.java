// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ObserverObject.java

package org.forkjoin.core.dao;


// Referenced classes of package org.forkjoin.core.dao:
//			Observer

public interface ObserverObject {

	public abstract boolean isChange();

	public abstract void onSaveAfter();

	public abstract void setObserver(Observer observer);
}
