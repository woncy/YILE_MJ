// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ConcurrentArrayList.java

package com.isnowfox.util.collect;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public final class ConcurrentArrayList
	implements List, RandomAccess {
	private static class COWSubListIterator
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

		COWSubListIterator(List l, int index, int offset, int size) {
			this.offset = offset;
			this.size = size;
			i = l.listIterator(index + offset);
		}
	}

	private static class COWSubList extends AbstractList
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
			l.removeRange(offset, offset + size);
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
			COWSubListIterator cowsublistiterator;
			checkForComodification();
			cowsublistiterator = new COWSubListIterator(l, 0, offset, size);
			lock.unlock();
			return cowsublistiterator;
			Exception exception;
			exception;
			lock.unlock();
			throw exception;
		}

		public ListIterator listIterator(int index) {
			ReentrantLock lock;
			lock = l.lock;
			lock.lock();
			COWSubListIterator cowsublistiterator;
			checkForComodification();
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(index).append(", Size: ").append(size).toString());
			}
			cowsublistiterator = new COWSubListIterator(l, index, offset, size);
			lock.unlock();
			return cowsublistiterator;
			Exception exception;
			exception;
			lock.unlock();
			throw exception;
		}

		public List subList(int fromIndex, int toIndex) {
			ReentrantLock lock;
			lock = l.lock;
			lock.lock();
			COWSubList cowsublist;
			checkForComodification();
			if (fromIndex < 0 || toIndex > size) {
				throw new IndexOutOfBoundsException();
			}
			cowsublist = new COWSubList(l, fromIndex + offset, toIndex + offset);
			lock.unlock();
			return cowsublist;
			Exception exception;
			exception;
			lock.unlock();
			throw exception;
		}

		COWSubList(ConcurrentArrayList list, int fromIndex, int toIndex) {
			l = list;
			expectedArray = l.getArray();
			offset = fromIndex;
			size = toIndex - fromIndex;
		}
	}

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


	final transient ReentrantLock lock;
	private volatile transient Object array[];

	final Object[] getArray() {
		return array;
	}

	final void setArray(Object a[]) {
		array = a;
	}

	public ConcurrentArrayList() {
		lock = new ReentrantLock();
		setArray(new Object[0]);
	}

	public ConcurrentArrayList(Collection c) {
		lock = new ReentrantLock();
		Object elements[] = c.toArray();
		setArray(elements);
	}

	public ConcurrentArrayList(Object elements[]) {
		lock = new ReentrantLock();
		setArray(elements);
	}

	public int size() {
		return getArray().length;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	private static boolean eq(Object o1, Object o2) {
		return o1 != null ? o1.equals(o2) : o2 == null;
	}

	private static int indexOf(Object o, Object elements[], int index, int fence) {
		if (o == null) {
			for (int i = index; i < fence; i++) {
				if (elements[i] == null) {
					return i;
				}
			}

		} else {
			for (int i = index; i < fence; i++) {
				if (o.equals(elements[i])) {
					return i;
				}
			}

		}
		return -1;
	}

	private static int lastIndexOf(Object o, Object elements[], int index) {
		if (o == null) {
			for (int i = index; i >= 0; i--) {
				if (elements[i] == null) {
					return i;
				}
			}

		} else {
			for (int i = index; i >= 0; i--) {
				if (o.equals(elements[i])) {
					return i;
				}
			}

		}
		return -1;
	}

	public boolean contains(Object o) {
		Object elements[] = getArray();
		return indexOf(o, elements, 0, elements.length) >= 0;
	}

	public int indexOf(Object o) {
		Object elements[] = getArray();
		return indexOf(o, elements, 0, elements.length);
	}

	public int indexOf(Object e, int index) {
		Object elements[] = getArray();
		return indexOf(e, elements, index, elements.length);
	}

	public int lastIndexOf(Object o) {
		Object elements[] = getArray();
		return lastIndexOf(o, elements, elements.length - 1);
	}

	public int lastIndexOf(Object e, int index) {
		Object elements[] = getArray();
		return lastIndexOf(e, elements, index);
	}

	public Object[] toArray() {
		return getArray();
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

	private Object get(Object a[], int index) {
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
		obj = oldValue;
		lock.unlock();
		return obj;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public boolean add(Object e) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		boolean flag;
		Object elements[] = getArray();
		int len = elements.length;
		Object newElements[] = Arrays.copyOf(elements, len + 1);
		newElements[len] = e;
		setArray(newElements);
		flag = true;
		lock.unlock();
		return flag;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public void add(int index, Object element) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		Object elements[] = getArray();
		int len = elements.length;
		if (index > len || index < 0) {
			throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(index).append(", Size: ").append(len).toString());
		}
		int numMoved = len - index;
		Object newElements[];
		if (numMoved == 0) {
			newElements = Arrays.copyOf(elements, len + 1);
		} else {
			newElements = new Object[len + 1];
			System.arraycopy(((Object) (elements)), 0, ((Object) (newElements)), 0, index);
			System.arraycopy(((Object) (elements)), index, ((Object) (newElements)), index + 1, numMoved);
		}
		newElements[index] = element;
		setArray(newElements);
		lock.unlock();
		break MISSING_BLOCK_LABEL_151;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public Object remove(int index) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		Object obj;
		Object elements[] = getArray();
		int len = elements.length;
		Object oldValue = get(elements, index);
		int numMoved = len - index - 1;
		if (numMoved == 0) {
			setArray(Arrays.copyOf(elements, len - 1));
		} else {
			Object newElements[] = new Object[len - 1];
			System.arraycopy(((Object) (elements)), 0, ((Object) (newElements)), 0, index);
			System.arraycopy(((Object) (elements)), index + 1, ((Object) (newElements)), index, numMoved);
			setArray(newElements);
		}
		obj = oldValue;
		lock.unlock();
		return obj;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public boolean remove(Object o) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		Object elements[];
		int len;
		int newlen;
		Object newElements[];
		int i;
		elements = getArray();
		len = elements.length;
		if (len == 0) {
			break MISSING_BLOCK_LABEL_145;
		}
		newlen = len - 1;
		newElements = new Object[newlen];
		i = 0;
_L1:
		boolean flag;
		if (i >= newlen) {
			break MISSING_BLOCK_LABEL_118;
		}
		if (!eq(o, elements[i])) {
			break MISSING_BLOCK_LABEL_103;
		}
		for (int k = i + 1; k < len; k++) {
			newElements[k - 1] = elements[k];
		}

		setArray(newElements);
		flag = true;
		lock.unlock();
		return flag;
		newElements[i] = elements[i];
		i++;
		  goto _L1
		if (!eq(o, elements[newlen])) {
			break MISSING_BLOCK_LABEL_145;
		}
		setArray(newElements);
		i = 1;
		lock.unlock();
		return i;
		newlen = 0;
		lock.unlock();
		return newlen;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	private void removeRange(int fromIndex, int toIndex) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		Object elements[] = getArray();
		int len = elements.length;
		if (fromIndex < 0 || toIndex > len || toIndex < fromIndex) {
			throw new IndexOutOfBoundsException();
		}
		int newlen = len - (toIndex - fromIndex);
		int numMoved = len - toIndex;
		if (numMoved == 0) {
			setArray(Arrays.copyOf(elements, newlen));
		} else {
			Object newElements[] = new Object[newlen];
			System.arraycopy(((Object) (elements)), 0, ((Object) (newElements)), 0, fromIndex);
			System.arraycopy(((Object) (elements)), toIndex, ((Object) (newElements)), fromIndex, numMoved);
			setArray(newElements);
		}
		lock.unlock();
		break MISSING_BLOCK_LABEL_126;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public boolean addIfAbsent(Object e) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		Object elements[];
		int len;
		Object newElements[];
		int i;
		elements = getArray();
		len = elements.length;
		newElements = new Object[len + 1];
		i = 0;
_L1:
		boolean flag;
		if (i >= len) {
			break MISSING_BLOCK_LABEL_73;
		}
		if (!eq(e, elements[i])) {
			break MISSING_BLOCK_LABEL_58;
		}
		flag = false;
		lock.unlock();
		return flag;
		newElements[i] = elements[i];
		i++;
		  goto _L1
		newElements[len] = e;
		setArray(newElements);
		i = 1;
		lock.unlock();
		return i;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public boolean containsAll(Collection c) {
		Object elements[] = getArray();
		int len = elements.length;
		for (Iterator iterator1 = c.iterator(); iterator1.hasNext();) {
			Object e = iterator1.next();
			if (indexOf(e, elements, 0, len) < 0) {
				return false;
			}
		}

		return true;
	}

	public boolean removeAll(Collection c) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		boolean flag1;
		Object elements[] = getArray();
		int len = elements.length;
		if (len == 0) {
			break MISSING_BLOCK_LABEL_104;
		}
		int newlen = 0;
		Object temp[] = new Object[len];
		for (int i = 0; i < len; i++) {
			Object element = elements[i];
			if (!c.contains(element)) {
				temp[newlen++] = element;
			}
		}

		if (newlen == len) {
			break MISSING_BLOCK_LABEL_104;
		}
		setArray(Arrays.copyOf(temp, newlen));
		flag1 = true;
		lock.unlock();
		return flag1;
		boolean flag = false;
		lock.unlock();
		return flag;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public boolean retainAll(Collection c) {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		boolean flag1;
		Object elements[] = getArray();
		int len = elements.length;
		if (len == 0) {
			break MISSING_BLOCK_LABEL_104;
		}
		int newlen = 0;
		Object temp[] = new Object[len];
		for (int i = 0; i < len; i++) {
			Object element = elements[i];
			if (c.contains(element)) {
				temp[newlen++] = element;
			}
		}

		if (newlen == len) {
			break MISSING_BLOCK_LABEL_104;
		}
		setArray(Arrays.copyOf(temp, newlen));
		flag1 = true;
		lock.unlock();
		return flag1;
		boolean flag = false;
		lock.unlock();
		return flag;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public int addAllAbsent(Collection c) {
		Object cs[];
		Object uniq[];
		ReentrantLock lock;
		cs = c.toArray();
		if (cs.length == 0) {
			return 0;
		}
		uniq = new Object[cs.length];
		lock = this.lock;
		lock.lock();
		int j;
		Object elements[] = getArray();
		int len = elements.length;
		int added = 0;
		for (int i = 0; i < cs.length; i++) {
			Object e = cs[i];
			if (indexOf(e, elements, 0, len) < 0 && indexOf(e, uniq, 0, added) < 0) {
				uniq[added++] = e;
			}
		}

		if (added > 0) {
			Object newElements[] = Arrays.copyOf(elements, len + added);
			System.arraycopy(((Object) (uniq)), 0, ((Object) (newElements)), len, added);
			setArray(newElements);
		}
		j = added;
		lock.unlock();
		return j;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public void clear() {
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		setArray(new Object[0]);
		lock.unlock();
		break MISSING_BLOCK_LABEL_31;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public boolean addAll(Collection c) {
		ReentrantLock lock;
		Object cs[] = c.toArray();
		if (cs.length == 0) {
			return false;
		}
		lock = this.lock;
		lock.lock();
		boolean flag;
		Object elements[] = getArray();
		setArray(elements);
		flag = true;
		lock.unlock();
		return flag;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	public boolean addAll(int index, Collection c) {
		Object cs[];
		ReentrantLock lock;
		cs = c.toArray();
		lock = this.lock;
		lock.lock();
		Object elements[];
		int len;
		boolean flag;
		elements = getArray();
		len = elements.length;
		if (index > len || index < 0) {
			throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(index).append(", Size: ").append(len).toString());
		}
		if (cs.length != 0) {
			break MISSING_BLOCK_LABEL_92;
		}
		flag = false;
		lock.unlock();
		return flag;
		boolean flag1;
		int numMoved = len - index;
		Object newElements[];
		if (numMoved == 0) {
			newElements = Arrays.copyOf(elements, len + cs.length);
		} else {
			newElements = new Object[len + cs.length];
			System.arraycopy(((Object) (elements)), 0, ((Object) (newElements)), 0, index);
			System.arraycopy(((Object) (elements)), index, ((Object) (newElements)), index + cs.length, numMoved);
		}
		System.arraycopy(((Object) (cs)), 0, ((Object) (newElements)), index, cs.length);
		setArray(newElements);
		flag1 = true;
		lock.unlock();
		return flag1;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		Object elements[] = getArray();
		s.writeInt(elements.length);
		Object aobj[] = elements;
		int i = aobj.length;
		for (int j = 0; j < i; j++) {
			Object element = aobj[j];
			s.writeObject(element);
		}

	}

	public String toString() {
		return Arrays.toString(getArray());
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof List)) {
			return false;
		}
		List list = (List)(List)o;
		Iterator it = list.iterator();
		Object elements[] = getArray();
		int len = elements.length;
		for (int i = 0; i < len; i++) {
			if (!it.hasNext() || !eq(elements[i], it.next())) {
				return false;
			}
		}

		return !it.hasNext();
	}

	public int hashCode() {
		int hashCode = 1;
		Object elements[] = getArray();
		int len = elements.length;
		for (int i = 0; i < len; i++) {
			Object obj = elements[i];
			hashCode = 31 * hashCode + (obj != null ? obj.hashCode() : 0);
		}

		return hashCode;
	}

	public Iterator iterator() {
		return new COWIterator(getArray(), 0);
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
		ReentrantLock lock;
		lock = this.lock;
		lock.lock();
		COWSubList cowsublist;
		Object elements[] = getArray();
		int len = elements.length;
		if (fromIndex < 0 || toIndex > len || fromIndex > toIndex) {
			throw new IndexOutOfBoundsException();
		}
		cowsublist = new COWSubList(this, fromIndex, toIndex);
		lock.unlock();
		return cowsublist;
		Exception exception;
		exception;
		lock.unlock();
		throw exception;
	}

}
