// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ConcurrentArrayList.java

package com.isnowfox.util.collect;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

// Referenced classes of package com.isnowfox.util.collect:
//			ConcurrentArrayList

private static class ConcurrentArrayList$COWSubList extends AbstractList
	implements RandomAccess {

	private final ConcurrentArrayList l;
	private final int offset;
	private int size;
	private Object expectedArray[];

	private void checkForComodification() {
		if (l.getArray() != expectedArray) {
			throw new ConcurrentModificationException();
		} else {
			return;
		}
	}

	private void rangeCheck(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(index).append(",Size: ").append(size).toString());
		} else {
			return;
		}
	}

	public Object set(int index, Object element) {
		ReentrantLock lock;
		lock = l.lock;
		lock.lock();
		Object obj;
		rangeCheck(index);
		checkForComodification();
		Object x = l.set(index + offset, element);
		expectedArray = l.getArray();
		obj = x;
		lock.unlock();
		return obj;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public Object get(int index) {
		ReentrantLock lock;
		lock = l.lock;
		lock.lock();
		Object obj;
		rangeCheck(index);
		checkForComodification();
		obj = l.get(index + offset);
		lock.unlock();
		return obj;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public int size() {
		ReentrantLock lock;
		lock = l.lock;
		lock.lock();
		int i;
		checkForComodification();
		i = size;
		lock.unlock();
		return i;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public void add(int index, Object element) {
		ReentrantLock lock;
		lock = l.lock;
		lock.lock();
		checkForComodification();
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		l.add(index + offset, element);
		expectedArray = l.getArray();
		size++;
		lock.unlock();
		break MISSING_BLOCK_LABEL_87;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public void clear() {
		ReentrantLock lock;
		lock = l.lock;
		lock.lock();
		checkForComodification();
		ConcurrentArrayList.access$100(l, offset, offset + size);
		expectedArray = l.getArray();
		size = 0;
		lock.unlock();
		break MISSING_BLOCK_LABEL_66;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public Object remove(int index) {
		ReentrantLock lock;
		lock = l.lock;
		lock.lock();
		Object obj;
		rangeCheck(index);
		checkForComodification();
		Object result = l.remove(index + offset);
		expectedArray = l.getArray();
		size--;
		obj = result;
		lock.unlock();
		return obj;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public boolean remove(Object o) {
		int index = indexOf(o);
		if (index == -1) {
			return false;
		} else {
			remove(index);
			return true;
		}
	}

	public Iterator iterator() {
		ReentrantLock lock;
		lock = l.lock;
		lock.lock();
		terator terator;
		checkForComodification();
		terator = new terator(l, 0, offset, size);
		lock.unlock();
		return terator;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public ListIterator listIterator(int index) {
		ReentrantLock lock;
		lock = l.lock;
		lock.lock();
		terator terator;
		checkForComodification();
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(index).append(", Size: ").append(size).toString());
		}
		terator = new terator(l, index, offset, size);
		lock.unlock();
		return terator;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public List subList(int fromIndex, int toIndex) {
		ReentrantLock lock;
		lock = l.lock;
		lock.lock();
		ConcurrentArrayList$COWSubList concurrentarraylist$cowsublist;
		checkForComodification();
		if (fromIndex < 0 || toIndex > size) {
			throw new IndexOutOfBoundsException();
		}
		concurrentarraylist$cowsublist = new ConcurrentArrayList$COWSubList(l, fromIndex + offset, toIndex + offset);
		lock.unlock();
		return concurrentarraylist$cowsublist;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	ConcurrentArrayList$COWSubList(ConcurrentArrayList list, int fromIndex, int toIndex) {
		l = list;
		expectedArray = l.getArray();
		offset = fromIndex;
		size = toIndex - fromIndex;
	}
}
