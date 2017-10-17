// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MathUtils.java

package com.isnowfox.util;


public final class MathUtils {

	public MathUtils() {
	}

	public static final int sum(int ints[]) {
		int number = 0;
		int ai[] = ints;
		int j = ai.length;
		for (int k = 0; k < j; k++) {
			int i = ai[k];
			number += i;
		}

		return number;
	}
}
