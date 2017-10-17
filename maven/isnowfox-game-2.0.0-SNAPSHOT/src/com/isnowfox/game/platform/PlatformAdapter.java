// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PlatformAdapter.java

package com.isnowfox.game.platform;

import io.netty.buffer.ByteBuf;
import java.util.Map;

// Referenced classes of package com.isnowfox.game.platform:
//			Platform, User, UserInfo, ApiCallback

public class PlatformAdapter
	implements Platform {

	public PlatformAdapter() {
	}

	public boolean onIn(ByteBuf in, User user) throws Exception {
		return true;
	}

	public UserInfo login(String info) {
		return null;
	}

	public void pay(int i, String s, ApiCallback apicallback) {
	}

	public void logout(String s) {
	}

	public void payResult(Map map) {
	}

	public void close() throws InterruptedException {
	}
}
