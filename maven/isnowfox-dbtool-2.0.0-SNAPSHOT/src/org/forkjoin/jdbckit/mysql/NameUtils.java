// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   NameUtils.java

package org.forkjoin.jdbckit.mysql;


public class NameUtils {

	public NameUtils() {
	}

	public static final String toClassName(String str) {
		StringBuilder sb = new StringBuilder();
		boolean isUp = false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (0 == i) {
				sb.append(Character.toUpperCase(c));
				continue;
			}
			if (c == '_') {
				isUp = true;
				continue;
			}
			if (isUp) {
				isUp = false;
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static final String toFieldName(String str) {
		StringBuilder sb = new StringBuilder();
		boolean isUp = false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (i == 0) {
				c = Character.toLowerCase(c);
			}
			if (c == '_') {
				isUp = true;
				continue;
			}
			if (isUp) {
				isUp = false;
				sb.append(Character.toUpperCase(c));
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}
}
