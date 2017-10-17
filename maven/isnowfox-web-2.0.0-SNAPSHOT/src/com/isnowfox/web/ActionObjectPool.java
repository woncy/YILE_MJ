// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ActionObjectPool.java

package com.isnowfox.web;

import com.isnowfox.core.IocFactory;
import java.util.HashMap;
import java.util.Map;

public class ActionObjectPool {

	private Map map;
	private IocFactory iocFactory;

	public ActionObjectPool(IocFactory iocFactory) {
		map = new HashMap();
		this.iocFactory = iocFactory;
	}

	public Object get(Class cls) throws InstantiationException, IllegalAccessException {
		Object obj = map.get(cls);
		if (obj == null) {
			synchronized (this) {
				obj = map.get(cls);
				if (obj == null) {
					obj = cls.newInstance();
					if (iocFactory != null) {
						iocFactory.initBean(obj);
					}
					map.put(cls, obj);
				}
			}
		}
		return obj;
	}
}
