// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   LocalCache.java

package org.forkjoin.core.dao;

import com.google.common.cache.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package org.forkjoin.core.dao:
//			EntityObject, KeyObject

public class LocalCache {

	private static final Logger log = LoggerFactory.getLogger(org/forkjoin/core/dao/LocalCache);
	public static final int MAX_MINUTES = 5;
	public static final int MAX_NUMS = 500;
	private Cache localCache;

	public LocalCache() {
	}

	public void init() {
		localCache = CacheBuilder.newBuilder().expireAfterAccess(5L, TimeUnit.MINUTES).recordStats().maximumSize(500L).weakKeys().build();
	}

	public EntityObject get(KeyObject k) {
		return (EntityObject)localCache.getIfPresent(k);
	}

	public void remove(KeyObject k) {
		localCache.invalidate(k);
	}

	public void put(KeyObject k, EntityObject t) {
		localCache.put(k, t);
	}

	public void put(Iterable values) {
		EntityObject t;
		for (Iterator iterator = values.iterator(); iterator.hasNext(); localCache.put(t.getKey(), t)) {
			t = (EntityObject)iterator.next();
		}

	}

	public ArrayList getValues(Iterable keys) {
		ArrayList list = new ArrayList();
		Iterator iterator = keys.iterator();
		do {
			if (!iterator.hasNext()) {
				break;
			}
			KeyObject k = (KeyObject)iterator.next();
			EntityObject t = get(k);
			if (t != null) {
				list.add(t);
			}
		} while (true);
		return list;
	}

	public CacheStats stats() {
		return localCache.stats();
	}

}
