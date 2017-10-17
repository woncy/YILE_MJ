// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   GridPageResult.java

package org.forkjoin.core.dao.grid;

import com.isnowfox.core.PageResult;
import java.util.List;

// Referenced classes of package org.forkjoin.core.dao.grid:
//			Columns

public abstract class GridPageResult extends PageResult {

	private static final long serialVersionUID = 0xd39cc7315ce9f43dL;

	public GridPageResult() {
	}

	public abstract Columns getColumns();

	public static final GridPageResult createGridPage(int count, int page, int pageSize, Columns columns, List list) {
		int m = count % pageSize;
		int s = count / pageSize;
		int pageCount = m <= 0 ? s : ++s;
		return new GridPageResult(list, pageCount, page, pageSize, count, columns) {

			private static final long serialVersionUID = 0x5f4174b0200e23cdL;
			private List value;
			final List val$list;
			final int val$pageCount;
			final int val$page;
			final int val$pageSize;
			final int val$count;
			final Columns val$columns;

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
				return (new StringBuilder()).append("PageResult [count=").append(count).append(", page=").append(page).append(", pageSize=").append(pageSize).append(", value=").append(value).append(", columns[").append(columns).append("]]").toString();
			}

			public Columns getColumns() {
				return columns;
			}

			 {
				list = list1;
				pageCount = i;
				page = j;
				pageSize = k;
				count = l;
				columns = columns1;
				super();
				value = list;
			}
		};
	}
}
