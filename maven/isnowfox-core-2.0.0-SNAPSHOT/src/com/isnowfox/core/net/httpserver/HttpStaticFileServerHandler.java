// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttpStaticFileServerHandler.java

package com.isnowfox.core.net.httpserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.ProgressiveFuture;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.MimetypesFileTypeMap;

public class HttpStaticFileServerHandler extends SimpleChannelInboundHandler {

	public static final String HTTP_DATE_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz";
	public static final String HTTP_DATE_GMT_TIMEZONE = "GMT";
	public static final int HTTP_CACHE_SECONDS = 60;
	private final File rootDir;
	private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");
	private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");

	public HttpStaticFileServerHandler(File rootDir) {
		this.rootDir = rootDir;
	}

	public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
		if (!request.decoderResult().isSuccess()) {
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}
		if (request.method() != HttpMethod.GET) {
			sendError(ctx, HttpResponseStatus.METHOD_NOT_ALLOWED);
			return;
		}
		String uri = request.uri();
		File file = sanitizeUri(uri);
		if (file == null) {
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}
		if (file.isHidden() || !file.exists()) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}
		if (file.isDirectory()) {
			if (uri.endsWith("/")) {
				sendListing(ctx, file);
			} else {
				sendRedirect(ctx, (new StringBuilder()).append(uri).append('/').toString());
			}
			return;
		}
		if (!file.isFile()) {
			sendError(ctx, HttpResponseStatus.FORBIDDEN);
			return;
		}
		String ifModifiedSince = request.headers().get(HttpHeaderNames.IF_MODIFIED_SINCE);
		if (ifModifiedSince != null && !ifModifiedSince.isEmpty()) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
			Date ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);
			long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000L;
			long fileLastModifiedSeconds = file.lastModified() / 1000L;
			if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
				sendNotModified(ctx);
				return;
			}
		}
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(file, "r");
		}
		catch (FileNotFoundException ignore) {
			sendError(ctx, HttpResponseStatus.NOT_FOUND);
			return;
		}
		long fileLength = raf.length();
		HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		HttpUtil.setContentLength(response, fileLength);
		setContentTypeHeader(response, file);
		setDateAndCacheHeaders(response, file);
		if (HttpUtil.isKeepAlive(request)) {
			response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
		}
		ctx.write(response);
		ChannelFuture sendFileFuture;
		ChannelFuture lastContentFuture;
		if (ctx.pipeline().get(io/netty/handler/ssl/SslHandler) == null) {
			sendFileFuture = ctx.write(new DefaultFileRegion(raf.getChannel(), 0L, fileLength), ctx.newProgressivePromise());
			lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		} else {
			sendFileFuture = ctx.writeAndFlush(new HttpChunkedInput(new ChunkedFile(raf, 0L, fileLength, 8192)), ctx.newProgressivePromise());
			lastContentFuture = sendFileFuture;
		}
		sendFileFuture.addListener(new ChannelProgressiveFutureListener() {

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

			 {
				this.this$0 = HttpStaticFileServerHandler.this;
				super();
			}
		});
		if (!HttpUtil.isKeepAlive(request)) {
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}

	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		if (ctx.channel().isActive()) {
			sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private File sanitizeUri(String uri) {
		try {
			uri = URLDecoder.decode(uri, "UTF-8");
		}
		catch (UnsupportedEncodingException e) {
			throw new Error(e);
		}
		if (uri.isEmpty() || uri.charAt(0) != '/') {
			return null;
		}
		uri = uri.replace('/', File.separatorChar);
		if (uri.contains("?")) {
			uri = uri.substring(0, uri.indexOf("?"));
		}
		if (uri.contains((new StringBuilder()).append(File.separator).append('.').toString()) || uri.contains((new StringBuilder()).append('.').append(File.separator).toString()) || uri.charAt(0) == '.' || uri.charAt(uri.length() - 1) == '.' || INSECURE_URI.matcher(uri).matches()) {
			return null;
		} else {
			return new File(rootDir, uri);
		}
	}

	private static void sendListing(ChannelHandlerContext ctx, File dir) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
		String dirPath = dir.getPath();
		StringBuilder buf = (new StringBuilder()).append("<!DOCTYPE html>\r\n").append("<html><head><title>").append("Listing of: ").append(dirPath).append("</title></head><body>\r\n").append("<h3>Listing of: ").append(dirPath).append("</h3>\r\n").append("<ul>").append("<li><a href=\"../\">..</a></li>\r\n");
		File afile[] = dir.listFiles();
		int i = afile.length;
		for (int j = 0; j < i; j++) {
			File f = afile[j];
			if (f.isHidden() || !f.canRead()) {
				continue;
			}
			String name = f.getName();
			if (ALLOWED_FILE_NAME.matcher(name).matches()) {
				buf.append("<li><a href=\"").append(name).append("\">").append(name).append("</a></li>\r\n");
			}
		}

		buf.append("</ul></body></html>\r\n");
		ByteBuf buffer = Unpooled.copiedBuffer(buf, CharsetUtil.UTF_8);
		response.content().writeBytes(buffer);
		buffer.release();
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private static void sendRedirect(ChannelHandlerContext ctx, String newUri) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FOUND);
		response.headers().set(HttpHeaderNames.LOCATION, newUri);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer((new StringBuilder()).append("Failure: ").append(status).append("\r\n").toString(), CharsetUtil.UTF_8));
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private static void sendNotModified(ChannelHandlerContext ctx) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_MODIFIED);
		setDateHeader(response);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private static void setDateHeader(FullHttpResponse response) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar time = new GregorianCalendar();
		response.headers().set(HttpHeaderNames.DATE, dateFormatter.format(time.getTime()));
	}

	private static void setDateAndCacheHeaders(HttpResponse response, File fileToCache) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
		dateFormatter.setTimeZone(TimeZone.getTimeZone("GMT"));
		Calendar time = new GregorianCalendar();
		response.headers().set(HttpHeaderNames.DATE, dateFormatter.format(time.getTime()));
		time.add(13, 60);
		response.headers().set(HttpHeaderNames.EXPIRES, dateFormatter.format(time.getTime()));
		response.headers().set(HttpHeaderNames.CACHE_CONTROL, "private, max-age=60");
		response.headers().set(HttpHeaderNames.LAST_MODIFIED, dateFormatter.format(new Date(fileToCache.lastModified())));
	}

	private static void setContentTypeHeader(HttpResponse response, File file) {
		MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		response.headers().set(HttpHeaderNames.CONTENT_TYPE, mimeTypesMap.getContentType(file.getPath()));
	}

	public volatile void channelRead0(ChannelHandlerContext channelhandlercontext, Object obj) throws Exception {
		channelRead0(channelhandlercontext, (FullHttpRequest)obj);
	}

}
