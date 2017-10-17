// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SocketClient.java

package com.isnowfox.core.net;

import com.isnowfox.core.net.message.coder.PacketDecoder;
import com.isnowfox.core.net.message.coder.PacketEncoder;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

// Referenced classes of package com.isnowfox.core.net:
//			PacketChannelHandler, SocketClient, NetPacketHandler

static class SocketClient$2 extends ChannelInitializer {

	final NetPacketHandler val$handler;

	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast("packetDecoder", new PacketDecoder(val$handler));
		p.addLast("packetEncoder", new PacketEncoder());
		p.addLast("handler", new PacketChannelHandler(val$handler));
	}

	protected volatile void initChannel(Channel channel) throws Exception {
		initChannel((SocketChannel)channel);
	}

	SocketClient$2(NetPacketHandler netpackethandler) {
		val$handler = netpackethandler;
		super();
	}
}
