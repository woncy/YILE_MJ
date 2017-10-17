// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ShortList.java

package com.isnowfox.util.collect.primitive;

import java.util.Arrays;

public final class ShortList {

	private static final short EMPTY_ELEMENTDATA[];
	private static final int DEFAULT_CAPACITY = 10;
	public static final ShortList EMPTY;
	private static final int MAX_ARRAY_SIZE = 0x7ffffff7;
	private short objects[];
	private int size;

	public ShortList(short array[], int length) {
		objects = array;
		size = length;
	}

	public ShortList() {
		objects = EMPTY_ELEMENTDATA;
		size = 0;
	}

	public ShortList(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException((new StringBuilder()).append("Illegal Capacity: ").append(initialCapacity).toString());
		} else {
			objects = new short[initialCapacity];
			return;
		}
	}

	public boolean contains(short item) {
		for (int i = 0; i < size; i++) {
			if (objects[i] == item) {
				return true;
			}
		}

		return false;
	}

	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof com.sun.org.apache.xerces.internal.xs.ShortList)) {
			return false;
		}
		com.sun.org.apache.xerces.internal.xs.ShortList rhs = (com.sun.org.apache.xerces.internal.xs.ShortList)obj;
		if (size != rhs.getLength()) {
			return false;
		}
		for (int i = 0; i < size; i++) {
			if (objects[i] != rhs.item(i)) {
				return false;
			}
		}

		return true;
	}

	public boolean add(short value) {
		ensureCapacityInternal(size + 1);
		objects[size++] = value;
		return true;
	}

	private void ensureCapacityInternal(int minCapacity) {
		if (objects == EMPTY_ELEMENTDATA) {
			minCapacity = Math.max(10, minCapacity);
		}
		ensureExplicitCapacity(minCapacity);
	}

	private void ensureExplicitCapacity(int minCapacity) {
		if (minCapacity - objects.length > 0) {
			grow(minCapacity);
		}
	}

	private void grow(int minCapacity) {
		int oldCapacity = objects.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		if (newCapacity - minCapacity < 0) {
			newCapacity = minCapacity;
		}
		if (newCapacity - 0x7ffffff7 > 0) {
			newCapacity = hugeCapacity(minCapacity);
		}
		objects = Arrays.copyOf(objects, newCapacity);
	}

	private static int hugeCapacity(int minCapacity) {
		if (minCapacity < 0) {
			throw new OutOfMemoryError();
		} else {
			return minCapacity <= 0x7ffffff7 ? 0x7ffffff7 : 0x7fffffff;
		}
	}

	public short get(int index) {
		if (index >= 0 && index < size) {
			return objects[index];
		} else {
			throw new IndexOutOfBoundsException((new StringBuilder()).append("Index: ").append(index).toString());
		}
	}

	public int size() {
		return size;
	}

	public boolean remove(short value) {
		for (int index = 0; index < size; index++) {
			if (value == objects[index]) {
				fastRemove(index);
				return true;
			}
		}

		return false;
	}

	public short removeByIndex(int index) {
		rangeCheck(index);
		short oldValue = objects[index];
		int numMoved = size - index - 1;
		if (numMoved > 0) {
			System.arraycopy(objects, index + 1, objects, index, numMoved);
		}
		size--;
		return oldValue;
	}

	private void rangeCheck(int index) {
		if (index >= size) {
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		} else {
			return;
		}
	}

	private void fastRemove(int index) {
		int numMoved = size - index - 1;
		if (numMoved > 0) {
			System.arraycopy(objects, index + 1, objects, index, numMoved);
		}
		size--;
	}

	private String outOfBoundsMsg(int index) {
		return (new StringBuilder()).append("Index: ").append(index).append(", Size: ").append(size).toString();
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public short[] cloneArray() {
		short copy[] = new short[size];
		System.arraycopy(objects, 0, copy, 0, size);
		return copy;
	}

	public short[] cloneArray(short value) {
		int index = -1;
		for (int i = 0; i < size; i++) {
			if (objects[i] == value) {
				index = i;
				short copy[] = new short[size - 1];
				if (index > 0) {
					System.arraycopy(objects, 0, copy, 0, index);
				}
				if (index < copy.length) {
					System.arraycopy(objects, index + 1, copy, index, copy.length - index);
				}
				return copy;
			}
		}

		short copy[] = new short[size];
		System.arraycopy(objects, 0, copy, 0, size);
		return copy;
	}

	static  {
		EMPTY_ELEMENTDATA = new short[0];
		EMPTY = new ShortList(EMPTY_ELEMENTDATA, 0);
	}
}
