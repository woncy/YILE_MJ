// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PxMsgHandler.java

package com.isnowfox.game.proxy;

import com.isnowfox.core.net.NetHandler;
import com.isnowfox.game.proxy.message.PxMsg;

public interface PxMsgHandler
	extends NetHandler {

	public abstract void onMessage(PxMsg pxmsg) throws Exception;
}
