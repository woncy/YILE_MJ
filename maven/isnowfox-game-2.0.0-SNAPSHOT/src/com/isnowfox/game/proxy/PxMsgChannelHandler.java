// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PxMsgChannelHandler.java

package com.isnowfox.game.proxy;

import com.isnowfox.core.net.ChannelHandler;
import com.isnowfox.core.net.Session;
import com.isnowfox.game.proxy.message.PxMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;

// Referenced classes of package com.isnowfox.game.proxy:
//			PxMsgHandler

public class PxMsgChannelHandler extends ChannelHandler {

	private PxMsgHandler messageHandler;

	public PxMsgChannelHandler(PxMsgHandler messageHandler) {
		super(messageHandler);
		this.messageHandler = messageHandler;
	}

	protected void channelRead0(ChannelHandlerContext ctx, PxMsg msg) throws Exception {
		Session session = (Session)ctx.channel().attr(SESSION).get();
		msg.setSession(session);
		messageHandler.onMessage(msg);
	}

	protected volatile void channelRead0(ChannelHandlerContext channelhandlercontext, Object obj) throws Exception {
		channelRead0(channelhandlercontext, (PxMsg)obj);
	}
}
