// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LongExpression.java

package com.isnowfox.el.exp;

import com.isnowfox.el.Expression;

public abstract class LongExpression
	implements Expression {

	public LongExpression() {
	}

	public Long el(Object obj) {
		return Long.valueOf(longEl(obj));
	}

	public abstract long longEl(Object obj);

	public volatile Object el(Object obj) {
		return el(obj);
	}
}
