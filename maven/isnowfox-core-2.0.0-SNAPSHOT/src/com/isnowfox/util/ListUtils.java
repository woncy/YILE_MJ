// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ListUtils.java

package com.isnowfox.util;

import java.util.*;

public final class ListUtils {

	public static final LinkedList EMPTY_LINKED_LIST = new LinkedList() {

		public void addFirst(Object o) {
			throw new RuntimeException("不能修改 EMPTY_LINKED_LIST");
		}

		public void addLast(Object o) {
			throw new RuntimeException("不能修改 EMPTY_LINKED_LIST");
		}

		public boolean addAll(Collection c) {
			throw new RuntimeException("不能修改 EMPTY_LINKED_LIST");
		}

		public boolean addAll(int index, Collection c) {
			throw new RuntimeException("不能修改 EMPTY_LINKED_LIST");
		}

		public void add(int index, Object element) {
			throw new RuntimeException("不能修改 EMPTY_LINKED_LIST");
		}

		public void push(Object o) {
			throw new RuntimeException("不能修改 EMPTY_LINKED_LIST");
		}

		public final int size() {
			return 0;
		}

		public final boolean isEmpty() {
			return true;
		}

	};

	public ListUtils() {
	}

	public static final LinkedList emptyLinkedList() {
		return EMPTY_LINKED_LIST;
	}

	public static Object get(List l, int index) {
		return l.size() <= index ? null : l.get(index);
	}

	public static Object get(Object l[], int index) {
		return l.length <= index ? null : l[index];
	}

}
