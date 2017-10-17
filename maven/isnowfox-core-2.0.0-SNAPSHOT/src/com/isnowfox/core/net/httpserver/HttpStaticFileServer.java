// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttpStaticFileServer.java

package com.isnowfox.core.net.httpserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.*;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.util.internal.SystemPropertyUtil;
import java.io.File;
import java.io.PrintStream;

// Referenced classes of package com.isnowfox.core.net.httpserver:
//			HttpStaticFileServerInitializer

public final class HttpStaticFileServer {

	static final boolean SSL;
	static final int PORT;

	public HttpStaticFileServer() {
	}

	public static void main(String args[]) throws Exception {
		start(PORT, SSL, new File(SystemPropertyUtil.get("user.dir")));
	}

	public static void start(int port, boolean ssl, File root) throws Exception {
		SslContext sslCtx;
		EventLoopGroup bossGroup;
		EventLoopGroup workerGroup;
		if (SSL) {
			SelfSignedCertificate ssc = new SelfSignedCertificate();
			sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).sslProvider(SslProvider.JDK).build();
		} else {
			sslCtx = null;
		}
		bossGroup = new NioEventLoopGroup(1);
		workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		((ServerBootstrap)((ServerBootstrap)b.group(bossGroup, workerGroup).channel(io/netty/channel/socket/nio/NioServerSocketChannel)).handler(new LoggingHandler(LogLevel.INFO))).childHandler(new HttpStaticFileServerInitializer(sslCtx, root));
		Channel ch = b.bind(port).sync().channel();
		System.err.println((new StringBuilder()).append("Open your web browser and navigate to ").append(ssl ? "https" : "http").append("://127.0.0.1:").append(PORT).append('/').toString());
		ch.closeFuture().sync();
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		break MISSING_BLOCK_LABEL_239;
		Exception exception;
		exception;
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		throw exception;
	}

	static  {
		SSL = System.getProperty("ssl") != null;
		PORT = Integer.parseInt(System.getProperty("port", SSL ? "8443" : "8080"));
	}
}
