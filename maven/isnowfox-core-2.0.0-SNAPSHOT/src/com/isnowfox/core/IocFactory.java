// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   IocFactory.java

package com.isnowfox.core;

import java.util.Map;

public interface IocFactory {

	public abstract void initBean(Object obj);

	public abstract Object getBean(Class class1);

	public abstract Object getBean(String s);

	public abstract Object getBean(String s, Class class1);

	public abstract Map getBeansOfType(Class class1);

	public abstract void destroy();
}
