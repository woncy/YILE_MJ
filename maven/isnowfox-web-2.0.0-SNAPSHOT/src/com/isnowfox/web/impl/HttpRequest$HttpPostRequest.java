// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttpRequest.java

package com.isnowfox.web.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.*;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

// Referenced classes of package com.isnowfox.web.impl:
//			HttpRequest

public static class HttpRequest$HttpPostRequest extends com.isnowfox.web.impl.HttpRequest {

	private Map params;

	public Map getAllParams() {
		return params;
	}

	private Map decodeParams(Map params, String s) {
		String name = null;
		int pos = 0;
		char c = '\0';
		int i;
		for (i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			if (c == '=' && name == null) {
				if (pos != i) {
					name = decodeComponent(s.substring(pos, i), charset);
				}
				pos = i + 1;
				continue;
			}
			if (c != '&') {
				continue;
			}
			if (name == null && pos != i) {
				addParam(params, decodeComponent(s.substring(pos, i), charset), "");
			} else
			if (name != null) {
				addParam(params, name, decodeComponent(s.substring(pos, i), charset));
				name = null;
			}
			pos = i + 1;
		}

		if (pos != i) {
			if (name == null) {
				addParam(params, decodeComponent(s.substring(pos, i), charset), "");
			} else {
				addParam(params, name, decodeComponent(s.substring(pos, i), charset));
			}
		} else
		if (name != null) {
			addParam(params, name, "");
		}
		return params;
	}

	private static String decodeComponent(String s, Charset charset) {
		if (s == null) {
			return "";
		}
		return URLDecoder.decode(s, charset.name());
		UnsupportedEncodingException e;
		e;
		throw new UnsupportedCharsetException(charset.name());
	}

	private static void addParam(Map params, String name, String value) {
		List values = (List)params.get(name);
		if (values == null) {
			values = new ArrayList(1);
			params.put(name, values);
		}
		values.add(value);
	}

	private static final boolean isMultipartContent(HttpRequest request) {
		String contentType = request.getHeader("Content-Type");
		if (contentType == null) {
			return false;
		}
		return contentType.toLowerCase().startsWith("multipart/");
	}

	public boolean isPost() {
		return true;
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

	private HttpRequest$HttpPostRequest(HttpRequest request, byte post[], Channel channel, Charset charset) throws IOException {
		uri = request.getUri();
		this.charset = charset;
		this.request = request;
		QueryStringDecoder decoder = new QueryStringDecoder(uri, charset);
		params = decoder.getParameters();
		if (!isMultipartContent(request) && post != null) {
			String str = new String(post, charset);
			if (params.isEmpty()) {
				params = new LinkedHashMap();
			}
			decodeParams(params, str);
		}
		path = decoder.getPath();
		this.channel = channel;
	}

}
