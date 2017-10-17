// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SocketServer.java

package com.isnowfox.core.net;

import com.isnowfox.core.net.message.MessageFactory;
import com.isnowfox.core.net.message.coder.CrcEncryptPacketDecoder;
import com.isnowfox.core.net.message.coder.CrcEncryptPacketEncoder;
import com.isnowfox.core.net.message.coder.MessageDecoder;
import com.isnowfox.core.net.message.coder.MessageEncoder;
import com.isnowfox.core.net.message.coder.PacketDecoder;
import com.isnowfox.core.net.message.coder.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;
import java.net.ConnectException;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package com.isnowfox.core.net:
//			NetMessageHandler, NetPacketHandler, WebSocketChannelHandler, CrcEncryptChannelHandler, 
//			PacketChannelHandler, MessageChannelHandler

public final class SocketServer {

	private static final int DEFAULT_BOSS_THREAD_NUMS = 4;
	private static final int DEFAULT_WORKER_THREAD_NUMS = 8;
	private final int port;
	private final ChannelInitializer initializer;
	private final int bossThreadNums;
	private final int workerThreadNums;
	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/core/net/SocketServer);
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private Channel channel;

	public static final SocketServer createMessageServer(int port, MessageFactory messageFactory, NetMessageHandler messageHandler) {
		return createMessageServer(port, messageFactory, messageHandler, 4, 8);
	}

	public static final SocketServer createMessageServer(int port, MessageFactory messageFactory, NetMessageHandler messageHandler, int bossThreadNums, int workerThreadNums) {
		return new SocketServer(port, new ChannelInitializer(messageFactory, messageHandler) {

			final MessageFactory val$messageFactory;
			final NetMessageHandler val$messageHandler;

			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast("messageDecoder", new MessageDecoder(messageFactory, messageHandler));
				p.addLast("messageEncoder", new MessageEncoder());
				p.addLast("handler", new MessageChannelHandler(messageHandler));
			}

			protected volatile void initChannel(Channel channel1) throws Exception {
				initChannel((SocketChannel)channel1);
			}

			 {
				messageFactory = messagefactory;
				messageHandler = netmessagehandler;
				super();
			}
		}, bossThreadNums, workerThreadNums);
	}

	public static final SocketServer createPacketServer(int port, NetPacketHandler handler) {
		return createPacketServer(port, handler, 4, 8);
	}

	public static final SocketServer createPacketServer(int port, NetPacketHandler handler, int bossThreadNums, int workerThreadNums) {
		return new SocketServer(port, new ChannelInitializer(handler) {

			final NetPacketHandler val$handler;

			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast("packetDecoder", new PacketDecoder(handler));
				p.addLast("packetEncoder", new PacketEncoder());
				p.addLast("handler", new PacketChannelHandler(handler));
			}

			protected volatile void initChannel(Channel channel1) throws Exception {
				initChannel((SocketChannel)channel1);
			}

			 {
				handler = netpackethandler;
				super();
			}
		}, bossThreadNums, workerThreadNums);
	}

	public static final SocketServer createCrcEncryptServer(int port, NetPacketHandler handler, int bossThreadNums, int workerThreadNums, int zipSize, int encryptValue) {
		return new SocketServer(port, new ChannelInitializer(handler, zipSize, encryptValue) {

			final NetPacketHandler val$handler;
			final int val$zipSize;
			final int val$encryptValue;

			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast("packetDecoder", new CrcEncryptPacketDecoder(handler));
				p.addLast("packetEncoder", new CrcEncryptPacketEncoder());
				p.addLast("handler", new CrcEncryptChannelHandler(handler, zipSize, encryptValue));
			}

			protected volatile void initChannel(Channel channel1) throws Exception {
				initChannel((SocketChannel)channel1);
			}

			 {
				handler = netpackethandler;
				zipSize = i;
				encryptValue = j;
				super();
			}
		}, bossThreadNums, workerThreadNums);
	}

	public static final SocketServer createWebSocketServer(int port, NetPacketHandler handler, int bossThreadNums, int workerThreadNums, String path) {
		return new SocketServer(port, new ChannelInitializer(path, handler) {

			final String val$path;
			final NetPacketHandler val$handler;

			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast("httpServerCodec", new HttpServerCodec());
				p.addLast("httpObjectAggregator", new HttpObjectAggregator(0x10000));
				p.addLast("webSocketServerCompressionHandler", new WebSocketServerCompressionHandler());
				p.addLast("webSocketServerProtocolHandler", new WebSocketServerProtocolHandler(path, null, true));
				p.addLast("handler", new WebSocketChannelHandler(handler));
			}

			protected volatile void initChannel(Channel channel1) throws Exception {
				initChannel((SocketChannel)channel1);
			}

			 {
				path = s;
				handler = netpackethandler;
				super();
			}
		}, bossThreadNums, workerThreadNums);
	}

	private SocketServer(int port, ChannelInitializer initializer, int bossThreadNums, int workerThreadNums) {
		this.port = port;
		this.initializer = initializer;
		this.bossThreadNums = bossThreadNums;
		this.workerThreadNums = workerThreadNums;
	}

	public void start() throws Exception {
		if (bossGroup != null) {
			throw new ConnectException("不能重复启动监听");
		} else {
			log.info("启动端口监听！{}", Integer.valueOf(port));
			bossGroup = new NioEventLoopGroup(bossThreadNums);
			workerGroup = new NioEventLoopGroup(workerThreadNums);
			ServerBootstrap b = new ServerBootstrap();
			((ServerBootstrap)((ServerBootstrap)b.group(bossGroup, workerGroup).channel(io/netty/channel/socket/nio/NioServerSocketChannel)).handler(new LoggingHandler(LogLevel.INFO))).childHandler(initializer);
			ChannelFuture f = b.bind(port);
			channel = f.channel();
			f.get();
			return;
		}
	}

	public void close() throws InterruptedException, ExecutionException {
		channel.close().get();
		bossGroup.shutdownGracefully().get();
		workerGroup.shutdownGracefully().get();
	}

	public int getPort() {
		return port;
	}

}
