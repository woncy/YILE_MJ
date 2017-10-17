// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   NetMessageHandler.java

package com.isnowfox.core.net;

import com.isnowfox.core.net.message.Message;

// Referenced classes of package com.isnowfox.core.net:
//			NetHandler

public interface NetMessageHandler
	extends NetHandler {

	public abstract void onMessage(Message message) throws Exception;
}
