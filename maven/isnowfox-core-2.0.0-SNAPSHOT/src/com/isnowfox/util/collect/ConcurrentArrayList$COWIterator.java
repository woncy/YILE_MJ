// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ConcurrentArrayList.java

package com.isnowfox.util.collect;

import java.util.ListIterator;
import java.util.NoSuchElementException;

// Referenced classes of package com.isnowfox.util.collect:
//			ConcurrentArrayList

private static class ConcurrentArrayList$COWIterator
	implements ListIterator {

	private final Object snapshot[];
	private int cursor;

	public boolean hasNext() {
		return cursor < snapshot.length;
	}

	public boolean hasPrevious() {
		return cursor > 0;
	}

	public Object next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		} else {
			return snapshot[cursor++];
		}
	}

	public Object previous() {
		if (!hasPrevious()) {
			throw new NoSuchElementException();
		} else {
			return snapshot[--cursor];
		}
	}

	public int nextIndex() {
		return cursor;
	}

	public int previousIndex() {
		return cursor - 1;
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}

	public void set(Object e) {
		throw new UnsupportedOperationException();
	}

	public void add(Object e) {
		throw new UnsupportedOperationException();
	}

	private ConcurrentArrayList$COWIterator(Object elements[], int initialCursor) {
		cursor = initialCursor;
		snapshot = elements;
	}

}
