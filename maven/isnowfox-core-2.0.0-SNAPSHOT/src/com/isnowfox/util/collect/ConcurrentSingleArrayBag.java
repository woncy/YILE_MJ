// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ConcurrentSingleArrayBag.java

package com.isnowfox.util.collect;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang.ArrayUtils;

// Referenced classes of package com.isnowfox.util.collect:
//			ConcurrentSingleArrayBagListener

public class ConcurrentSingleArrayBag
	implements List, RandomAccess, Cloneable, Serializable {
	private static class COWIterator
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

		private COWIterator(Object elements[], int initialCursor) {
			cursor = initialCursor;
			snapshot = elements;
		}

	}


	private List observers;
	private static final long serialVersionUID = 0xad05469a1a8e20f9L;
	final transient ReentrantLock lock;
	private volatile transient Object array[];

	public ConcurrentSingleArrayBag() {
		observers = new ArrayList();
		lock = new ReentrantLock();
		setArray(new Object[0]);
	}

	public ConcurrentSingleArrayBag(int size) {
		observers = new ArrayList();
		lock = new ReentrantLock();
		setArray(new Object[size]);
	}

	private ConcurrentSingleArrayBag(Object value[]) {
		observers = new ArrayList();
		lock = new ReentrantLock();
		setArray(value);
	}

	final Object[] getArray() {
		return array;
	}

	final void setArray(Object a[]) {
		array = a;
	}

	final Object get(Object a[], int index) {
		return a[index];
	}

	public Object get(int index) {
		return get(getArray(), index);
	}

	public Object set(int index, Object element) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		Object obj;
		Object elements[] = getArray();
		Object oldValue = get(elements, index);
		elements[index] = element;
		fireChanged();
		obj = oldValue;
		lock.unlock();
		return obj;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public int size() {
		return getArray().length;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	private Object[] getValue() {
		return getArray();
	}

	public boolean check(int nums) {
		Object elements[] = getArray();
		int space = 0;
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] == null && ++space >= nums) {
				return true;
			}
		}

		return false;
	}

	public boolean check() {
		Object elements[] = getArray();
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] == null) {
				return true;
			}
		}

		return false;
	}

	public void extend(int size) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		Object elements[] = getArray();
		if (size > elements.length) {
			setArray(Arrays.copyOf(elements, size));
			fireChanged();
		}
		lock.unlock();
		break MISSING_BLOCK_LABEL_49;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public boolean contains(Object o) {
		Object elements[] = getArray();
		return ArrayUtils.indexOf(elements, o) >= 0;
	}

	public Iterator iterator() {
		return new COWIterator(getArray(), 0);
	}

	public Object[] toArray() {
		Object elements[] = getArray();
		return Arrays.copyOf(elements, elements.length);
	}

	public Object[] toArray(Object a[]) {
		Object elements[] = getArray();
		int len = elements.length;
		if (a.length < len) {
			return (Object[])Arrays.copyOf(elements, len, ((Object) (a)).getClass());
		}
		System.arraycopy(((Object) (elements)), 0, ((Object) (a)), 0, len);
		if (a.length > len) {
			a[len] = null;
		}
		return a;
	}

	public boolean add(Object e) {
		return addElement(e) > -1;
	}

	public int addElement(Object e) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		Object elements[];
		int len;
		int i;
		elements = getArray();
		len = elements.length;
		i = 0;
_L1:
		int j;
		if (i >= len) {
			break MISSING_BLOCK_LABEL_61;
		}
		if (elements[i] != null) {
			break MISSING_BLOCK_LABEL_55;
		}
		elements[i] = e;
		fireChanged();
		j = i;
		lock.unlock();
		return j;
		i++;
		  goto _L1
		lock.unlock();
		break MISSING_BLOCK_LABEL_77;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
		return -1;
	}

	public boolean remove(Object o) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		Object elements[];
		int len;
		int i;
		elements = getArray();
		len = elements.length;
		if (len == 0) {
			break MISSING_BLOCK_LABEL_69;
		}
		i = 0;
_L1:
		boolean flag;
		if (i >= len) {
			break MISSING_BLOCK_LABEL_69;
		}
		if (!eq(o, elements[i])) {
			break MISSING_BLOCK_LABEL_63;
		}
		elements[i] = null;
		fireChanged();
		flag = true;
		lock.unlock();
		return flag;
		i++;
		  goto _L1
		i = 0;
		lock.unlock();
		return i;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public Object remove(int index) {
		return set(index, null);
	}

	public boolean addAll(Collection c) {
		Object cs[];
		ReentrantLock lock;
		cs = c.toArray();
		lock = this.lock;
		lock.lock();
		int i;
		Object elements[] = getArray();
		int nums = 0;
		for (i = 0; i < elements.length; i++) {
			if (elements[i] == null) {
				nums++;
			}
		}

		if (nums < cs.length) {
			break MISSING_BLOCK_LABEL_121;
		}
		i = 0;
		for (int j = 0; i < elements.length && j < cs.length; i++) {
			if (elements[i] == null) {
				elements[i] = cs[j++];
			}
		}

		fireChanged();
		i = 1;
		lock.unlock();
		return i;
		lock.unlock();
		break MISSING_BLOCK_LABEL_137;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
		return false;
	}

	public void clear() {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		Object elements[] = getArray();
		int len = elements.length;
		if (len != 0) {
			for (int i = 0; i < len; i++) {
				elements[i] = null;
			}

		}
		fireChanged();
		lock.unlock();
		break MISSING_BLOCK_LABEL_61;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public int indexOf(Object o) {
		return ArrayUtils.indexOf(array, o);
	}

	public int lastIndexOf(Object o) {
		return ArrayUtils.lastIndexOf(array, o);
	}

	public boolean containsAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	public boolean removeAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	public boolean retainAll(Collection c) {
		throw new UnsupportedOperationException();
	}

	public ListIterator listIterator() {
		return new COWIterator(getArray(), 0);
	}

	public ListIterator listIterator(int index) {
		Object elements[] = getArray();
		int len = elements.length;
		if (index < 0 || index > len) {
			throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(index).toString());
		} else {
			return new COWIterator(elements, index);
		}
	}

	public List subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	private static boolean eq(Object o1, Object o2) {
		return o1 != null ? o1.equals(o2) : o2 == null;
	}

	public String toString() {
		return Arrays.toString(getArray());
	}

	public void add(int index, Object element) {
		throw new UnsupportedOperationException();
	}

	public boolean addAll(int index, Collection c) {
		throw new UnsupportedOperationException();
	}

	public void addObserver(ConcurrentSingleArrayBagListener obs) {
		observers.add(obs);
	}

	private void fireChanged() {
		for (int i = 0; i < observers.size(); i++) {
			((ConcurrentSingleArrayBagListener)observers.get(i)).onChanged();
		}

	}
}
