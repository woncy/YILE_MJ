// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   IndexAction.java

package com.isnowfox.cms.action;

import com.google.common.collect.Maps;
import com.isnowfox.util.RandomUtils;
import java.util.Date;
import java.util.Map;

public class IndexAction {

	public IndexAction() {
	}

	public Object index(String userAgent) {
		Map map = Maps.newHashMap();
		map.put("test", new Date());
		map.put("userAgent", userAgent);
		if (RandomUtils.randInt(1, 2) == 1) {
			return new Date();
		} else {
			return map;
		}
	}
}
