// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ActionConfig.java

package com.isnowfox.web.config;


// Referenced classes of package com.isnowfox.web.config:
//			ActionConfig

public static final class ActionConfig$LiefCycleType extends Enum {

	public static final ActionConfig$LiefCycleType SINGLETON;
	public static final ActionConfig$LiefCycleType REQUEST;
	private static final ActionConfig$LiefCycleType $VALUES[];

	public static ActionConfig$LiefCycleType[] values() {
		return (ActionConfig$LiefCycleType[])$VALUES.clone();
	}

	public static ActionConfig$LiefCycleType valueOf(String name) {
		return (ActionConfig$LiefCycleType)Enum.valueOf(com/isnowfox/web/config/ActionConfig$LiefCycleType, name);
	}

	static  {
		SINGLETON = new ActionConfig$LiefCycleType("SINGLETON", 0);
		REQUEST = new ActionConfig$LiefCycleType("REQUEST", 1);
		$VALUES = (new ActionConfig$LiefCycleType[] {
			SINGLETON, REQUEST
		});
	}

	private ActionConfig$LiefCycleType(String s, int i) {
		super(s, i);
	}
}
