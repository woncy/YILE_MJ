// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   MimeUtils.java

package com.isnowfox.util;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public final class MimeUtils {

	private static Map map;
	private static Exception exception;

	public MimeUtils() {
	}

	public static String getMimeType(String extension) throws Exception {
		if (null == exception) {
			return (String)map.get(extension);
		} else {
			throw exception;
		}
	}

	static  {
		map = Maps.newHashMap();
		InputStream is = null;
		map.put("xml", "text/xml");
		map.put("html", "text/html");
		map.put("htm", "text/html");
		map.put("png", "image/png");
		if (null != is) {
			try {
				is.close();
			}
			catch (IOException e) {
				exception = e;
			}
		}
	}
}
