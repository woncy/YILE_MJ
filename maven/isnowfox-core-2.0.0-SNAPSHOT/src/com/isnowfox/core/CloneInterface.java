// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CloneInterface.java

package com.isnowfox.core;


public interface CloneInterface
	extends Cloneable {

	public abstract Object clone() throws CloneNotSupportedException;
}
