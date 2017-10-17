// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SocketServer.java

package com.isnowfox.core.net;

import com.isnowfox.core.net.message.coder.CrcEncryptPacketDecoder;
import com.isnowfox.core.net.message.coder.CrcEncryptPacketEncoder;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;

// Referenced classes of package com.isnowfox.core.net:
//			CrcEncryptChannelHandler, SocketServer, NetPacketHandler

static class SocketServer$3 extends ChannelInitializer {

	final NetPacketHandler val$handler;
	final int val$zipSize;
	final int val$encryptValue;

	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast("packetDecoder", new CrcEncryptPacketDecoder(val$handler));
		p.addLast("packetEncoder", new CrcEncryptPacketEncoder());
		p.addLast("handler", new CrcEncryptChannelHandler(val$handler, val$zipSize, val$encryptValue));
	}

	protected volatile void initChannel(Channel channel) throws Exception {
		initChannel((SocketChannel)channel);
	}

	SocketServer$3(NetPacketHandler netpackethandler, int i, int j) {
		val$handler = netpackethandler;
		val$zipSize = i;
		val$encryptValue = j;
		super();
	}
}
