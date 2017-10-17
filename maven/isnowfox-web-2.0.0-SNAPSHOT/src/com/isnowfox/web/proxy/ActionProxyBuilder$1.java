// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ActionProxyBuilder.java

package com.isnowfox.web.proxy;

import com.isnowfox.web.ParameterType;
import com.isnowfox.web.config.ViewConfig;

// Referenced classes of package com.isnowfox.web.proxy:
//			ActionProxyBuilder

static class ActionProxyBuilder$1 {

	static final int $SwitchMap$com$isnowfox$web$ParameterType[];
	static final int $SwitchMap$com$isnowfox$web$config$ViewConfig$MapType[];

	static  {
		$SwitchMap$com$isnowfox$web$config$ViewConfig$MapType = new int[com.isnowfox.web.config.lues().length];
		try {
			$SwitchMap$com$isnowfox$web$config$ViewConfig$MapType[com.isnowfox.web.config.LY.dinal()] = 1;
		}
		catch (NoSuchFieldError nosuchfielderror) { }
		$SwitchMap$com$isnowfox$web$ParameterType = new int[ParameterType.values().length];
		try {
			$SwitchMap$com$isnowfox$web$ParameterType[ParameterType.COOKIE.ordinal()] = 1;
		}
		catch (NoSuchFieldError nosuchfielderror1) { }
		try {
			$SwitchMap$com$isnowfox$web$ParameterType[ParameterType.REQUEST.ordinal()] = 2;
		}
		catch (NoSuchFieldError nosuchfielderror2) { }
		try {
			$SwitchMap$com$isnowfox$web$ParameterType[ParameterType.HEADER.ordinal()] = 3;
		}
		catch (NoSuchFieldError nosuchfielderror3) { }
		try {
			$SwitchMap$com$isnowfox$web$ParameterType[ParameterType.IOC.ordinal()] = 4;
		}
		catch (NoSuchFieldError nosuchfielderror4) { }
	}
}
