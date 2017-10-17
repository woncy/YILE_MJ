// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ClassUtils.java

package com.isnowfox.util;

import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.isnowfox.util:
//			ClassUtils

private static class ClassUtils$WrapPrimitivesWrapInner {

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


	private ClassUtils$WrapPrimitivesWrapInner() {
	}
}
