// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttpStaticFileServerHandler.java

package com.isnowfox.core.net.httpserver;

import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ProgressiveFuture;
import java.io.PrintStream;

// Referenced classes of package com.isnowfox.core.net.httpserver:
//			HttpStaticFileServerHandler

class HttpStaticFileServerHandler$1
	implements ChannelProgressiveFutureListener {

	final HttpStaticFileServerHandler this$0;

	public void operationProgressed(ChannelProgressiveFuture future, long progress, long total) {
		if (total < 0L) {
			System.err.println((new StringBuilder()).append(future.channel()).append(" Transfer progress: ").append(progress).toString());
		} else {
			System.err.println((new StringBuilder()).append(future.channel()).append(" Transfer progress: ").append(progress).append(" / ").append(total).toString());
		}
	}

	public void operationComplete(ChannelProgressiveFuture future) {
		System.err.println((new StringBuilder()).append(future.channel()).append(" Transfer complete.").toString());
	}

	public volatile void operationProgressed(ProgressiveFuture progressivefuture, long l, long l1) throws Exception {
		operationProgressed((ChannelProgressiveFuture)progressivefuture, l, l1);
	}

	public volatile void operationComplete(Future future) throws Exception {
		operationComplete((ChannelProgressiveFuture)future);
	}

	HttpStaticFileServerHandler$1() {
		this.this$0 = HttpStaticFileServerHandler.this;
		super();
	}
}
