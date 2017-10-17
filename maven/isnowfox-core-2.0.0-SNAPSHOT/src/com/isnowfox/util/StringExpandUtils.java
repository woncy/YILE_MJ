// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   StringExpandUtils.java

package com.isnowfox.util;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;

public final class StringExpandUtils extends StringUtils {

	public StringExpandUtils() {
	}

	public static String unicodeEncode(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			sb.append(CharUtils.unicodeEscaped(str.charAt(i)));
		}

		return sb.toString();
	}

	public static String escapeHtml(String str) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if ('<' == c) {
				sb.append("&lt;");
				continue;
			}
			if ('>' == c) {
				sb.append("&gt;");
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static boolean checkLengthRange(String str, int min, int max) {
		int l = str != null ? str.length() : 0;
		return l >= min && l <= max;
	}
}
