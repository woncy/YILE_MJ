// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Platform.java

package com.isnowfox.game.platform;

import io.netty.buffer.ByteBuf;
import java.util.Map;

// Referenced classes of package com.isnowfox.game.platform:
//			User, UserInfo, ApiCallback

public interface Platform {

	public abstract boolean onIn(ByteBuf bytebuf, User user) throws Exception;

	public abstract UserInfo login(String s);

	public abstract void pay(int i, String s, ApiCallback apicallback);

	public abstract void logout(String s);

	public abstract void payResult(Map map);

	public abstract void close() throws InterruptedException;
}
