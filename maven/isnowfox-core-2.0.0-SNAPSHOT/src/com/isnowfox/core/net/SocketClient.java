// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SocketClient.java

package com.isnowfox.core.net;

import com.isnowfox.core.net.message.MessageFactory;
import com.isnowfox.core.net.message.coder.MessageDecoder;
import com.isnowfox.core.net.message.coder.MessageEncoder;
import com.isnowfox.core.net.message.coder.PacketDecoder;
import com.isnowfox.core.net.message.coder.PacketEncoder;
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

// Referenced classes of package com.isnowfox.core.net:
//			NetMessageHandler, NetPacketHandler, PacketChannelHandler, MessageChannelHandler

public final class SocketClient {

	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/core/net/SocketClient);
	private static final int DEFAULT_THREAD_NUMS = 8;
	private static final int RETRY_WAIT = 1000;
	private final String host;
	private final int port;
	private final int threandNums;
	private Channel channel;
	private final ChannelInitializer initializer;
	private EventLoopGroup group;

	public static final SocketClient createMessageClient(MessageFactory messageFactory, String host, int port, NetMessageHandler messageHandler) {
		return createMessageClient(messageFactory, host, port, messageHandler, 8);
	}

	public static final SocketClient createMessageClient(MessageFactory messageFactory, String host, int port, NetMessageHandler messageHandler, int threandNums) {
		return new SocketClient(new ChannelInitializer(messageFactory, messageHandler) {

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
		}, host, port, threandNums);
	}

	public static final SocketClient createPacketClient(String host, int port, NetPacketHandler handler) {
		return createPacketClient(host, port, handler, 8);
	}

	public static final SocketClient createPacketClient(String host, int port, NetPacketHandler handler, int threandNums) {
		return new SocketClient(new ChannelInitializer(handler) {

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
		}, host, port, threandNums);
	}

	private SocketClient(ChannelInitializer initializer, String host, int port, int threandNums) {
		this.host = host;
		this.port = port;
		this.threandNums = threandNums;
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
		group = new NioEventLoopGroup(threandNums);
		Bootstrap b = new Bootstrap();
		((Bootstrap)((Bootstrap)b.group(group)).channel(io/netty/channel/socket/nio/NioSocketChannel)).handler(initializer);
		if (retry) {
			do {
				ChannelFuture f = b.connect(host, port);
				f.await();
				Throwable throwable = f.cause();
				if (throwable == null) {
					channel = f.channel();
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
		InetSocketAddress inetAddress = (InetSocketAddress)channel.localAddress();
		return inetAddress;
	}

	public void write(Object msg) {
		log.debug("发送消息!{}", msg);
		channel.write(msg);
	}

	public void writeAndFlush(Object msg) {
		log.debug("发送消息!{}", msg);
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
