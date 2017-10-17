// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ViewMap.java

package com.isnowfox.web.config;


public class ViewMap {

	private Object c;
	private String value;

	public ViewMap() {
	}

	public ViewMap(Object c, String value) {
		this.c = c;
		this.value = value;
	}

	public Object getC() {
		return c;
	}

	public void setC(Object c) {
		this.c = c;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
