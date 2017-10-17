// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ViewConfig.java

package com.isnowfox.web.config;


// Referenced classes of package com.isnowfox.web.config:
//			ViewConfig

public static final class ViewConfig$MapType extends Enum {

	public static final ViewConfig$MapType CLASS;
	public static final ViewConfig$MapType VIEW;
	public static final ViewConfig$MapType ONLY;
	public static final ViewConfig$MapType EL;
	public static final ViewConfig$MapType NULL;
	private static final ViewConfig$MapType $VALUES[];

	public static ViewConfig$MapType[] values() {
		return (ViewConfig$MapType[])$VALUES.clone();
	}

	public static ViewConfig$MapType valueOf(String name) {
		return (ViewConfig$MapType)Enum.valueOf(com/isnowfox/web/config/ViewConfig$MapType, name);
	}

	static  {
		CLASS = new ViewConfig$MapType("CLASS", 0);
		VIEW = new ViewConfig$MapType("VIEW", 1);
		ONLY = new ViewConfig$MapType("ONLY", 2);
		EL = new ViewConfig$MapType("EL", 3);
		NULL = new ViewConfig$MapType("NULL", 4);
		$VALUES = (new ViewConfig$MapType[] {
			CLASS, VIEW, ONLY, EL, NULL
		});
	}

	private ViewConfig$MapType(String s, int i) {
		super(s, i);
	}
}
