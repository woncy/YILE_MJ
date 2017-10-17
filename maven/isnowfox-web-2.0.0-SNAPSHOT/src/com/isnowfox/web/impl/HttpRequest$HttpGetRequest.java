// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttpRequest.java

package com.isnowfox.web.impl;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

// Referenced classes of package com.isnowfox.web.impl:
//			HttpRequest

public static class HttpRequest$HttpGetRequest extends com.isnowfox.web.impl.HttpRequest {

	private Map params;

	public boolean isPost() {
		return false;
	}

	public Map getAllParams() {
		return params;
	}

	public final String getString(String key) {
		List list = (List)params.get(key);
		if (list != null && !list.isEmpty()) {
			return (String)list.get(0);
		} else {
			return null;
		}
	}

	public String getString(String key, String defaultValue) {
		List list = (List)params.get(key);
		if (list != null && !list.isEmpty()) {
			return (String)list.get(0);
		} else {
			return defaultValue;
		}
	}

	public List getStringList(String key) {
		return (List)params.get(key);
	}

	private HttpRequest$HttpGetRequest(HttpRequest request, Channel channel, Charset charset) throws IOException {
		uri = request.getUri();
		this.charset = charset;
		this.request = request;
		QueryStringDecoder decoder = new QueryStringDecoder(uri, charset);
		params = decoder.getParameters();
		path = decoder.getPath();
		this.channel = channel;
	}

}
