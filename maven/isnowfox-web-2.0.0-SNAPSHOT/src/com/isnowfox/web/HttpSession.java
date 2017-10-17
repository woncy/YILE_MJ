// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttpSession.java

package com.isnowfox.web;

import com.google.common.collect.Maps;
import com.isnowfox.core.bean.Attributes;
import java.util.Map;
import java.util.Set;

public class HttpSession
	implements Attributes {

	private Map attributesMap;
	private int maxInactiveInterval;

	private HttpSession() {
		attributesMap = Maps.newConcurrentMap();
		maxInactiveInterval = 1200;
	}

	public void removeAttribute(Object name) {
		attributesMap.remove(name);
	}

	public void setAttribute(String name, Object object) {
		attributesMap.put(name, object);
	}

	public Object getAttribute(String name) {
		return attributesMap.get(name);
	}

	public Map getAttributesMap() {
		return attributesMap;
	}

	public Set getAttributeNames() {
		return attributesMap.keySet();
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}
}
