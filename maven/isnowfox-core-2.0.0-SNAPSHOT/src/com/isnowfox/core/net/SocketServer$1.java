// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SocketServer.java

package com.isnowfox.core.net;

import com.isnowfox.core.net.message.MessageFactory;
import com.isnowfox.core.net.message.coder.MessageDecoder;
import com.isnowfox.core.net.message.coder.MessageEncoder;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

// Referenced classes of package com.isnowfox.core.net:
//			MessageChannelHandler, SocketServer, NetMessageHandler

static class SocketServer$1 extends ChannelInitializer {

	final MessageFactory val$messageFactory;
	final NetMessageHandler val$messageHandler;

	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast("messageDecoder", new MessageDecoder(val$messageFactory, val$messageHandler));
		p.addLast("messageEncoder", new MessageEncoder());
		p.addLast("handler", new MessageChannelHandler(val$messageHandler));
	}

	protected volatile void initChannel(Channel channel) throws Exception {
		initChannel((SocketChannel)channel);
	}

	SocketServer$1(MessageFactory messagefactory, NetMessageHandler netmessagehandler) {
		val$messageFactory = messagefactory;
		val$messageHandler = netmessagehandler;
		super();
	}
}
