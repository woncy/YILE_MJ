// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PxServer.java

package com.isnowfox.game.proxy;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import java.net.ConnectException;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package com.isnowfox.game.proxy:
//			PxMsgFactory, PxMsgHandler, PxMsgDecoder, PxMsgEncoder, 
//			PxMsgChannelHandler

public final class PxServer {

	private static final int DEFAULT_BOSS_THREAD_NUMS = 4;
	private static final int DEFAULT_WORKER_THREAD_NUMS = 8;
	private final int port;
	private final ChannelInitializer initializer;
	private final int bossThreadNums;
	private final int workerThreadNums;
	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/game/proxy/PxServer);
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;

	public static final PxServer create(int port, PxMsgHandler messageHandler) {
		return create(port, messageHandler, 4, 8);
	}

	public static final PxServer create(int port, PxMsgHandler messageHandler, int bossThreadNums, int workerThreadNums) {
		return create(new PxMsgFactory(), port, messageHandler, bossThreadNums, workerThreadNums);
	}

	public static final PxServer create(PxMsgFactory pxMsgFactory, int port, PxMsgHandler messageHandler, int bossThreadNums, int workerThreadNums) {
		return new PxServer(port, new ChannelInitializer(pxMsgFactory, messageHandler) {

			final PxMsgFactory val$pxMsgFactory;
			final PxMsgHandler val$messageHandler;

			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast("messageDecoder", new PxMsgDecoder(pxMsgFactory));
				p.addLast("messageEncoder", new PxMsgEncoder());
				p.addLast("handler", new PxMsgChannelHandler(messageHandler));
			}

			protected volatile void initChannel(Channel channel) throws Exception {
				initChannel((SocketChannel)channel);
			}

			 {
				pxMsgFactory = pxmsgfactory;
				messageHandler = pxmsghandler;
				super();
			}
		}, bossThreadNums, workerThreadNums);
	}

	private PxServer(int port, ChannelInitializer initializer, int bossThreadNums, int workerThreadNums) {
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
			((ServerBootstrap)b.group(bossGroup, workerGroup).channel(io/netty/channel/socket/nio/NioServerSocketChannel)).childHandler(initializer);
			ChannelFuture f = b.bind(port);
			f.get();
			return;
		}
	}

	public void close() throws InterruptedException, ExecutionException {
		bossGroup.shutdownGracefully().get();
		workerGroup.shutdownGracefully().get();
	}

	public int getPort() {
		return port;
	}

}
