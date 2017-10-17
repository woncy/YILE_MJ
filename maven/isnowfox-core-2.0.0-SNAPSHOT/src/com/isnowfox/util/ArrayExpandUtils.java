// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ArrayExpandUtils.java

package com.isnowfox.util;

import gnu.trove.list.array.TIntArrayList;
import java.util.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

public final class ArrayExpandUtils extends ArrayUtils {

	public ArrayExpandUtils() {
	}

	public static int[] toPrimitive(List list) {
		if (list == null) {
			return null;
		}
		int array[] = new int[list.size()];
		for (int i = 0; i < array.length; i++) {
			array[i] = ((Integer)list.get(i)).intValue();
		}

		return array;
	}

	public static int[] toPrimitive(Collection collect) {
		if (collect == null) {
			return null;
		}
		int array[] = new int[collect.size()];
		int i = 0;
		for (Iterator iterator = collect.iterator(); iterator.hasNext();) {
			int value = ((Integer)iterator.next()).intValue();
			array[i++] = value;
		}

		return array;
	}

	public static transient boolean check(Object objs[], Class clses[]) {
		if (objs.length == clses.length) {
			for (int i = 0; i < clses.length; i++) {
				if (!clses[i].isInstance(objs[i])) {
					return false;
				}
			}

			return true;
		} else {
			return false;
		}
	}

	public static Object get(Object objs[], int i) {
		if (objs != null && i < objs.length && i > -1) {
			return objs[i];
		} else {
			return null;
		}
	}

	public static String join(String array[], char sep, int startIndex) {
		return join(array, sep, startIndex, array.length);
	}

	public static String join(String array[], char sep, int startIndex, int endIndex) {
		if (startIndex < 0 || startIndex > endIndex || endIndex > array.length) {
			throw new IndexOutOfBoundsException((new StringBuilder()).append("startIndex ").append(startIndex).append(", endIndex ").append(endIndex).append(", array.length").append(array.length).toString());
		}
		StringBuilder sb = new StringBuilder();
		for (int i = startIndex; i < array.length && i < endIndex; i++) {
			if (i > startIndex) {
				sb.append(sep);
			}
			sb.append(array[i]);
		}

		return sb.toString();
	}

	public static final String toString(byte array[], int len) {
		StringBuilder sb = new StringBuilder(len * 5);
		sb.append('[');
		for (int i = 0; i < array.length && i < len; i++) {
			if (i > 0) {
				sb.append(',');
			}
			sb.append(array[i]);
		}

		sb.append(']');
		return sb.toString();
	}

	public static final String toString(byte array[], int offset, int len) {
		StringBuilder sb = new StringBuilder(len * 5);
		sb.append('[');
		for (int i = offset; i < array.length && i < len; i++) {
			if (i > 0) {
				sb.append(',');
			}
			sb.append(array[i]);
		}

		sb.append(']');
		return sb.toString();
	}

	public static int[] toIntArray(String str) {
		String array[] = str.split(",");
		int intArray[] = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			intArray[i] = NumberUtils.toInt(array[i]);
		}

		return intArray;
	}

	public static String toSizeString(TIntArrayList array[]) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			sb.append((new StringBuilder()).append(array[i].size()).append(",").toString());
		}

		return sb.toString();
	}
}
