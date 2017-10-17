// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   IntKeyMap.java

package com.isnowfox.util;

import java.lang.reflect.Array;
import java.util.Arrays;

// Referenced classes of package com.isnowfox.util:
//			MaxValueException

public class IntKeyMap {

	private final Object values[];
	private final int size;
	private int max;

	public IntKeyMap(int size, Class elementClass) {
		max = 0;
		this.size = size;
		values = (Object[])(Object[])Array.newInstance(elementClass, size);
	}

	public Object getInt(int i) {
		return values[i];
	}

	public int add(Object e) {
		if (max >= size) {
			throw new MaxValueException((new StringBuilder()).append("IntKeyMap id too big:").append(max).append(", max:").append(size).toString());
		}
		int id;
		for (id = max++; values[id] != null; id = max++) { }
		values[id] = e;
		return id;
	}

	private int getFreeId() {
		for (int i = 0; i < size; i++) {
			int id = max + 1;
			if (id >= size) {
				id = 0;
			}
			if (values[id] == null) {
				max = id;
				return id;
			}
		}

		throw new MaxValueException((new StringBuilder()).append("IntKeyMap id too big:").append(max).append(", max:").append(size).toString());
	}

	public void remove(int id) {
		values[id] = null;
	}

	public String toString() {
		return (new StringBuilder()).append("IntKeyMap [values=").append(Arrays.toString(values)).append(", size=").append(size).append(", max=").append(max).append("]").toString();
	}
}
