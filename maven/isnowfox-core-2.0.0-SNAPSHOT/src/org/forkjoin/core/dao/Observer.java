// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Observer.java

package org.forkjoin.core.dao;


public interface Observer {

	public abstract void changeProperty(String s, Object obj);
}
