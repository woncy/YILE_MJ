// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HeaderConfig.java

package com.isnowfox.web.config;


// Referenced classes of package com.isnowfox.web.config:
//			HeaderConfig

public static class HeaderConfig$Item {

	public final String name;
	public final String value;

	public String toString() {
		return (new StringBuilder()).append("Item [name=").append(name).append(", value=").append(value).append("]").toString();
	}

	public HeaderConfig$Item(String name, String value) {
		this.name = name;
		this.value = value;
	}
}
