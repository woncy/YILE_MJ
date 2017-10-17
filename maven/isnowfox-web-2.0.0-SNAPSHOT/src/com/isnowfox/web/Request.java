// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Request.java

package com.isnowfox.web;

import java.nio.charset.Charset;
import java.util.*;

public abstract class Request {

	public Request() {
	}

	public abstract boolean isPost();

	public abstract byte[] getBytes(String s);

	public abstract boolean getBoolean(String s);

	public abstract String getString(String s);

	public abstract List getStringList(String s);

	public abstract Map getAllParams();

	public Map getParamsMap() {
		Map allParams = getAllParams();
		Map result = new HashMap();
		Iterator iterator = allParams.entrySet().iterator();
		do {
			if (!iterator.hasNext()) {
				break;
			}
			java.util.Map.Entry e = (java.util.Map.Entry)iterator.next();
			List list = (List)e.getValue();
			if (!list.isEmpty()) {
				result.put(e.getKey(), list.get(0));
			}
		} while (true);
		return result;
	}

	public abstract String getStringByCharset(String s, String s1);

	public abstract String getString(String s, String s1);

	public abstract int getInt(String s);

	public abstract int getInt(String s, int i);

	public abstract int getInt(String s, int i, int j);

	public abstract int getIntMin(String s, int i, int j);

	public abstract int getInt(String s, int i, int j, int k);

	public abstract float getFloat(String s, float f);

	public abstract Charset getCharset();

	public abstract String getRemoteInfo();

	public abstract int getLocalPort();

	public abstract String getPath();

	public abstract String getHeader(String s);

	public abstract List getHeaders(String s);

	public abstract List getHeaders();

	public abstract boolean containsHeader(String s);

	public abstract Set getHeaderNames();

	public abstract String getCookieString(String s);
}
