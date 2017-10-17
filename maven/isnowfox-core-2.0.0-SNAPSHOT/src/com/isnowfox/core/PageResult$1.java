// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PageResult.java

package com.isnowfox.core;

import java.util.List;

// Referenced classes of package com.isnowfox.core:
//			PageResult

static class PageResult$1 extends PageResult {

	private static final long serialVersionUID = 0x5f4174b0200e23cdL;
	private List value;
	final List val$list;
	final int val$pageCount;
	final int val$page;
	final int val$pageSize;
	final int val$count;

	public int getPageCount() {
		return val$pageCount;
	}

	public void setValue(List value) {
		this.value = value;
	}

	public int getStart() {
		return (val$page - 1) * val$pageSize;
	}

	public List getValue() {
		return value;
	}

	public int getCount() {
		return val$count;
	}

	public int getPage() {
		return val$page;
	}

	public int getPageSize() {
		return val$pageSize;
	}

	public boolean isHasPrev() {
		return val$page > 1;
	}

	public boolean isHasNext() {
		return val$page < val$pageCount;
	}

	public String toString() {
		return (new StringBuilder()).append("PageResult [count=").append(val$count).append(", page=").append(val$page).append(", pageSize=").append(val$pageSize).append(", value=").append(value).append("]").toString();
	}

	PageResult$1(List list1, int i, int j, int k, int l) {
		val$list = list1;
		val$pageCount = i;
		val$page = j;
		val$pageSize = k;
		val$count = l;
		super();
		value = val$list;
	}
}
