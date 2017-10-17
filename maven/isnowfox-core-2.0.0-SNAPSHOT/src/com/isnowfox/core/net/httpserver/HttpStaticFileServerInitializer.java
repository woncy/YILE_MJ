// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttpStaticFileServerInitializer.java

package com.isnowfox.core.net.httpserver;

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;
import java.io.File;

// Referenced classes of package com.isnowfox.core.net.httpserver:
//			HttpStaticFileServerHandler

public class HttpStaticFileServerInitializer extends ChannelInitializer {

	private final SslContext sslCtx;
	private final File rootDir;

	public HttpStaticFileServerInitializer(SslContext sslCtx, File rootDir) {
		this.sslCtx = sslCtx;
		this.rootDir = rootDir;
	}

	public void initChannel(SocketChannel ch) {
		ChannelPipeline pipeline = ch.pipeline();
		if (sslCtx != null) {
			pipeline.addLast(new ChannelHandler[] {
				sslCtx.newHandler(ch.alloc())
			});
		}
		pipeline.addLast(new ChannelHandler[] {
			new HttpServerCodec()
		});
		pipeline.addLast(new ChannelHandler[] {
			new HttpObjectAggregator(0x10000)
		});
		pipeline.addLast(new ChannelHandler[] {
			new ChunkedWriteHandler()
		});
		pipeline.addLast(new ChannelHandler[] {
			new HttpStaticFileServerHandler(rootDir)
		});
	}

	public volatile void initChannel(Channel channel) throws Exception {
		initChannel((SocketChannel)channel);
	}
}
