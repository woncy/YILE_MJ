// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MessageBuilder.java

package com.isnowfox.io.serialize.tool;

import java.util.Comparator;

// Referenced classes of package com.isnowfox.io.serialize.tool:
//			MessageBuilder

class MessageBuilder$1
	implements Comparator {

	final MessageBuilder this$0;

	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}

	public volatile int compare(Object obj, Object obj1) {
		return compare((String)obj, (String)obj1);
	}

	MessageBuilder$1() {
		this.this$0 = MessageBuilder.this;
		super();
	}
}
