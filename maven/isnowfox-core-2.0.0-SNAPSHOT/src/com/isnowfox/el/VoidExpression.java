// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   VoidExpression.java

package com.isnowfox.el;


// Referenced classes of package com.isnowfox.el:
//			Expression

public abstract class VoidExpression
	implements Expression {

	public VoidExpression() {
	}

	public Object el(Object obj) {
		voidEl(obj);
		return null;
	}

	public abstract void voidEl(Object obj);
}
