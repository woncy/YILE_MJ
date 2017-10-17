// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PacketChannelHandler.java

package com.isnowfox.core.net;

import com.isnowfox.core.net.message.Packet;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;

// Referenced classes of package com.isnowfox.core.net:
//			ChannelHandler, Session, NetPacketHandler

public class PacketChannelHandler extends ChannelHandler {

	private NetPacketHandler messageHandler;

	public PacketChannelHandler(NetPacketHandler messageHandler) {
		super(messageHandler);
		this.messageHandler = messageHandler;
	}

	protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
		Session session = (Session)ctx.channel().attr(SESSION).get();
		msg.setSession(session);
		messageHandler.onPacket(msg);
	}

	protected volatile void channelRead0(ChannelHandlerContext channelhandlercontext, Object obj) throws Exception {
		channelRead0(channelhandlercontext, (Packet)obj);
	}
}
