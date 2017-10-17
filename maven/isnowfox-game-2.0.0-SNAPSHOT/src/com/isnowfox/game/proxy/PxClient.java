// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PxClient.java

package com.isnowfox.game.proxy;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package com.isnowfox.game.proxy:
//			PxMsgFactory, PxMsgHandler, PxMsgDecoder, PxMsgEncoder, 
//			PxMsgChannelHandler

public class PxClient {

	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/game/proxy/PxClient);
	private static final int DEFAULT_THREAD_NUMS = 8;
	private static final int RETRY_WAIT = 1000;
	private final String host;
	private final int port;
	private final int threadNums;
	private Channel channel;
	private final ChannelInitializer initializer;
	private EventLoopGroup group;

	public static final PxClient create(String host, int port, PxMsgHandler messageHandler) {
		return create(host, port, messageHandler, 8);
	}

	public static final PxClient create(String host, int port, PxMsgHandler messageHandler, int threadNums) {
		return create(new PxMsgFactory(), host, port, messageHandler, threadNums);
	}

	public static final PxClient create(PxMsgFactory pxMsgFactory, String host, int port, PxMsgHandler messageHandler, int threadNums) {
		return new PxClient(new ChannelInitializer(pxMsgFactory, messageHandler) {

			final PxMsgFactory val$pxMsgFactory;
			final PxMsgHandler val$messageHandler;

			protected void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline p = ch.pipeline();
				p.addLast("messageDecoder", new PxMsgDecoder(pxMsgFactory));
				p.addLast("messageEncoder", new PxMsgEncoder());
				p.addLast("handler", new PxMsgChannelHandler(messageHandler));
			}

			protected volatile void initChannel(Channel channel1) throws Exception {
				initChannel((SocketChannel)channel1);
			}

			 {
				pxMsgFactory = pxmsgfactory;
				messageHandler = pxmsghandler;
				super();
			}
		}, host, port, threadNums);
	}

	private PxClient(ChannelInitializer initializer, String host, int port, int threadNums) {
		this.host = host;
		this.port = port;
		this.threadNums = threadNums;
		this.initializer = initializer;
	}

	public void connectRetry() throws Exception {
		connect(true);
	}

	public void connectNoRetry() throws Exception {
		connect(false);
	}

	public void connect(boolean retry) throws Exception {
		if (group != null) {
			throw new ConnectException("不能重复连接！");
		}
		group = new NioEventLoopGroup(threadNums);
		Bootstrap b = new Bootstrap();
		((Bootstrap)((Bootstrap)b.group(group)).channel(io/netty/channel/socket/nio/NioSocketChannel)).handler(initializer);
		if (retry) {
			do {
				ChannelFuture f = b.connect(host, port);
				channel = f.channel();
				f.await();
				Throwable throwable = f.cause();
				if (throwable == null) {
					return;
				}
				log.error("连接失败！等待1000开始重试！{}", throwable.getMessage());
				Thread.sleep(1000L);
			} while (true);
		} else {
			ChannelFuture f = b.connect(host, port);
			channel = f.channel();
			f.get();
			return;
		}
	}

	public InetSocketAddress getLocalAddress() {
		return (InetSocketAddress)channel.localAddress();
	}

	public void write(Object msg) {
		log.debug("发送消息!{}", msg);
		channel.write(msg);
	}

	public void writeAndFlush(Object msg) {
		channel.writeAndFlush(msg);
	}

	public Channel getChannel() {
		return channel;
	}

	public void close() throws InterruptedException, ExecutionException {
		group.shutdownGracefully().get();
		group = null;
	}

}
