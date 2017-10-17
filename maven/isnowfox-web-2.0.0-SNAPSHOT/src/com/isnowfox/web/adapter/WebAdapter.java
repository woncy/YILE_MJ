// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   WebAdapter.java

package com.isnowfox.web.adapter;

import com.isnowfox.util.TimeSpan;
import com.isnowfox.web.Config;
import com.isnowfox.web.Context;
import com.isnowfox.web.Dispatcher;
import com.isnowfox.web.Request;
import com.isnowfox.web.Response;
import com.isnowfox.web.codec.Uri;
import com.isnowfox.web.impl.HttpResponse;
import java.io.ByteArrayOutputStream;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.WriteCompletionEvent;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebAdapter extends SimpleChannelUpstreamHandler {

	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/web/adapter/WebAdapter);
	private Dispatcher dispatcher;
	private boolean readingChunks;
	private Config config;
	private ByteArrayOutputStream baos;

	public WebAdapter(Config config, Dispatcher dispatcher) {
		baos = new ByteArrayOutputStream();
		this.dispatcher = dispatcher;
		this.config = config;
	}

	public void messageReceived(ChannelHandlerContext chc, MessageEvent evt) throws Exception {
		if (!readingChunks) {
			HttpRequest request = (HttpRequest)evt.getMessage();
			if (HttpHeaders.is100ContinueExpected(request)) {
				send100Continue(evt);
			}
			if (request.isChunked()) {
				readingChunks = true;
			} else {
				ChannelBuffer content = request.getContent();
				if (content.readable()) {
					baos.write(content.array());
				}
				service(chc, evt);
			}
		} else {
			HttpChunk chunk = (HttpChunk)evt.getMessage();
			if (chunk.isLast()) {
				service(chc, evt);
			} else {
				ChannelBuffer content = chunk.getContent();
				baos.write(content.array());
			}
		}
	}

	private final void service(ChannelHandlerContext chc, MessageEvent evt) throws Exception {
		TimeSpan ts;
		Uri uri;
		Response resp;
		Request req;
		byte postData[] = baos.toByteArray();
		baos.reset();
		HttpRequest request = (HttpRequest)evt.getMessage();
		ts = new TimeSpan();
		uri = new Uri(request.getUri(), config.getRequestCharset(), config.getParamsMax(), config.getIndexPage());
		if (log.isDebugEnabled()) {
			log.debug("处理请求[method:{},uri:{}]", request.getMethod(), uri);
		}
		resp = new HttpResponse(evt.getChannel(), chc, config);
		req = com.isnowfox.web.impl.HttpRequest.create(request, postData, evt.getChannel(), config.getRequestCharset());
		Context.getInstance().setRequest(req);
		dispatcher.service(uri, req, resp);
		if (log.isDebugEnabled()) {
			log.debug("请求处理结束,耗时[{}]", ts.end());
		}
		Context.getInstance().clearRequest();
		break MISSING_BLOCK_LABEL_212;
		Exception exception;
		exception;
		Context.getInstance().clearRequest();
		throw exception;
	}

	private void send100Continue(MessageEvent me) {
		org.jboss.netty.handler.codec.http.HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
		me.getChannel().write(response);
	}

	public void exceptionCaught(ChannelHandlerContext chc, ExceptionEvent e) throws Exception {
		log.error("Channel错误", e.getCause());
		e.getChannel().close();
	}

	public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) throws Exception {
		super.writeComplete(ctx, e);
		Object o = ctx.getAttachment();
		if (o instanceof HttpResponse) {
			HttpResponse resp = (HttpResponse)o;
			if (resp.isClose()) {
				log.debug("writeComplete close");
			}
		}
		log.info("writeComplete");
	}

	public void channelDisconnected(ChannelHandlerContext chc, ChannelStateEvent e) throws Exception {
		super.channelDisconnected(chc, e);
		log.info("channelDisconnected");
	}

	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelConnected(ctx, e);
		log.info("channelConnected");
	}

}
