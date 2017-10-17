// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SnsNetwork.java

package com.qq.open;

import com.qq.open.https.MySecureProtocolSocketFactory;
import java.io.IOException;
import java.util.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.MultipartPostMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;

// Referenced classes of package com.qq.open:
//			OpensnsException, ErrorCode

public class SnsNetwork {

	private static final String CONTENT_CHARSET = "UTF-8";
	private static final int CONNECTION_TIMEOUT = 3000;
	private static final int READ_DATA_TIMEOUT = 3000;

	public SnsNetwork() {
	}

	public static String postRequest(String url, HashMap params, HashMap cookies, String protocol) throws OpensnsException {
		HttpClient httpClient;
		PostMethod postMethod;
		if (protocol.equalsIgnoreCase("https")) {
			Protocol httpsProtocol = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", httpsProtocol);
		}
		httpClient = new HttpClient();
		postMethod = new PostMethod(url);
		if (params != null && !params.isEmpty()) {
			NameValuePair data[] = new NameValuePair[params.size()];
			Iterator iter = params.entrySet().iterator();
			for (int i = 0; iter.hasNext(); i++) {
				java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
				data[i] = new NameValuePair((String)entry.getKey(), (String)entry.getValue());
			}

			postMethod.setRequestBody(data);
		}
		if (cookies != null && !cookies.isEmpty()) {
			Iterator iter = cookies.entrySet().iterator();
			StringBuilder buffer = new StringBuilder(128);
			java.util.Map.Entry entry;
			for (; iter.hasNext(); buffer.append((String)entry.getKey()).append("=").append((String)entry.getValue()).append("; ")) {
				entry = (java.util.Map.Entry)iter.next();
			}

			postMethod.getParams().setCookiePolicy("ignoreCookies");
			postMethod.setRequestHeader("Cookie", buffer.toString());
		}
		postMethod.setRequestHeader("User-Agent", "Java OpenApiV3 SDK Client");
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(3000);
		postMethod.getParams().setParameter("http.protocol.content-charset", "UTF-8");
		postMethod.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler());
		String s;
		int statusCode = httpClient.executeMethod(postMethod);
		if (statusCode != 200) {
			throw new OpensnsException(1900, (new StringBuilder()).append("Request [").append(url).append("] failed:").append(postMethod.getStatusLine()).toString());
		}
		byte responseBody[] = postMethod.getResponseBody();
		s = new String(responseBody, "UTF-8");
		postMethod.releaseConnection();
		return s;
		Exception exception;
		exception;
		postMethod.releaseConnection();
		throw exception;
		HttpException e;
		e;
		throw new OpensnsException(1900, e);
		e;
		throw new OpensnsException(1900, e);
	}

	public static String postRequestWithFile(String url, HashMap params, HashMap cookies, FilePart fp, String protocol) throws OpensnsException {
		HttpClient httpClient;
		MultipartPostMethod postMethod;
		if (protocol.equalsIgnoreCase("https")) {
			Protocol httpsProtocol = new Protocol("https", new MySecureProtocolSocketFactory(), 443);
			Protocol.registerProtocol("https", httpsProtocol);
		}
		httpClient = new HttpClient();
		postMethod = new MultipartPostMethod(url);
		postMethod.addPart(fp);
		if (params != null && !params.isEmpty()) {
			java.util.Map.Entry entry;
			for (Iterator iter = params.entrySet().iterator(); iter.hasNext(); postMethod.addPart(new StringPart((String)entry.getKey(), (String)entry.getValue(), "UTF-8"))) {
				entry = (java.util.Map.Entry)iter.next();
			}

		}
		if (cookies != null && !cookies.isEmpty()) {
			Iterator iter = cookies.entrySet().iterator();
			StringBuilder buffer = new StringBuilder(128);
			java.util.Map.Entry entry;
			for (; iter.hasNext(); buffer.append((String)entry.getKey()).append("=").append((String)entry.getValue()).append("; ")) {
				entry = (java.util.Map.Entry)iter.next();
			}

			postMethod.getParams().setCookiePolicy("ignoreCookies");
			postMethod.setRequestHeader("Cookie", buffer.toString());
		}
		postMethod.setRequestHeader("User-Agent", "Java OpenApiV3 SDK Client");
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(3000);
		postMethod.getParams().setParameter("http.method.retry-handler", new DefaultHttpMethodRetryHandler());
		String s;
		postMethod.getParams().setContentCharset("UTF-8");
		int statusCode = httpClient.executeMethod(postMethod);
		if (statusCode != 200) {
			throw new OpensnsException(1900, (new StringBuilder()).append("Request [").append(url).append("] failed:").append(postMethod.getStatusLine()).toString());
		}
		byte responseBody[] = postMethod.getResponseBody();
		s = new String(responseBody, "UTF-8");
		postMethod.releaseConnection();
		return s;
		Exception exception;
		exception;
		postMethod.releaseConnection();
		throw exception;
		HttpException e;
		e;
		throw new OpensnsException(1900, (new StringBuilder()).append("Request [").append(url).append("] failed:").append(e.getMessage()).toString());
		e;
		throw new OpensnsException(1900, (new StringBuilder()).append("Request [").append(url).append("] failed:").append(e.getMessage()).toString());
	}
}
