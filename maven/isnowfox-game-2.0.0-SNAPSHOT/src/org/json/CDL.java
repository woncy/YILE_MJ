// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CDL.java

package org.json;


// Referenced classes of package org.json:
//			JSONArray, JSONTokener, JSONException, JSONObject

public class CDL {

	public CDL() {
	}

	private static String getValue(JSONTokener x) throws JSONException {
		char c;
		do {
			c = x.next();
		} while (c == ' ' || c == '\t');
		switch (c) {
		case 0: // '\0'
			return null;

		case 34: // '"'
		case 39: // '\''
			char q = c;
			StringBuffer sb = new StringBuffer();
			do {
				c = x.next();
				if (c != q) {
					if (c == 0 || c == '\n' || c == '\r') {
						throw x.syntaxError((new StringBuilder()).append("Missing close quote '").append(q).append("'.").toString());
					}
					sb.append(c);
				} else {
					return sb.toString();
				}
			} while (true);

		case 44: // ','
			x.back();
			return "";
		}
		x.back();
		return x.nextTo(',');
	}

	public static JSONArray rowToJSONArray(JSONTokener x) throws JSONException {
		JSONArray ja = new JSONArray();
		do {
			String value = getValue(x);
			char c = x.next();
			if (value == null || ja.length() == 0 && value.length() == 0 && c != ',') {
				return null;
			}
			ja.put(value);
			while (c != ',')  {
				if (c != ' ') {
					if (c == '\n' || c == '\r' || c == 0) {
						return ja;
					} else {
						throw x.syntaxError((new StringBuilder()).append("Bad character '").append(c).append("' (").append(c).append(").").toString());
					}
				}
				c = x.next();
			}
		} while (true);
	}

	public static JSONObject rowToJSONObject(JSONArray names, JSONTokener x) throws JSONException {
		JSONArray ja = rowToJSONArray(x);
		return ja == null ? null : ja.toJSONObject(names);
	}

	public static String rowToString(JSONArray ja) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ja.length(); i++) {
			if (i > 0) {
				sb.append(',');
			}
			Object object = ja.opt(i);
			if (object == null) {
				continue;
			}
			String string = object.toString();
			if (string.length() > 0 && (string.indexOf(',') >= 0 || string.indexOf('\n') >= 0 || string.indexOf('\r') >= 0 || string.indexOf('\0') >= 0 || string.charAt(0) == '"')) {
				sb.append('"');
				int length = string.length();
				for (int j = 0; j < length; j++) {
					char c = string.charAt(j);
					if (c >= ' ' && c != '"') {
						sb.append(c);
					}
				}

				sb.append('"');
			} else {
				sb.append(string);
			}
		}

		sb.append('\n');
		return sb.toString();
	}

	public static JSONArray toJSONArray(String string) throws JSONException {
		return toJSONArray(new JSONTokener(string));
	}

	public static JSONArray toJSONArray(JSONTokener x) throws JSONException {
		return toJSONArray(rowToJSONArray(x), x);
	}

	public static JSONArray toJSONArray(JSONArray names, String string) throws JSONException {
		return toJSONArray(names, new JSONTokener(string));
	}

	public static JSONArray toJSONArray(JSONArray names, JSONTokener x) throws JSONException {
		if (names == null || names.length() == 0) {
			return null;
		}
		JSONArray ja = new JSONArray();
		do {
			JSONObject jo = rowToJSONObject(names, x);
			if (jo == null) {
				break;
			}
			ja.put(jo);
		} while (true);
		if (ja.length() == 0) {
			return null;
		} else {
			return ja;
		}
	}

	public static String toString(JSONArray ja) throws JSONException {
		JSONObject jo = ja.optJSONObject(0);
		if (jo != null) {
			JSONArray names = jo.names();
			if (names != null) {
				return (new StringBuilder()).append(rowToString(names)).append(toString(names, ja)).toString();
			}
		}
		return null;
	}

	public static String toString(JSONArray names, JSONArray ja) throws JSONException {
		if (names == null || names.length() == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ja.length(); i++) {
			JSONObject jo = ja.optJSONObject(i);
			if (jo != null) {
				sb.append(rowToString(jo.toJSONArray(names)));
			}
		}

		return sb.toString();
	}
}
