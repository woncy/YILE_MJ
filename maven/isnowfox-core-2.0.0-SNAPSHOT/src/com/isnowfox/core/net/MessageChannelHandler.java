// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MessageChannelHandler.java

package com.isnowfox.core.net;

import com.isnowfox.core.net.message.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import org.slf4j.Logger;

// Referenced classes of package com.isnowfox.core.net:
//			ChannelHandler, Session, NetMessageHandler

public class MessageChannelHandler extends ChannelHandler {

	private NetMessageHandler messageHandler;

	public MessageChannelHandler(NetMessageHandler messageHandler) {
		super(messageHandler);
		this.messageHandler = messageHandler;
	}

	protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("收到消息!{}", msg);
		}
		Session session = (Session)ctx.channel().attr(SESSION).get();
		msg.setSession(session);
		messageHandler.onMessage(msg);
	}

	protected volatile void channelRead0(ChannelHandlerContext channelhandlercontext, Object obj) throws Exception {
		channelRead0(channelhandlercontext, (Message)obj);
	}
}
