// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ListUtils.java

package com.isnowfox.util;

import java.util.Collection;
import java.util.LinkedList;

// Referenced classes of package com.isnowfox.util:
//			ListUtils

static class ListUtils$1 extends LinkedList {

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

	ListUtils$1() {
	}
}
