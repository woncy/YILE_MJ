// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   TgwPlatform.java

package com.isnowfox.game.platform.tgw;

import org.slf4j.Logger;

// Referenced classes of package com.isnowfox.game.platform.tgw:
//			TgwPlatform

class TgwPlatform$3
	implements tExceptionHandler {

	final TgwPlatform this$0;

	public void uncaughtException(Thread t, Throwable e) {
		TgwPlatform.access$000().error("Ïß³Ì³Ø´íÎó£¬»á»Ö¸´£¡", e);
	}

	TgwPlatform$3() {
		this.this$0 = TgwPlatform.this;
		super();
	}
}
