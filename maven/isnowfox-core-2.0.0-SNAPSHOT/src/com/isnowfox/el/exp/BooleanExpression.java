// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BooleanExpression.java

package com.isnowfox.el.exp;

import com.isnowfox.el.Expression;

public abstract class BooleanExpression
	implements Expression {

	public BooleanExpression() {
	}

	public Boolean el(Object obj) {
		return Boolean.valueOf(booleanEl(obj));
	}

	public abstract boolean booleanEl(Object obj);

	public volatile Object el(Object obj) {
		return el(obj);
	}
}
