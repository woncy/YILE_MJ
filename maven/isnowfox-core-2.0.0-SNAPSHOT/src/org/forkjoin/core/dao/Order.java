// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Order.java

package org.forkjoin.core.dao;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;

public class Order {
	static class Item {

		String name;
		boolean desc;

		Item(String name, boolean desc) {
			this.name = name;
			this.desc = desc;
		}
	}


	private List items;

	private Order() {
		items = Lists.newArrayList();
	}

	public static Order create() {
		return new Order();
	}

	public static Order create(String name, boolean isDesc) {
		Order p = new Order();
		p.add(name, isDesc);
		return p;
	}

	public static Order desc(String name) {
		return createSingleton(name, true);
	}

	public static Order asc(String name) {
		return createSingleton(name, false);
	}

	public static Order createSingleton(String name, boolean isDesc) {
		return new Order(name, isDesc) {

			final String val$name;
			final boolean val$isDesc;

			public void toSql(StringBuilder sb) {
				sb.append(" ORDER BY ");
				sb.append('`');
				sb.append(name);
				sb.append('`');
				if (isDesc) {
					sb.append(" DESC");
				} else {
					sb.append(" ASC");
				}
			}

			public void add(String name, boolean isDesc) {
				throw new IllegalStateException("单例Order不能添加");
			}

			 {
				name = s;
				isDesc = flag;
				super(null);
			}
		};
	}

	public static Order createByExp(String expression, boolean isDesc) {
		return new Order(expression, isDesc) {

			final String val$expression;
			final boolean val$isDesc;

			public void toSql(StringBuilder sb) {
				sb.append(" ORDER BY ");
				sb.append(expression);
				if (isDesc) {
					sb.append(" DESC");
				} else {
					sb.append(" ASC");
				}
			}

			public void add(String name, boolean isDesc) {
				throw new IllegalStateException("单例Order不能添加");
			}

			 {
				expression = s;
				isDesc = flag;
				super(null);
			}
		};
	}

	public void add(String name, boolean isDesc) {
		items.add(new Item(name, isDesc));
	}

	public String toSql() {
		StringBuilder sb = new StringBuilder();
		toSql(sb);
		return sb.toString();
	}

	public void toSql(StringBuilder sb) {
		if (items.isEmpty()) {
			return;
		}
		sb.append(" ORDER BY ");
		boolean fist = true;
		for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			Item item = (Item)iterator.next();
			if (fist) {
				fist = false;
			} else {
				sb.append(',');
			}
			sb.append('`');
			sb.append(item.name);
			sb.append('`');
			if (item.desc) {
				sb.append(" DESC");
			} else {
				sb.append(" ASC");
			}
		}

	}

}
