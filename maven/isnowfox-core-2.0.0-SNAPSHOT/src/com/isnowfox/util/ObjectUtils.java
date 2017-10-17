// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ObjectUtils.java

package com.isnowfox.util;

import org.apache.commons.lang3.math.NumberUtils;

public final class ObjectUtils {

	public ObjectUtils() {
	}

	public static final int checkInt(Object obj, int defaultValue) {
		if (obj == null || !(obj instanceof Integer)) {
			return defaultValue;
		} else {
			return ((Integer)obj).intValue();
		}
	}

	public static final transient boolean check(Object obj, Class clses[]) {
		if (obj == null) {
			return false;
		}
		if (clses.length == 1) {
			return clses[0].isInstance(obj);
		}
		if (obj instanceof Object[]) {
			Object objs[] = (Object[])(Object[])obj;
			if (objs.length == clses.length) {
				for (int i = 0; i < clses.length; i++) {
					if (!clses[i].isInstance(objs[i])) {
						return false;
					}
				}

				return true;
			}
		}
		return false;
	}

	public static int toInt(Object ret) {
		if (ret == null) {
			return -1;
		}
		if (ret instanceof Number) {
			return ((Number)ret).intValue();
		} else {
			return NumberUtils.toInt(String.valueOf(ret));
		}
	}
}
