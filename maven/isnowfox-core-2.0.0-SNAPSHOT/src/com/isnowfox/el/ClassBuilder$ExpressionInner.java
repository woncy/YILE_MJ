// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ClassBuilder.java

package com.isnowfox.el;

import com.isnowfox.el.exp.BooleanExpression;
import com.isnowfox.el.exp.ByteExpression;
import com.isnowfox.el.exp.CharExpression;
import com.isnowfox.el.exp.DoubleExpression;
import com.isnowfox.el.exp.FloatExpression;
import com.isnowfox.el.exp.IntExpression;
import com.isnowfox.el.exp.LongExpression;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package com.isnowfox.el:
//			ShortExpression, VoidExpression, ClassBuilder

private static class ClassBuilder$ExpressionInner {

	private static Map map;

	static  {
		map = new HashMap(16);
		map.put(Boolean.TYPE, com/isnowfox/el/exp/BooleanExpression);
		map.put(Byte.TYPE, com/isnowfox/el/exp/ByteExpression);
		map.put(Character.TYPE, com/isnowfox/el/exp/CharExpression);
		map.put(Double.TYPE, com/isnowfox/el/exp/DoubleExpression);
		map.put(Float.TYPE, com/isnowfox/el/exp/FloatExpression);
		map.put(Integer.TYPE, com/isnowfox/el/exp/IntExpression);
		map.put(Long.TYPE, com/isnowfox/el/exp/LongExpression);
		map.put(Short.TYPE, com/isnowfox/el/ShortExpression);
		map.put(Void.TYPE, com/isnowfox/el/VoidExpression);
	}


	private ClassBuilder$ExpressionInner() {
	}
}
