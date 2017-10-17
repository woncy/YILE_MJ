// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CloneUtils.java

package com.isnowfox.util;

import com.isnowfox.core.CloneInterface;
import java.lang.reflect.Array;
import java.util.*;

public class CloneUtils {

	public CloneUtils() {
	}

	public static final CloneInterface[] clone(CloneInterface array[]) throws CloneNotSupportedException {
		if (array == null) {
			return null;
		}
		CloneInterface result[] = (CloneInterface[])(CloneInterface[])Array.newInstance(array.getClass().getComponentType(), array.length);
		for (int i = 0; i < array.length; i++) {
			CloneInterface v = array[i];
			if (v != null) {
				result[i] = (CloneInterface)v.clone();
			}
		}

		return result;
	}

	public static final Map clone(Map map) throws CloneNotSupportedException {
		if (map == null) {
			return null;
		}
		HashMap result = new HashMap(map.size());
		CloneInterface value;
		Object key;
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext(); result.put(key, value)) {
			java.util.Map.Entry entry = (java.util.Map.Entry)iterator.next();
			value = (CloneInterface)entry.getValue();
			key = entry.getKey();
			if (value != null) {
				value = (CloneInterface)value.clone();
			}
		}

		return result;
	}

	public static int[][] clone(int array[][]) {
		int result[][] = new int[array.length][];
		for (int i = 0; i < array.length; i++) {
			int v[] = array[i];
			if (v != null) {
				result[i] = (int[])(int[])v.clone();
			}
		}

		return result;
	}
}
