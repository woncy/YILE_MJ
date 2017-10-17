// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   GamePayResult.java

package com.isnowfox.game.platform;

import java.util.Map;

// Referenced classes of package com.isnowfox.game.platform:
//			ApiCallback

public interface GamePayResult {

	public abstract void pay(Map map, int i, int j, String s, ApiCallback apicallback);

	public abstract void paySuccess(String s, String s1);

	public abstract void error(Map map, String s, String s1);

	public abstract void login(Map map, String s, String s1);
}
