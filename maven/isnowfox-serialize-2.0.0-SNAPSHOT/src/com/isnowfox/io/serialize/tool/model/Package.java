// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Package.java

package com.isnowfox.io.serialize.tool.model;

import java.util.*;

// Referenced classes of package com.isnowfox.io.serialize.tool.model:
//			Message

public class Package {

	private Set sets;
	private Map map;
	private String name;
	private int type;

	public Package() {
		sets = new TreeSet(new Comparator() {

			final Package this$0;

			public int compare(Message o1, Message o2) {
				return o1.getName().compareTo(o2.getName());
			}

			public volatile int compare(Object obj, Object obj1) {
				return compare((Message)obj, (Message)obj1);
			}

			 {
				this.this$0 = Package.this;
				super();
			}
		});
		map = new HashMap();
	}

	public void add(Message msg) {
		msg.setPack(this);
		sets.add(msg);
		map.put(msg.getName(), msg);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConstantName(int left) {
		String str = name.toUpperCase();
		return String.format((new StringBuilder()).append("%-").append(left).append("s").toString(), new Object[] {
			str.replace('.', '_')
		});
	}

	public Set getValues() {
		return sets;
	}

	public Message get(String name) {
		return (Message)map.get(name);
	}

	public final int getType() {
		return type;
	}

	public final void setType(int type) {
		this.type = type;
	}
}
