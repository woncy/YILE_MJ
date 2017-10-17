// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttpRequest.java

package com.isnowfox.web.impl;

import com.google.common.collect.Maps;
import com.isnowfox.web.Request;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.math.NumberUtils;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.Cookie;
import org.jboss.netty.handler.codec.http.CookieDecoder;
import org.jboss.netty.handler.codec.http.HttpMethod;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

public abstract class HttpRequest extends Request {
	public static class HttpPostRequest extends HttpRequest {

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

		private static final boolean isMultipartContent(org.jboss.netty.handler.codec.http.HttpRequest request) {
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

		private HttpPostRequest(org.jboss.netty.handler.codec.http.HttpRequest request, byte post[], Channel channel, Charset charset) throws IOException {
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

	public static class HttpGetRequest extends HttpRequest {

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

		private HttpGetRequest(org.jboss.netty.handler.codec.http.HttpRequest request, Channel channel, Charset charset) throws IOException {
			uri = request.getUri();
			this.charset = charset;
			this.request = request;
			QueryStringDecoder decoder = new QueryStringDecoder(uri, charset);
			params = decoder.getParameters();
			path = decoder.getPath();
			this.channel = channel;
		}

	}


	private static final String HEADER_NAME_COOKIE = "Cookie";
	protected org.jboss.netty.handler.codec.http.HttpRequest request;
	protected String uri;
	protected Charset charset;
	protected String path;
	protected Channel channel;
	private Map cookieMap;
	private boolean initCookie;

	public HttpRequest() {
		initCookie = false;
	}

	public static Request create(org.jboss.netty.handler.codec.http.HttpRequest request, byte post[], Channel channel, Charset charsert) throws IOException {
		HttpMethod method = request.getMethod();
		HttpRequest re;
		if (method == HttpMethod.GET) {
			re = new HttpGetRequest(request, channel, charsert);
		} else
		if (method.equals(HttpMethod.POST)) {
			re = new HttpPostRequest(request, post, channel, charsert);
		} else {
			throw new IOException("No Support");
		}
		return re;
	}

	private Cookie getNettyCookie(String key) {
		CookieDecoder cd = new CookieDecoder();
		if (cookieMap == null) {
			if (initCookie) {
				return null;
			}
			initCookie = true;
			String cookieString = request.getHeader("Cookie");
			if (cookieString != null) {
				Set set = cd.decode(cookieString);
				cookieMap = Maps.newHashMapWithExpectedSize(set.size());
				Cookie c;
				for (Iterator iterator = set.iterator(); iterator.hasNext(); cookieMap.put(c.getName(), c)) {
					c = (Cookie)iterator.next();
				}

				return (Cookie)cookieMap.get(key);
			} else {
				return null;
			}
		} else {
			return (Cookie)cookieMap.get(key);
		}
	}

	public String getCookieString(String key) {
		Cookie c = getNettyCookie(key);
		if (c != null) {
			return c.getValue();
		} else {
			return null;
		}
	}

	public String getUri() {
		return uri;
	}

	public String getHeader(String name) {
		return request.getHeader(name);
	}

	public List getHeaders(String name) {
		return request.getHeaders(name);
	}

	public List getHeaders() {
		return request.getHeaders();
	}

	public boolean containsHeader(String name) {
		return request.containsHeader(name);
	}

	public Set getHeaderNames() {
		return request.getHeaderNames();
	}

	public byte[] getBytes(String key) {
		throw new UnsupportedOperationException("No Support");
	}

	public boolean getBoolean(String key) {
		return Boolean.valueOf(getString(key)).booleanValue();
	}

	public String getStringByCharset(String key, String charset) {
		Charset inCharset = Charset.forName(charset);
		String value = getString(key);
		if (!this.charset.contains(inCharset)) {
			value = new String(value.getBytes(this.charset), inCharset);
		}
		return value;
	}

	public int getInt(String key) {
		return NumberUtils.toInt(getString(key));
	}

	public int getInt(String key, int defaultValue) {
		return NumberUtils.toInt(getString(key), defaultValue);
	}

	public float getFloat(String key, float defaultValue) {
		return NumberUtils.toFloat(getString(key), defaultValue);
	}

	public int getInt(String key, int defaultValue, int max) {
		return Math.min(getInt(key, defaultValue), max);
	}

	public int getIntMin(String key, int defaultValue, int min) {
		return Math.max(getInt(key, defaultValue), min);
	}

	public int getInt(String key, int defaultValue, int min, int max) {
		int i = NumberUtils.toInt(getString(key), defaultValue);
		i = i >= min ? i : min;
		return i <= max ? i : max;
	}

	public Charset getCharset() {
		return charset;
	}

	public String getRemoteInfo() {
		Object o = channel.getRemoteAddress();
		return o != null ? o.toString() : null;
	}

	public int getLocalPort() {
		SocketAddress localAddress = channel.getLocalAddress();
		if (localAddress instanceof InetSocketAddress) {
			return ((InetSocketAddress)localAddress).getPort();
		} else {
			return -1;
		}
	}

	public String getPath() {
		return path;
	}
}
