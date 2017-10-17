// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Context.java

package com.isnowfox.web;

import com.google.common.collect.Maps;
import com.isnowfox.core.bean.Attributes;
import java.util.Map;
import java.util.Set;

// Referenced classes of package com.isnowfox.web:
//			Request

public final class Context
	implements Attributes {

	private static final Context instance = new Context();
	private Map attributesMap;
	private ThreadLocal requestThreadLocal;

	private Context() {
		attributesMap = Maps.newConcurrentMap();
		requestThreadLocal = new ThreadLocal();
	}

	public static Context getInstance() {
		return instance;
	}

	public void setRequest(Request re) {
		requestThreadLocal.set(re);
		if (re == null) {
			requestThreadLocal.remove();
		}
	}

	public Request getRequest() {
		return (Request)requestThreadLocal.get();
	}

	public void clearRequest() {
		requestThreadLocal.remove();
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

}
