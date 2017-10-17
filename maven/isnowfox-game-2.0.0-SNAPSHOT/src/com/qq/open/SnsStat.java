// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SnsStat.java

package com.qq.open;

import java.net.*;
import java.util.HashMap;

public class SnsStat {

	private static final String STAT_SVR_NAME = "apistat.tencentyun.com";
	private static final int STAT_SVR_PORT = 19888;

	public SnsStat() {
	}

	public static void statReport(long startTime, String serverName, HashMap params, String method, String protocol, int rc, String scriptName) {
		try {
			long endTime = System.currentTimeMillis();
			double timeCost = (double)(endTime - startTime) / 1000D;
			String sendStr = String.format("{\"appid\":%s, \"pf\":\"%s\",\"rc\":%d,\"svr_name\":\"%s\", \"interface\":\"%s\",\"protocol\":\"%s\",\"method\":\"%s\",\"time\":%.4f,\"timestamp\":%d,\"collect_point\":\"sdk-java-v3\"}", new Object[] {
				params.get("appid"), params.get("pf"), Integer.valueOf(rc), InetAddress.getByName(serverName).getHostAddress(), scriptName, protocol, method, Double.valueOf(timeCost), Long.valueOf(endTime / 1000L)
			});
			DatagramSocket client = new DatagramSocket();
			byte sendBuf[] = sendStr.getBytes();
			String reportSvrIp = "apistat.tencentyun.com";
			int reportSvrport = 19888;
			InetAddress addr = InetAddress.getByName(reportSvrIp);
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, addr, reportSvrport);
			client.send(sendPacket);
		}
		catch (Exception exception) { }
	}
}
