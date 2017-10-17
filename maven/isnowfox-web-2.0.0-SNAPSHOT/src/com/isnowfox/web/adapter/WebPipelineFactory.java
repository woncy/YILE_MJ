// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   WebPipelineFactory.java

package com.isnowfox.web.adapter;

import com.isnowfox.web.Config;
import com.isnowfox.web.Server;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;

// Referenced classes of package com.isnowfox.web.adapter:
//			WebAdapter

public class WebPipelineFactory
	implements ChannelPipelineFactory {

	private Server server;
	private Config config;

	public WebPipelineFactory(Server server, Config config) {
		this.server = server;
		this.config = config;
	}

	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("encoder", new HttpResponseEncoder());
		pipeline.addLast("handler", new WebAdapter(config, server.getDispatcher()));
		return pipeline;
	}
}
