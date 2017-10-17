// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ShortExpression.java

package com.isnowfox.el;


// Referenced classes of package com.isnowfox.el:
//			Expression

public abstract class ShortExpression
	implements Expression {

	public ShortExpression() {
	}

	public Short el(Object obj) {
		return Short.valueOf(shortEl(obj));
	}

	public abstract short shortEl(Object obj);

	public volatile Object el(Object obj) {
		return el(obj);
	}
}
