// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ChannelHandler.java

package com.isnowfox.core.net;

import io.netty.channel.*;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package com.isnowfox.core.net:
//			Session, NetHandler

public abstract class ChannelHandler extends SimpleChannelInboundHandler {

	protected static final Logger log = LoggerFactory.getLogger(com/isnowfox/core/net/ChannelHandler);
	public static final AttributeKey SESSION = AttributeKey.valueOf("RmiClientHandler.SESSION");
	private final NetHandler messageHandler;

	public ChannelHandler(NetHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelActive();
		Channel channel = ctx.channel();
		if (log.isDebugEnabled()) {
			log.debug("连接成功{}", channel);
		}
		Session session = messageHandler.createSession(ctx);
		ctx.channel().attr(SESSION).set(session);
		messageHandler.onConnect(session);
	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelInactive();
		if (log.isDebugEnabled()) {
			log.debug("连接断开{}", ctx);
		}
		messageHandler.onDisconnect((Session)ctx.channel().attr(SESSION).get());
		ctx.channel().attr(SESSION).remove();
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		messageHandler.onException((Session)ctx.channel().attr(SESSION).get(), cause);
		ctx.close();
	}

}
