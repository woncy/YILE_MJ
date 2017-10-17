// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BaseRandom.java

package com.isnowfox.util;

import java.io.PrintStream;

public class BaseRandom {

	private int seed;
	private static final int multiplier = 0x1315d;
	private static final int addend = 11;
	private static final int mask = 0xffffff;

	public static void main(String args[]) {
		BaseRandom r = new BaseRandom(1);
		int trueNums = 0;
		for (int i = 0; i < 0x186a0; i++) {
			if (r.nextMax(2048) > 1024) {
				trueNums++;
			}
		}

		System.out.println((new StringBuilder()).append("sb").append(trueNums).toString());
	}

	public BaseRandom(int seed) {
		this.seed = initialScramble(seed);
	}

	private static int initialScramble(int seed) {
		return (seed ^ 0x1315d) & 0xffffff;
	}

	public Boolean randOdds(double value) {
		int i = nextInt16();
		return Boolean.valueOf((double)i < value * 65535D);
	}

	public void setSeed(int seed) {
		this.seed = initialScramble(seed);
	}

	protected int next(int bits) {
		seed = seed * 0x1315d + 11 & 0xffffff;
		return seed >>> 24 - bits;
	}

	public int nextInt16() {
		return next(16);
	}

	public int nextInt() {
		return (next(16) << 16) + next(16);
	}

	public int nextInt31() {
		return (next(15) << 15) + next(16);
	}

	public int nextBits(int bits) {
		if (bits > 16) {
			int highBits = bits - 16;
			return (next(highBits) << highBits) + next(16);
		} else {
			return next(bits);
		}
	}

	public int nextMax(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must be positive");
		}
		int bits;
		int val;
		do {
			bits = nextInt31();
			val = bits % n;
		} while ((bits - val) + (n - 1) < 0);
		return val;
	}

	public boolean nextBoolean() {
		return next(1) != 0;
	}
}
