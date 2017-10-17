// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PxMsg.java

package com.isnowfox.game.proxy.message;

import com.isnowfox.core.net.Session;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public interface PxMsg {

	public static final int HEAD_LENGTH = 5;

	public abstract void encode(ByteBuf bytebuf) throws Exception;

	public abstract void decode(ByteBuf bytebuf, ChannelHandlerContext channelhandlercontext) throws Exception;

	public abstract int getType();

	public abstract Session getSession();

	public abstract void setSession(Session session);
}
