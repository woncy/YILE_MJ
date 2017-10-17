// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MessageQueueHandler.java

package com.isnowfox.core.net;

import com.google.common.collect.Queues;
import com.isnowfox.core.net.message.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;

// Referenced classes of package com.isnowfox.core.net:
//			Session, NetMessageHandler

public class MessageQueueHandler
	implements NetMessageHandler {

	private static final int QUEUE_MAX = 10000;
	private ArrayBlockingQueue queue;

	public MessageQueueHandler() {
		queue = Queues.newArrayBlockingQueue(10000);
	}

	public Session createSession(ChannelHandlerContext ctx) {
		return new Session(ctx.channel());
	}

	public boolean onIn(Session session, ByteBuf in) throws Exception {
		return true;
	}

	public void onConnect(Session session1) {
	}

	public void onDisconnect(Session session1) {
	}

	public void onMessage(Message msg) throws InterruptedException {
		queue.put(msg);
	}

	public void onException(Session session1, Throwable throwable) {
	}

	public Message pollMessage() {
		return (Message)queue.poll();
	}

	public void drainMessage(Collection collect) {
		queue.drainTo(collect);
	}
}
