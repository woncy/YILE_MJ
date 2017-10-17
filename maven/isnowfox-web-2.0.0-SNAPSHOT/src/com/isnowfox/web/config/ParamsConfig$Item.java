// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ParamsConfig.java

package com.isnowfox.web.config;

import com.isnowfox.web.ParameterType;

// Referenced classes of package com.isnowfox.web.config:
//			ParamsConfig

public static class ParamsConfig$Item {

	private ParameterType type;
	private String name;
	private Class cls;

	public ParameterType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Class getCls() {
		return cls;
	}

	public ParamsConfig$Item(ParameterType type, String name, Class cls) {
		this.type = type;
		this.name = name;
		this.cls = cls;
	}
}
