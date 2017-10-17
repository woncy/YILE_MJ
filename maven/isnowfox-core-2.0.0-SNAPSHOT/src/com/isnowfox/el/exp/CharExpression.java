// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CharExpression.java

package com.isnowfox.el.exp;

import com.isnowfox.el.Expression;

public abstract class CharExpression
	implements Expression {

	public CharExpression() {
	}

	public Character el(Object obj) {
		return Character.valueOf(charEl(obj));
	}

	public abstract char charEl(Object obj);

	public volatile Object el(Object obj) {
		return el(obj);
	}
}
