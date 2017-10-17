// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PageResult.java

package com.isnowfox.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class PageResult
	implements Serializable {

	private static final long serialVersionUID = 0xdab9603cfe1c0804L;

	public PageResult() {
	}

	public static final PageResult createPage(int count, int page, int pageSize) {
		return createPage(count, page, pageSize, null);
	}

	public static final PageResult createPageByAllList(int page, int pageSize, List list) {
		int count = list.size();
		PageResult result = createPage(count, page, pageSize, null);
		int start = result.getStart();
		if (start < count) {
			result.setValue(new ArrayList(list.subList(result.getStart(), result.getStart() + Math.min(count - start, pageSize))));
		}
		return result;
	}

	public static final ArrayList createListByFromTo(int from, int to, List list) {
		int count = list.size();
		ArrayList result = new ArrayList();
		int start = from - 1 <= 0 ? 0 : from - 1;
		int end = to <= count ? to - 1 : count - 1;
		if (end >= start) {
			for (int i = start; i <= end; i++) {
				result.add(list.get(i));
			}

		}
		return result;
	}

	public static final PageResult createPage(int count, int page, int pageSize, List list) {
		int m = count % pageSize;
		int s = count / pageSize;
		int pageCount = m <= 0 ? s : ++s;
		return new PageResult(list, pageCount, page, pageSize, count) {

			private static final long serialVersionUID = 0x5f4174b0200e23cdL;
			private List value;
			final List val$list;
			final int val$pageCount;
			final int val$page;
			final int val$pageSize;
			final int val$count;

			public int getPageCount() {
				return pageCount;
			}

			public void setValue(List value) {
				this.value = value;
			}

			public int getStart() {
				return (page - 1) * pageSize;
			}

			public List getValue() {
				return value;
			}

			public int getCount() {
				return count;
			}

			public int getPage() {
				return page;
			}

			public int getPageSize() {
				return pageSize;
			}

			public boolean isHasPrev() {
				return page > 1;
			}

			public boolean isHasNext() {
				return page < pageCount;
			}

			public String toString() {
				return (new StringBuilder()).append("PageResult [count=").append(count).append(", page=").append(page).append(", pageSize=").append(pageSize).append(", value=").append(value).append("]").toString();
			}

			 {
				list = list1;
				pageCount = i;
				page = j;
				pageSize = k;
				count = l;
				super();
				value = list;
			}
		};
	}

	public abstract int getPageCount();

	public abstract int getStart();

	public abstract List getValue();

	public abstract void setValue(List list);

	public abstract int getCount();

	public abstract int getPage();

	public abstract int getPageSize();

	public abstract boolean isHasPrev();

	public abstract boolean isHasNext();

	public abstract String toString();
}
