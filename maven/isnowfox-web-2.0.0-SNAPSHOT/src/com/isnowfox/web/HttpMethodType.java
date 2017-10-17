// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttpMethodType.java

package com.isnowfox.web;


public final class HttpMethodType extends Enum {

	public static final HttpMethodType ALL;
	public static final HttpMethodType DELETE;
	public static final HttpMethodType GET;
	public static final HttpMethodType HEAD;
	public static final HttpMethodType POST;
	public static final HttpMethodType PUT;
	private static final HttpMethodType $VALUES[];

	public static HttpMethodType[] values() {
		return (HttpMethodType[])$VALUES.clone();
	}

	public static HttpMethodType valueOf(String name) {
		return (HttpMethodType)Enum.valueOf(com/isnowfox/web/HttpMethodType, name);
	}

	private HttpMethodType(String s, int i) {
		super(s, i);
	}

	static  {
		ALL = new HttpMethodType("ALL", 0);
		DELETE = new HttpMethodType("DELETE", 1);
		GET = new HttpMethodType("GET", 2);
		HEAD = new HttpMethodType("HEAD", 3);
		POST = new HttpMethodType("POST", 4);
		PUT = new HttpMethodType("PUT", 5);
		$VALUES = (new HttpMethodType[] {
			ALL, DELETE, GET, HEAD, POST, PUT
		});
	}
}
