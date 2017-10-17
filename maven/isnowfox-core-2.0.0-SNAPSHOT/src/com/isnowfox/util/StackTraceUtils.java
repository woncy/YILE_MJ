// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   StackTraceUtils.java

package com.isnowfox.util;


public final class StackTraceUtils {

	public StackTraceUtils() {
	}

	public static String getBaseStackInfo(int i) {
		Throwable t = new Throwable();
		StackTraceElement array[] = t.getStackTrace();
		if (++i < array.length) {
			return String.valueOf(array[i]);
		} else {
			return "no StackInfo";
		}
	}

	public static CharSequence getBaseStackInfo(int i, int nums) {
		Throwable t = new Throwable();
		StackTraceElement array[] = t.getStackTrace();
		if (++i < array.length) {
			StringBuilder sb = new StringBuilder();
			int j = 0;
			for (int index = i; j < nums && index < array.length; index++) {
				if (j > 0) {
					sb.append("\n");
				}
				sb.append(array[index]);
				j++;
			}

			return sb;
		} else {
			return "no StackInfo";
		}
	}
}
