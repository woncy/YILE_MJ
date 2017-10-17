// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BitUtils.java

package com.isnowfox.util;


public final class BitUtils {

	public BitUtils() {
	}

	public static final int set(int mask, int bit) {
		return mask | 1 << bit - 1;
	}

	public static final int clear(int mask, int bit) {
		return mask & ~(1 << bit - 1);
	}

	public static final boolean get(int mask, int bit) {
		return (mask & 1 << bit - 1) > 0;
	}
}
