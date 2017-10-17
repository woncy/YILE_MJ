// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   RandomUtils.java

package com.isnowfox.util;

import java.util.Random;

public final class RandomUtils {

	private static char ch[] = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 
		'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 
		'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 
		'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 
		'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 
		'y', 'z', '0', '1'
	};
	private static Random random = new Random();
	private static Random seedRandom = new Random();

	public RandomUtils() {
	}

	public static int randInt(int min, int max) {
		if (max < min) {
			return 0;
		}
		if (max == min) {
			return min;
		} else {
			int i = random.nextInt((max - min) + 1);
			return i + min;
		}
	}

	public static int randRange(int min, int max) {
		if (max == min) {
			return min;
		} else {
			int v = Math.abs(min - max);
			int i = random.nextInt(v + 1);
			min = Math.min(min, max);
			return i + min;
		}
	}

	public static int nextInt(int max) {
		return random.nextInt(max);
	}

	public static float nextFloat() {
		return random.nextFloat();
	}

	public static boolean randBoolean() {
		return random.nextBoolean();
	}

	public static int randInt(int max) {
		return random.nextInt(max);
	}

	public static int randInt() {
		return random.nextInt();
	}

	public static int randInt(int ints[]) {
		return ints[random.nextInt(ints.length)];
	}

	public static double randDouble() {
		return random.nextDouble();
	}

	public static String createRandomString(int length) {
		if (length > 0) {
			int index = 0;
			char temp[] = new char[length];
			int num = random.nextInt();
			for (int i = 0; i < length % 5; i++) {
				temp[index++] = ch[num & 0x3f];
				num >>= 6;
			}

			for (int i = 0; i < length / 5; i++) {
				num = random.nextInt();
				for (int j = 0; j < 5; j++) {
					temp[index++] = ch[num & 0x3f];
					num >>= 6;
				}

			}

			return new String(temp, 0, length);
		}
		if (length == 0) {
			return "";
		} else {
			throw new IllegalArgumentException();
		}
	}

	public static long randNext(long seed, int max) {
		seedRandom.setSeed(seed);
		return (long)seedRandom.nextInt(max);
	}

}
