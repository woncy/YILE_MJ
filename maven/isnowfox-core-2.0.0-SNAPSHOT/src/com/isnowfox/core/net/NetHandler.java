// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   NetHandler.java

package com.isnowfox.core.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

// Referenced classes of package com.isnowfox.core.net:
//			Session

public interface NetHandler {

	public abstract void onConnect(Session session) throws Exception;

	public abstract void onDisconnect(Session session) throws Exception;

	public abstract void onException(Session session, Throwable throwable) throws Exception;

	public abstract Session createSession(ChannelHandlerContext channelhandlercontext) throws Exception;

	public abstract boolean onIn(Session session, ByteBuf bytebuf) throws Exception;
}
