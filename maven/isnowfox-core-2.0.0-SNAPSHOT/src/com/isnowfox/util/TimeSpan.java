// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   TimeSpan.java

package com.isnowfox.util;

import java.text.NumberFormat;

public final class TimeSpan {

	private long time;
	private NumberFormat nf;

	public TimeSpan() {
		nf = NumberFormat.getNumberInstance();
		nf.setParseIntegerOnly(false);
		nf.setGroupingUsed(false);
		nf.setMaximumFractionDigits(6);
		nf.setMaximumFractionDigits(6);
		start();
	}

	public TimeSpan start() {
		time = System.nanoTime();
		return this;
	}

	public String end() {
		return nf.format((double)(System.nanoTime() - time) / 1000000000D);
	}

	public double getSecond() {
		return (double)(System.nanoTime() - time) / 1000000000D;
	}

	public String toString() {
		return nf.format((double)(System.nanoTime() - time) / 1000000000D);
	}
}
