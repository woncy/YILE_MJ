// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttpResponse.java

package com.isnowfox.web.impl;

import com.isnowfox.web.Config;
import com.isnowfox.web.Response;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.*;
import org.apache.commons.io.output.StringBuilderWriter;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.http.*;

public final class HttpResponse
	implements Response {

	private static final String SET_COOKIE = "Set-Cookie";
	private static final ChannelBuffer EMPTY_BUFFER = ChannelBuffers.copiedBuffer(new byte[0]);
	private String contentType;
	private final Channel channel;
	private StringBuilderWriter writer;
	private byte data[];
	private boolean isNotFlush;
	private boolean close;
	private CookieEncoder cookieEncoder;
	private boolean cookie;
	private Charset charset;
	private DefaultHttpResponse response;
	private boolean isInitOut;

	public HttpResponse(Channel channel, ChannelHandlerContext chc, Config config) {
		contentType = "text/html; charset=UTF-8";
		isNotFlush = true;
		close = false;
		cookieEncoder = new CookieEncoder(true);
		cookie = false;
		response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		isInitOut = false;
		this.channel = channel;
		charset = config.getResponseCharset();
		contentType = (new StringBuilder()).append("text/html; charset=").append(charset.name()).toString();
	}

	public Writer getWriter() {
		if (null != writer) {
			return writer;
		}
		if (isInitOut) {
			throw new IllegalStateException("已经使用了其他输出方式!");
		} else {
			isInitOut = true;
			return writer = new StringBuilderWriter();
		}
	}

	public Appendable getAppendable() {
		if (null != writer) {
			return writer.getBuilder();
		}
		if (isInitOut) {
			throw new IllegalStateException("已经使用了其他输出方式!");
		} else {
			isInitOut = true;
			return (writer = new StringBuilderWriter()).getBuilder();
		}
	}

	public void sendError(int sc) {
		sendError(HttpResponseStatus.valueOf(sc));
	}

	public void sendError(int sc, String reasonPhrase) {
		sendError(new HttpResponseStatus(sc, reasonPhrase));
	}

	public void sendError(HttpResponseStatus status, String message) {
		sendError(status, message, null);
	}

	public void sendError(HttpResponseStatus status, String message, Throwable tw) {
		if (isNotFlush) {
			isNotFlush = false;
			int code = status.getCode();
			ChannelBuffer chBuf = ChannelBuffers.copiedBuffer(String.format("<html><head><title>%d %s</title></head><body bgcolor=\"white\"><center><h1>%d %s</h1></center></body></html>", new Object[] {
				Integer.valueOf(code), message, Integer.valueOf(code), status.getReasonPhrase()
			}), charset);
			response.setHeader("Content-Length", Integer.valueOf(chBuf.writerIndex()));
			if (cookie) {
				response.addHeader("Set-Cookie", cookieEncoder.encode());
			}
			response.setContent(chBuf);
			response.setStatus(status);
			channel.write(response);
		}
	}

	public void sendError(HttpResponseStatus status) {
		sendError(status, null, null);
	}

	public void sendRedirect(String location) {
		if (isNotFlush) {
			isNotFlush = false;
			HttpResponseStatus status = HttpResponseStatus.FOUND;
			ChannelBuffer chBuf = ChannelBuffers.EMPTY_BUFFER;
			response.setHeader("Content-Length", Integer.valueOf(0));
			response.setHeader("Location", location);
			if (cookie) {
				response.addHeader("Set-Cookie", cookieEncoder.encode());
			}
			response.setStatus(status);
			response.setContent(chBuf);
			channel.write(response);
		}
	}

	public void oneWrite(byte data[]) {
		if (isInitOut) {
			throw new IllegalStateException("已经使用了其他输出方式!");
		}
		if (null == this.data) {
			this.data = data;
			isInitOut = true;
		} else {
			throw new IllegalStateException("oneWrite 只能调用一次");
		}
	}

	private void flush() throws IOException {
		if (isNotFlush) {
			isNotFlush = false;
			ChannelBuffer chBuf = null;
			if (contentType != null) {
				response.setHeader("Content-Type", contentType);
			}
			if (null != writer) {
				chBuf = ChannelBuffers.copiedBuffer(writer.getBuilder(), charset);
				response.setHeader("Content-Length", Integer.valueOf(chBuf.writerIndex()));
			} else
			if (null != data) {
				chBuf = ChannelBuffers.copiedBuffer(data);
				response.setHeader("Content-Length", Integer.valueOf(data.length));
			} else {
				chBuf = EMPTY_BUFFER;
				response.setHeader("Content-Length", Integer.valueOf(0));
			}
			if (cookie) {
				response.addHeader("Set-Cookie", cookieEncoder.encode());
			}
			response.setContent(chBuf);
			channel.write(response);
		}
	}

	public void flushAndClose() throws IOException {
		close = true;
		flush();
		channel.close();
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public boolean isClose() {
		return close;
	}

	public void addHeader(String name, Object value) {
		response.addHeader(name, value);
	}

	public void setHeader(String name, Object value) {
		response.setHeader(name, value);
	}

	public void setHeader(String name, Iterable values) {
		response.setHeader(name, values);
	}

	public void removeHeader(String name) {
		response.removeHeader(name);
	}

	public void clearHeaders() {
		response.clearHeaders();
	}

	public String getHeader(String name) {
		List values = getHeaders(name);
		return values.size() <= 0 ? null : (String)values.get(0);
	}

	public List getHeaders(String name) {
		return response.getHeaders(name);
	}

	public List getHeaders() {
		return response.getHeaders();
	}

	public boolean containsHeader(String name) {
		return response.containsHeader(name);
	}

	public Set getHeaderNames() {
		return response.getHeaderNames();
	}

	public void add(Cookie cookie) {
		cookieEncoder.addCookie(cookie);
		this.cookie = true;
	}

	public void addCookie(String name, String value) {
		cookieEncoder.addCookie(name, value);
		cookie = true;
	}

	public Charset getCharset() {
		return charset;
	}

}
