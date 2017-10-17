// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ViewConfig.java

package com.isnowfox.web.config;

import com.isnowfox.web.ViewTypeInterface;

// Referenced classes of package com.isnowfox.web.config:
//			ViewConfig

private static class ViewConfig$Item {

	private ViewTypeInterface viewType;
	private Class cls;
	private String key;
	private String value;



	public ViewConfig$Item(ViewTypeInterface viewType, Class cls, String key, String value) {
		this.viewType = viewType;
		this.cls = cls;
		this.key = key;
		this.value = value;
	}
}
