// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ConcurrentArrayList.java

package com.isnowfox.util.collect;

import java.util.*;

// Referenced classes of package com.isnowfox.util.collect:
//			ConcurrentArrayList

private static class ConcurrentArrayList$COWSubListIterator
	implements ListIterator {

	private final ListIterator i;
	private final int offset;
	private final int size;

	public boolean hasNext() {
		return nextIndex() < size;
	}

	public Object next() {
		if (hasNext()) {
			return i.next();
		} else {
			throw new NoSuchElementException();
		}
	}

	public boolean hasPrevious() {
		return previousIndex() >= 0;
	}

	public Object previous() {
		if (hasPrevious()) {
			return i.previous();
		} else {
			throw new NoSuchElementException();
		}
	}

	public int nextIndex() {
		return i.nextIndex() - offset;
	}

	public int previousIndex() {
		return i.previousIndex() - offset;
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

	ConcurrentArrayList$COWSubListIterator(List l, int index, int offset, int size) {
		this.offset = offset;
		this.size = size;
		i = l.listIterator(index + offset);
	}
}
