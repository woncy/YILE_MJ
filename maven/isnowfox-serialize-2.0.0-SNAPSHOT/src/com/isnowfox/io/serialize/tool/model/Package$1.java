// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Package.java

package com.isnowfox.io.serialize.tool.model;

import java.util.Comparator;

// Referenced classes of package com.isnowfox.io.serialize.tool.model:
//			Message, Package

class Package$1
	implements Comparator {

	final Package this$0;

	public int compare(Message o1, Message o2) {
		return o1.getName().compareTo(o2.getName());
	}

	public volatile int compare(Object obj, Object obj1) {
		return compare((Message)obj, (Message)obj1);
	}

	Package$1() {
		this.this$0 = Package.this;
		super();
	}
}
