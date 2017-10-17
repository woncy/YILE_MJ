// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ClassUtils.java

package com.isnowfox.util;

import java.util.HashMap;
import java.util.Map;

public final class ClassUtils {
	private static class WrapPrimitivesWrapInner {

		private static Map map;

		static  {
			map = new HashMap(16);
			map.put(java/lang/Boolean, Boolean.TYPE);
			map.put(java/lang/Byte, Byte.TYPE);
			map.put(java/lang/Character, Character.TYPE);
			map.put(java/lang/Double, Double.TYPE);
			map.put(java/lang/Float, Float.TYPE);
			map.put(java/lang/Integer, Integer.TYPE);
			map.put(java/lang/Long, Long.TYPE);
			map.put(java/lang/Short, Short.TYPE);
			map.put(java/lang/Void, Void.TYPE);
		}


		private WrapPrimitivesWrapInner() {
		}
	}

	private static class PrimitivesWrapInner {

		private static Map map;

		static  {
			map = new HashMap(16);
			map.put(Boolean.TYPE, java/lang/Boolean);
			map.put(Byte.TYPE, java/lang/Byte);
			map.put(Character.TYPE, java/lang/Character);
			map.put(Double.TYPE, java/lang/Double);
			map.put(Float.TYPE, java/lang/Float);
			map.put(Integer.TYPE, java/lang/Integer);
			map.put(Long.TYPE, java/lang/Long);
			map.put(Short.TYPE, java/lang/Short);
			map.put(Void.TYPE, java/lang/Void);
		}


		private PrimitivesWrapInner() {
		}
	}


	public ClassUtils() {
	}

	public static Class wrap(Class cls) {
		return (Class)PrimitivesWrapInner.map.get(cls);
	}

	public static Class unwrap(Class cls) {
		return (Class)WrapPrimitivesWrapInner.map.get(cls);
	}

	public static boolean isWrap(Class cls) {
		return WrapPrimitivesWrapInner.map.get(cls) != null;
	}

	public static boolean isVoid(Class cls) {
		return cls == Void.TYPE || cls == java/lang/Void;
	}

	public static String getClassName(Class parameterType) {
		if (parameterType.isArray()) {
			return (new StringBuilder()).append(getClassName(parameterType.getComponentType())).append("[]").toString();
		} else {
			return parameterType.getCanonicalName();
		}
	}
}
