// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Attributes.java

package com.isnowfox.core.bean;

import java.util.Map;
import java.util.Set;

public interface Attributes {

	public abstract void removeAttribute(Object obj);

	public abstract void setAttribute(String s, Object obj);

	public abstract Object getAttribute(String s);

	public abstract Map getAttributesMap();

	public abstract Set getAttributeNames();
}
