// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   TgwPlatform.java

package com.isnowfox.game.platform.tgw;

import java.util.concurrent.ThreadFactory;

// Referenced classes of package com.isnowfox.game.platform.tgw:
//			TgwPlatform

class TgwPlatform$4
	implements ThreadFactory {

	final tExceptionHandler val$exceptionHandler;
	final TgwPlatform this$0;

	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r, "tgw AsyncThread");
		thread.setUncaughtExceptionHandler(val$exceptionHandler);
		return thread;
	}

	TgwPlatform$4() {
		this.this$0 = this$0;
		val$exceptionHandler = tExceptionHandler.this;
		super();
	}
}
