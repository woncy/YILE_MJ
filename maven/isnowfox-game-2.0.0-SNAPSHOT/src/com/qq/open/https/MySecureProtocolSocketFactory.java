// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MySecureProtocolSocketFactory.java

package com.qq.open.https;

import java.io.IOException;
import java.net.*;
import javax.net.ssl.*;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClientError;
import org.apache.commons.httpclient.params.HttpConnectionParams;
import org.apache.commons.httpclient.protocol.ControllerThreadSocketFactory;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;

// Referenced classes of package com.qq.open.https:
//			MyX509TrustManager

public class MySecureProtocolSocketFactory
	implements ProtocolSocketFactory {

	private SSLContext sslContext;

	public MySecureProtocolSocketFactory() {
		sslContext = null;
	}

	private static SSLContext createEasySSLContext() {
		SSLContext context;
		context = SSLContext.getInstance("SSL");
		context.init(null, new TrustManager[] {
			new MyX509TrustManager()
		}, null);
		return context;
		Exception e;
		e;
		throw new HttpClientError(e.toString());
	}

	private SSLContext getSSLContext() {
		if (sslContext == null) {
			sslContext = createEasySSLContext();
		}
		return sslContext;
	}

	public Socket createSocket(String host, int port, InetAddress clientHost, int clientPort) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, port, clientHost, clientPort);
	}

	public Socket createSocket(String host, int port, InetAddress localAddress, int localPort, HttpConnectionParams params) throws IOException, UnknownHostException, ConnectTimeoutException {
		if (params == null) {
			throw new IllegalArgumentException("Parameters may not be null");
		}
		int timeout = params.getConnectionTimeout();
		if (timeout == 0) {
			return createSocket(host, port, localAddress, localPort);
		} else {
			return ControllerThreadSocketFactory.createSocket(this, host, port, localAddress, localPort, timeout);
		}
	}

	public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(host, port);
	}

	public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
		return getSSLContext().getSocketFactory().createSocket(socket, host, port, autoClose);
	}
}
