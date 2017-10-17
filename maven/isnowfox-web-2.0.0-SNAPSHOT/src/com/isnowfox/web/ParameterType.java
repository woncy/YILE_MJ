// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ParameterType.java

package com.isnowfox.web;


public final class ParameterType extends Enum {

	public static final ParameterType REQUEST;
	public static final ParameterType HEADER;
	public static final ParameterType COOKIE;
	public static final ParameterType IOC;
	private static final ParameterType $VALUES[];

	public static ParameterType[] values() {
		return (ParameterType[])$VALUES.clone();
	}

	public static ParameterType valueOf(String name) {
		return (ParameterType)Enum.valueOf(com/isnowfox/web/ParameterType, name);
	}

	private ParameterType(String s, int i) {
		super(s, i);
	}

	static  {
		REQUEST = new ParameterType("REQUEST", 0);
		HEADER = new ParameterType("HEADER", 1);
		COOKIE = new ParameterType("COOKIE", 2);
		IOC = new ParameterType("IOC", 3);
		$VALUES = (new ParameterType[] {
			REQUEST, HEADER, COOKIE, IOC
		});
	}
}
