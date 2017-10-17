// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HTTP.java

package org.json;

import java.util.Iterator;

// Referenced classes of package org.json:
//			JSONObject, HTTPTokener, JSONException

public class HTTP {

	public static final String CRLF = "\r\n";

	public HTTP() {
	}

	public static JSONObject toJSONObject(String string) throws JSONException {
		JSONObject jo = new JSONObject();
		HTTPTokener x = new HTTPTokener(string);
		String token = x.nextToken();
		if (token.toUpperCase().startsWith("HTTP")) {
			jo.put("HTTP-Version", token);
			jo.put("Status-Code", x.nextToken());
			jo.put("Reason-Phrase", x.nextTo('\0'));
			x.next();
		} else {
			jo.put("Method", token);
			jo.put("Request-URI", x.nextToken());
			jo.put("HTTP-Version", x.nextToken());
		}
		for (; x.more(); x.next()) {
			String name = x.nextTo(':');
			x.next(':');
			jo.put(name, x.nextTo('\0'));
		}

		return jo;
	}

	public static String toString(JSONObject jo) throws JSONException {
		Iterator keys = jo.keys();
		StringBuffer sb = new StringBuffer();
		if (jo.has("Status-Code") && jo.has("Reason-Phrase")) {
			sb.append(jo.getString("HTTP-Version"));
			sb.append(' ');
			sb.append(jo.getString("Status-Code"));
			sb.append(' ');
			sb.append(jo.getString("Reason-Phrase"));
		} else
		if (jo.has("Method") && jo.has("Request-URI")) {
			sb.append(jo.getString("Method"));
			sb.append(' ');
			sb.append('"');
			sb.append(jo.getString("Request-URI"));
			sb.append('"');
			sb.append(' ');
			sb.append(jo.getString("HTTP-Version"));
		} else {
			throw new JSONException("Not enough material for an HTTP header.");
		}
		sb.append("\r\n");
		do {
			if (!keys.hasNext()) {
				break;
			}
			String string = keys.next().toString();
			if (!"HTTP-Version".equals(string) && !"Status-Code".equals(string) && !"Reason-Phrase".equals(string) && !"Method".equals(string) && !"Request-URI".equals(string) && !jo.isNull(string)) {
				sb.append(string);
				sb.append(": ");
				sb.append(jo.getString(string));
				sb.append("\r\n");
			}
		} while (true);
		sb.append("\r\n");
		return sb.toString();
	}
}
