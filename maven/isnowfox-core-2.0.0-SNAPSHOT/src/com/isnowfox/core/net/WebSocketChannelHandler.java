// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   WebSocketChannelHandler.java

package com.isnowfox.core.net;

import com.isnowfox.core.net.message.MessageProtocol;
import com.isnowfox.core.net.message.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package com.isnowfox.core.net:
//			Session, NetPacketHandler

public class WebSocketChannelHandler extends SimpleChannelInboundHandler {

	protected static final Logger log = LoggerFactory.getLogger(com/isnowfox/core/net/WebSocketChannelHandler);
	private static final AttributeKey SESSION = AttributeKey.valueOf("RmiClientHandler.SESSION");
	private final NetPacketHandler messageHandler;

	public WebSocketChannelHandler(NetPacketHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel channel = ctx.channel();
		if (log.isDebugEnabled()) {
			log.debug("连接成功{}", channel);
		}
		Session session = messageHandler.createSession(ctx);
		ctx.channel().attr(SESSION).set(session);
		messageHandler.onConnect(session);
		super.channelActive(ctx);
	}

	protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame frame) throws Exception {
		ByteBuf content = frame.content();
		Session session = (Session)ctx.channel().attr(SESSION).get();
		content.retain();
		Packet msg = new Packet(content.readableBytes(), (byte)0, content, 0);
		msg.setSession(session);
		messageHandler.onPacket(msg);
	}

	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("连接断开{}", ctx);
		}
		messageHandler.onDisconnect((Session)ctx.channel().attr(SESSION).get());
		ctx.channel().attr(SESSION).remove();
		super.channelInactive(ctx);
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		messageHandler.onException((Session)ctx.channel().attr(SESSION).get(), cause);
		super.exceptionCaught(ctx, cause);
		ctx.close();
	}

	protected volatile void channelRead0(ChannelHandlerContext channelhandlercontext, Object obj) throws Exception {
		channelRead0(channelhandlercontext, (WebSocketFrame)obj);
	}

}
