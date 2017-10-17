// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PxServer.java

package com.isnowfox.game.proxy;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

// Referenced classes of package com.isnowfox.game.proxy:
//			PxMsgDecoder, PxMsgEncoder, PxMsgChannelHandler, PxServer, 
//			PxMsgFactory, PxMsgHandler

static class PxServer$1 extends ChannelInitializer {

	final PxMsgFactory val$pxMsgFactory;
	final PxMsgHandler val$messageHandler;

	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast("messageDecoder", new PxMsgDecoder(val$pxMsgFactory));
		p.addLast("messageEncoder", new PxMsgEncoder());
		p.addLast("handler", new PxMsgChannelHandler(val$messageHandler));
	}

	protected volatile void initChannel(Channel channel) throws Exception {
		initChannel((SocketChannel)channel);
	}

	PxServer$1(PxMsgFactory pxmsgfactory, PxMsgHandler pxmsghandler) {
		val$pxMsgFactory = pxmsgfactory;
		val$messageHandler = pxmsghandler;
		super();
	}
}
