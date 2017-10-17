// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   NetPacketHandler.java

package com.isnowfox.core.net;

import com.isnowfox.core.net.message.Packet;

// Referenced classes of package com.isnowfox.core.net:
//			NetHandler

public interface NetPacketHandler
	extends NetHandler {

	public abstract void onPacket(Packet packet) throws Exception;
}
