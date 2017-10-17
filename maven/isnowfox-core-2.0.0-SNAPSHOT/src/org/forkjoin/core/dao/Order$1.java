// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Order.java

package org.forkjoin.core.dao;


// Referenced classes of package org.forkjoin.core.dao:
//			Order

static class Order$1 extends Order {

	final String val$name;
	final boolean val$isDesc;

	public void toSql(StringBuilder sb) {
		sb.append(" ORDER BY ");
		sb.append('`');
		sb.append(val$name);
		sb.append('`');
		if (val$isDesc) {
			sb.append(" DESC");
		} else {
			sb.append(" ASC");
		}
	}

	public void add(String name, boolean isDesc) {
		throw new IllegalStateException("单例Order不能添加");
	}

	Order$1(String s, boolean flag) {
		val$name = s;
		val$isDesc = flag;
		super(null);
	}
}
