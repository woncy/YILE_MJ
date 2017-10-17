// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ClassUtils.java

package com.isnowfox.util;

import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.isnowfox.util:
//			ClassUtils

private static class ClassUtils$PrimitivesWrapInner {

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


	private ClassUtils$PrimitivesWrapInner() {
	}
}
