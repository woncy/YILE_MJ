// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   IntIdMaker.java

package com.isnowfox.core.id;

import com.isnowfox.util.MaxValueException;
import java.util.BitSet;

public class IntIdMaker {

	private final int size;
	private int max;
	private final BitSet bits = new BitSet();

	public IntIdMaker(int size) {
		max = 0;
		this.size = size;
	}

	public int newId() {
		int i = 0;
		for (int id = max + 1; i < size; id++) {
			if (id >= size) {
				id = 0;
			}
			if (!bits.get(id)) {
				max = id;
				bits.set(id);
				return id;
			}
			i++;
		}

		throw new MaxValueException((new StringBuilder()).append("IntKeyMap id too big:").append(max).append(", max:").append(size).toString());
	}

	public boolean freeId(int id) {
		boolean result = bits.get(id);
		bits.clear(id);
		return result;
	}
}
