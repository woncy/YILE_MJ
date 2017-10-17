// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   DoubleExpression.java

package com.isnowfox.el.exp;

import com.isnowfox.el.Expression;

public abstract class DoubleExpression
	implements Expression {

	public DoubleExpression() {
	}

	public Double el(Object obj) {
		return Double.valueOf(doubleEl(obj));
	}

	public abstract double doubleEl(Object obj);

	public volatile Object el(Object obj) {
		return el(obj);
	}
}
