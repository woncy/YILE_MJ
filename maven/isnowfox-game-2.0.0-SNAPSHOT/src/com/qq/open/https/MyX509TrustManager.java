// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MyX509TrustManager.java

package com.qq.open.https;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public class MyX509TrustManager
	implements X509TrustManager {

	public MyX509TrustManager() {
	}

	public void checkClientTrusted(X509Certificate ax509certificate[], String s) throws CertificateException {
	}

	public void checkServerTrusted(X509Certificate ax509certificate[], String s) throws CertificateException {
	}

	public X509Certificate[] getAcceptedIssuers() {
		return null;
	}
}
