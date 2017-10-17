// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   IpUtils.java

package com.isnowfox.util;

import java.net.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class IpUtils {

	private static final Log log = LogFactory.getLog(com/isnowfox/util/IpUtils);

	public IpUtils() {
	}

	public static final int parseIp4(String addr) {
		byte b[];
		InetAddress ip = InetAddress.getByName(addr);
		b = ip.getAddress();
		if (b.length == 4) {
			return ((b[0] & 0xff) << 24) + ((b[1] & 0xff) << 16) + ((b[2] & 0xff) << 8) + (b[3] << 0);
		}
		return 0;
		UnknownHostException e;
		e;
		log.info((new StringBuilder()).append("´íÎóµÄipµØÖ·:").append(addr).toString(), e);
		return 0;
	}

	public static final String formatIp4(int i) {
		byte addr[] = new byte[4];
		addr[0] = (byte)(i >>> 24 & 0xff);
		addr[1] = (byte)(i >>> 16 & 0xff);
		addr[2] = (byte)(i >>> 8 & 0xff);
		addr[3] = (byte)(i & 0xff);
		return (new StringBuilder()).append(addr[0] & 0xff).append(".").append(addr[1] & 0xff).append(".").append(addr[2] & 0xff).append(".").append(addr[3] & 0xff).toString();
	}

	public static final int inet4AddressToInt(Inet4Address add) {
		byte b[] = add.getAddress();
		if (b.length == 4) {
			return ((b[0] & 0xff) << 24) + ((b[1] & 0xff) << 16) + ((b[2] & 0xff) << 8) + (b[3] << 0);
		} else {
			return 0;
		}
	}

	public static final int InetSocketAddressToInt(InetSocketAddress add) {
		byte b[] = add.getAddress().getAddress();
		if (b.length == 4) {
			return ((b[0] & 0xff) << 24) + ((b[1] & 0xff) << 16) + ((b[2] & 0xff) << 8) + (b[3] << 0);
		} else {
			return add.hashCode();
		}
	}

}
