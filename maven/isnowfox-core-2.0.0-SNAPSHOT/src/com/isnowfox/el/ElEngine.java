// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ElEngine.java

package com.isnowfox.el;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javassist.*;

// Referenced classes of package com.isnowfox.el:
//			ClassBuilder, Expression, UnknownKeyException

public class ElEngine {
	private static class Inner {

		private static final ElEngine instance = new ElEngine();



		private Inner() {
		}
	}


	public static final boolean DEBUG = true;
	private Map cacheMap;
	private AtomicInteger itemSeed;

	public ElEngine() {
		cacheMap = new HashMap();
		itemSeed = new AtomicInteger();
	}

	public static final ElEngine getInstance() {
		return Inner.instance;
	}

	public Object el(Object obj, String el) throws UnknownKeyException {
		return compile(obj.getClass(), el).el(obj);
	}

	public final Expression compile(Class cls, String el) throws UnknownKeyException {
		if (cls == null) {
			throw new NullPointerException("null");
		}
		Expression p;
		p = get(cls, el);
		if (p == null) {
			synchronized (this) {
				p = get(cls, el);
				if (p == null) {
					ClassPool pool = ClassPool.getDefault();
					ClassBuilder builder = new ClassBuilder(this, pool, cls, el, itemSeed.incrementAndGet());
					p = (Expression)builder.build();
					put(cls, el, p);
				}
			}
		}
		return p;
		Exception e;
		e;
		throw new RuntimeException(e);
	}

	private final Expression get(Class cls, String el) {
		Map m = (Map)cacheMap.get(cls);
		if (m == null) {
			return null;
		} else {
			return (Expression)m.get(el);
		}
	}

	private final void put(Class cls, String el, Expression o) {
		Map m = (Map)cacheMap.get(cls);
		if (m == null) {
			m = new HashMap();
			cacheMap.put(cls, m);
		}
		m.put(el, o);
	}
}
