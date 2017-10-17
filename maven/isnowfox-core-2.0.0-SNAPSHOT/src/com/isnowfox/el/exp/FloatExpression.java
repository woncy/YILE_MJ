// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   FloatExpression.java

package com.isnowfox.el.exp;

import com.isnowfox.el.Expression;

public abstract class FloatExpression
	implements Expression {

	public FloatExpression() {
	}

	public Float el(Object obj) {
		return Float.valueOf(floatEl(obj));
	}

	public abstract float floatEl(Object obj);

	public volatile Object el(Object obj) {
		return el(obj);
	}
}
