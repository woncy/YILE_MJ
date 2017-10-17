// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Cookie.java

package org.json;


// Referenced classes of package org.json:
//			JSONObject, JSONTokener, JSONException

public class Cookie {

	public Cookie() {
	}

	public static String escape(String string) {
		String s = string.trim();
		StringBuffer sb = new StringBuffer();
		int length = s.length();
		for (int i = 0; i < length; i++) {
			char c = s.charAt(i);
			if (c < ' ' || c == '+' || c == '%' || c == '=' || c == ';') {
				sb.append('%');
				sb.append(Character.forDigit((char)(c >>> 4 & 0xf), 16));
				sb.append(Character.forDigit((char)(c & 0xf), 16));
			} else {
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static JSONObject toJSONObject(String string) throws JSONException {
		JSONObject jo = new JSONObject();
		JSONTokener x = new JSONTokener(string);
		jo.put("name", x.nextTo('='));
		x.next('=');
		jo.put("value", x.nextTo(';'));
		x.next();
		String name;
		Object value;
		for (; x.more(); jo.put(name, value)) {
			name = unescape(x.nextTo("=;"));
			if (x.next() != '=') {
				if (name.equals("secure")) {
					value = Boolean.TRUE;
				} else {
					throw x.syntaxError("Missing '=' in cookie parameter.");
				}
			} else {
				value = unescape(x.nextTo(';'));
				x.next();
			}
		}

		return jo;
	}

	public static String toString(JSONObject jo) throws JSONException {
		StringBuffer sb = new StringBuffer();
		sb.append(escape(jo.getString("name")));
		sb.append("=");
		sb.append(escape(jo.getString("value")));
		if (jo.has("expires")) {
			sb.append(";expires=");
			sb.append(jo.getString("expires"));
		}
		if (jo.has("domain")) {
			sb.append(";domain=");
			sb.append(escape(jo.getString("domain")));
		}
		if (jo.has("path")) {
			sb.append(";path=");
			sb.append(escape(jo.getString("path")));
		}
		if (jo.optBoolean("secure")) {
			sb.append(";secure");
		}
		return sb.toString();
	}

	public static String unescape(String string) {
		int length = string.length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			char c = string.charAt(i);
			if (c == '+') {
				c = ' ';
			} else
			if (c == '%' && i + 2 < length) {
				int d = JSONTokener.dehexchar(string.charAt(i + 1));
				int e = JSONTokener.dehexchar(string.charAt(i + 2));
				if (d >= 0 && e >= 0) {
					c = (char)(d * 16 + e);
					i += 2;
				}
			}
			sb.append(c);
		}

		return sb.toString();
	}
}
