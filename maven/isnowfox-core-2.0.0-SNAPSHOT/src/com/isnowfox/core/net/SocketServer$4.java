// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SocketServer.java

package com.isnowfox.core.net;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;

// Referenced classes of package com.isnowfox.core.net:
//			WebSocketChannelHandler, SocketServer, NetPacketHandler

static class SocketServer$4 extends ChannelInitializer {

	final String val$path;
	final NetPacketHandler val$handler;

	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast("httpServerCodec", new HttpServerCodec());
		p.addLast("httpObjectAggregator", new HttpObjectAggregator(0x10000));
		p.addLast("webSocketServerCompressionHandler", new WebSocketServerCompressionHandler());
		p.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler(val$path, null, true));
		p.addLast("handler", new WebSocketChannelHandler(val$handler));
	}

	protected volatile void initChannel(Channel channel) throws Exception {
		initChannel((SocketChannel)channel);
	}

	SocketServer$4(String s, NetPacketHandler netpackethandler) {
		val$path = s;
		val$handler = netpackethandler;
		super();
	}
}
