// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ByteExpression.java

package com.isnowfox.el.exp;

import com.isnowfox.el.Expression;

public abstract class ByteExpression
	implements Expression {

	public ByteExpression() {
	}

	public Byte el(Object obj) {
		return Byte.valueOf(byteEl(obj));
	}

	public abstract byte byteEl(Object obj);

	public volatile Object el(Object obj) {
		return el(obj);
	}
}
