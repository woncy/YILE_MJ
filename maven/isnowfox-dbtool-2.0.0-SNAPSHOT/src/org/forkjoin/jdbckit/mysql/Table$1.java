// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Table.java

package org.forkjoin.jdbckit.mysql;

import java.util.Comparator;

// Referenced classes of package org.forkjoin.jdbckit.mysql:
//			Column, Table

class Table$1
	implements Comparator {

	final Table this$0;

	public int compare(Column o1, Column o2) {
		return Integer.valueOf(o1.getSeq()).compareTo(Integer.valueOf(o2.getSeq()));
	}

	public volatile int compare(Object obj, Object obj1) {
		return compare((Column)obj, (Column)obj1);
	}

	Table$1() {
		this.this$0 = Table.this;
		super();
	}
}
