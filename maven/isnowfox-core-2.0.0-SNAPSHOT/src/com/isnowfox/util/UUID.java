// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   UUID.java

package com.isnowfox.util;

import java.net.InetAddress;

public final class UUID {

	private static final char sep = 45;
	private static final int ip;
	private static short counter = 0;
	private static final int JVM = (int)(System.currentTimeMillis() >>> 8);

	public UUID() {
	}

	protected static int getJVM() {
		return JVM;
	}

	protected static short getCount() {
		/*<invalid signature>*/java.lang.Object local = com/isnowfox/util/UUID;
		JVM INSTR monitorenter ;
		if (counter < 0) {
			counter = 0;
		}
		return counter++;
		Exception exception;
		exception;
		throw exception;
	}

	protected static int getIP() {
		return ip;
	}

	protected static short getHiTime() {
		return (short)(int)(System.currentTimeMillis() >>> 32);
	}

	protected static int getLoTime() {
		return (int)System.currentTimeMillis();
	}

	public static String generate() {
		return (new StringBuilder(36)).append(format(getIP())).append('-').append(format(getJVM())).append('-').append(format(getHiTime())).append('-').append(format(getLoTime())).append('-').append(format(getCount())).toString();
	}

	public static String generateNoSep() {
		return (new StringBuilder(32)).append(format(getIP())).append(format(getJVM())).append(format(getHiTime())).append(format(getLoTime())).append(format(getCount())).toString();
	}

	protected static String format(int intValue) {
		String formatted = Integer.toHexString(intValue);
		StringBuilder buf = new StringBuilder("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected static String format(short shortValue) {
		String formatted = Integer.toHexString(shortValue);
		StringBuilder buf = new StringBuilder("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	public static int toInt(byte bytes[]) {
		int result = 0;
		for (int i = 0; i < 4; i++) {
			result = ((result << 8) - -128) + bytes[i];
		}

		return result;
	}

	static  {
		int ipadd;
		try {
			ipadd = toInt(InetAddress.getLocalHost().getAddress());
		}
		catch (Exception e) {
			ipadd = 0;
		}
		ip = ipadd;
	}
}
