// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   IntExpression.java

package com.isnowfox.el.exp;

import com.isnowfox.el.Expression;

public abstract class IntExpression
	implements Expression {

	public IntExpression() {
	}

	public Integer el(Object obj) {
		return Integer.valueOf(intEl(obj));
	}

	public abstract int intEl(Object obj);

	public volatile Object el(Object obj) {
		return el(obj);
	}
}
