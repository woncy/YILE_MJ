// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CookieList.java

package org.json;

import java.util.Iterator;

// Referenced classes of package org.json:
//			JSONObject, JSONTokener, JSONException, Cookie

public class CookieList {

	public CookieList() {
	}

	public static JSONObject toJSONObject(String string) throws JSONException {
		JSONObject jo = new JSONObject();
		for (JSONTokener x = new JSONTokener(string); x.more(); x.next()) {
			String name = Cookie.unescape(x.nextTo('='));
			x.next('=');
			jo.put(name, Cookie.unescape(x.nextTo(';')));
		}

		return jo;
	}

	public static String toString(JSONObject jo) throws JSONException {
		boolean b = false;
		Iterator keys = jo.keys();
		StringBuffer sb = new StringBuffer();
		do {
			if (!keys.hasNext()) {
				break;
			}
			String string = keys.next().toString();
			if (!jo.isNull(string)) {
				if (b) {
					sb.append(';');
				}
				sb.append(Cookie.escape(string));
				sb.append("=");
				sb.append(Cookie.escape(jo.getString(string)));
				b = true;
			}
		} while (true);
		return sb.toString();
	}
}
