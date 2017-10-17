// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AbstractMessageFactory.java

package com.isnowfox.core.net.message;

import java.util.*;
import net.sf.cglib.core.Constants;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastConstructor;

// Referenced classes of package com.isnowfox.core.net.message:
//			MessageProtocol, MessageException, AbstractMessageFactory

public static final class AbstractMessageFactory$ExpandBuild {

	private final AbstractMessageFactory factory;
	private final Map map;
	private int maxId;

	public void add(int id, Class cls) throws MessageException {
		if (id > 237) {
			throw MessageException.newIdValueRangeException(id);
		}
		Class cls1 = (Class)map.put(Integer.valueOf(id), cls);
		if (cls1 != null) {
			throw MessageException.newIdDuplicateRangeException(id, cls, cls1);
		}
		if (maxId < id) {
			maxId = id;
		}
	}

	public void end() throws MessageException {
		if (AbstractMessageFactory.access$100(factory)[0] != null) {
			throw MessageException.newFactoryDuplicateExpandException();
		}
		FastConstructor array[] = new FastConstructor[maxId + 1];
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			java.util.Map$Entry e = (java.util.Factory.ExpandBuild.map)iterator.next();
			array[((Integer)e.getKey()).intValue()] = FastClass.create((Class)e.map()).getConstructor(Constants.EMPTY_CLASS_ARRAY);
		}

		AbstractMessageFactory.access$100(factory)[0] = array;
	}

	private AbstractMessageFactory$ExpandBuild(AbstractMessageFactory factory) {
		map = new HashMap();
		maxId = 0;
		this.factory = factory;
	}

}
