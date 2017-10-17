// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   XML.java

package org.json;

import java.util.Iterator;

// Referenced classes of package org.json:
//			JSONException, JSONObject, XMLTokener, JSONArray

public class XML {

	public static final Character AMP = new Character('&');
	public static final Character APOS = new Character('\'');
	public static final Character BANG = new Character('!');
	public static final Character EQ = new Character('=');
	public static final Character GT = new Character('>');
	public static final Character LT = new Character('<');
	public static final Character QUEST = new Character('?');
	public static final Character QUOT = new Character('"');
	public static final Character SLASH = new Character('/');

	public XML() {
	}

	public static String escape(String string) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		for (int length = string.length(); i < length; i++) {
			char c = string.charAt(i);
			switch (c) {
			case 38: // '&'
				sb.append("&amp;");
				break;

			case 60: // '<'
				sb.append("&lt;");
				break;

			case 62: // '>'
				sb.append("&gt;");
				break;

			case 34: // '"'
				sb.append("&quot;");
				break;

			case 39: // '\''
				sb.append("&apos;");
				break;

			default:
				sb.append(c);
				break;
			}
		}

		return sb.toString();
	}

	public static void noSpace(String string) throws JSONException {
		int length = string.length();
		if (length == 0) {
			throw new JSONException("Empty string.");
		}
		for (int i = 0; i < length; i++) {
			if (Character.isWhitespace(string.charAt(i))) {
				throw new JSONException((new StringBuilder()).append("'").append(string).append("' contains a space character.").toString());
			}
		}

	}

	private static boolean parse(XMLTokener x, JSONObject context, String name) throws JSONException {
		JSONObject jsonobject = null;
		Object token = x.nextToken();
		if (token == BANG) {
			char c = x.next();
			if (c == '-') {
				if (x.next() == '-') {
					x.skipPast("-->");
					return false;
				}
				x.back();
			} else
			if (c == '[') {
				token = x.nextToken();
				if ("CDATA".equals(token) && x.next() == '[') {
					String string = x.nextCDATA();
					if (string.length() > 0) {
						context.accumulate("content", string);
					}
					return false;
				} else {
					throw x.syntaxError("Expected 'CDATA['");
				}
			}
			int i = 1;
			do {
				token = x.nextMeta();
				if (token == null) {
					throw x.syntaxError("Missing '>' after '<!'.");
				}
				if (token == LT) {
					i++;
				} else
				if (token == GT) {
					i--;
				}
			} while (i > 0);
			return false;
		}
		if (token == QUEST) {
			x.skipPast("?>");
			return false;
		}
		if (token == SLASH) {
			token = x.nextToken();
			if (name == null) {
				throw x.syntaxError((new StringBuilder()).append("Mismatched close tag ").append(token).toString());
			}
			if (!token.equals(name)) {
				throw x.syntaxError((new StringBuilder()).append("Mismatched ").append(name).append(" and ").append(token).toString());
			}
			if (x.nextToken() != GT) {
				throw x.syntaxError("Misshaped close tag");
			} else {
				return true;
			}
		}
		if (token instanceof Character) {
			throw x.syntaxError("Misshaped tag");
		}
		String tagName = (String)token;
		token = null;
		jsonobject = new JSONObject();
		do {
			if (token == null) {
				token = x.nextToken();
			}
			if (!(token instanceof String)) {
				break;
			}
			String string = (String)token;
			token = x.nextToken();
			if (token == EQ) {
				token = x.nextToken();
				if (!(token instanceof String)) {
					throw x.syntaxError("Missing value");
				}
				jsonobject.accumulate(string, stringToValue((String)token));
				token = null;
			} else {
				jsonobject.accumulate(string, "");
			}
		} while (true);
		if (token == SLASH) {
			if (x.nextToken() != GT) {
				throw x.syntaxError("Misshaped tag");
			}
			if (jsonobject.length() > 0) {
				context.accumulate(tagName, jsonobject);
			} else {
				context.accumulate(tagName, "");
			}
			return false;
		}
		if (token == GT) {
			do {
				do {
					token = x.nextContent();
					if (token == null) {
						if (tagName != null) {
							throw x.syntaxError((new StringBuilder()).append("Unclosed tag ").append(tagName).toString());
						} else {
							return false;
						}
					}
					if (!(token instanceof String)) {
						break;
					}
					String string = (String)token;
					if (string.length() > 0) {
						jsonobject.accumulate("content", stringToValue(string));
					}
				} while (true);
			} while (token != LT || !parse(x, jsonobject, tagName));
			if (jsonobject.length() == 0) {
				context.accumulate(tagName, "");
			} else
			if (jsonobject.length() == 1 && jsonobject.opt("content") != null) {
				context.accumulate(tagName, jsonobject.opt("content"));
			} else {
				context.accumulate(tagName, jsonobject);
			}
			return false;
		} else {
			throw x.syntaxError("Misshaped tag");
		}
	}

	public static Object stringToValue(String string) {
		if ("".equals(string)) {
			return string;
		}
		if ("true".equalsIgnoreCase(string)) {
			return Boolean.TRUE;
		}
		if ("false".equalsIgnoreCase(string)) {
			return Boolean.FALSE;
		}
		if ("null".equalsIgnoreCase(string)) {
			return JSONObject.NULL;
		}
		if ("0".equals(string)) {
			return new Integer(0);
		}
		char initial;
		boolean negative;
		initial = string.charAt(0);
		negative = false;
		if (initial == '-') {
			initial = string.charAt(1);
			negative = true;
		}
		if (initial == '0' && string.charAt(negative ? 2 : 1) == '0') {
			return string;
		}
		if (initial < '0' || initial > '9') {
			break MISSING_BLOCK_LABEL_200;
		}
		if (string.indexOf('.') >= 0) {
			return Double.valueOf(string);
		}
		Long myLong;
		if (string.indexOf('e') >= 0 || string.indexOf('E') >= 0) {
			break MISSING_BLOCK_LABEL_200;
		}
		myLong = new Long(string);
		if (myLong.longValue() == (long)myLong.intValue()) {
			return new Integer(myLong.intValue());
		}
		return myLong;
		Exception exception;
		exception;
		return string;
	}

	public static JSONObject toJSONObject(String string) throws JSONException {
		JSONObject jo = new JSONObject();
		for (XMLTokener x = new XMLTokener(string); x.more() && x.skipPast("<"); parse(x, jo, null)) { }
		return jo;
	}

	public static String toString(Object object) throws JSONException {
		return toString(object, null);
	}

	public static String toString(Object object, String tagName) throws JSONException {
		StringBuffer sb = new StringBuffer();
		if (object instanceof JSONObject) {
			if (tagName != null) {
				sb.append('<');
				sb.append(tagName);
				sb.append('>');
			}
			JSONObject jo = (JSONObject)object;
			for (Iterator keys = jo.keys(); keys.hasNext();) {
				String key = keys.next().toString();
				Object value = jo.opt(key);
				if (value == null) {
					value = "";
				}
				String string;
				if (value instanceof String) {
					string = (String)value;
				} else {
					string = null;
				}
				if ("content".equals(key)) {
					if (value instanceof JSONArray) {
						JSONArray ja = (JSONArray)value;
						int length = ja.length();
						int i = 0;
						while (i < length)  {
							if (i > 0) {
								sb.append('\n');
							}
							sb.append(escape(ja.get(i).toString()));
							i++;
						}
					} else {
						sb.append(escape(value.toString()));
					}
				} else
				if (value instanceof JSONArray) {
					JSONArray ja = (JSONArray)value;
					int length = ja.length();
					int i = 0;
					while (i < length)  {
						value = ja.get(i);
						if (value instanceof JSONArray) {
							sb.append('<');
							sb.append(key);
							sb.append('>');
							sb.append(toString(value));
							sb.append("</");
							sb.append(key);
							sb.append('>');
						} else {
							sb.append(toString(value, key));
						}
						i++;
					}
				} else
				if ("".equals(value)) {
					sb.append('<');
					sb.append(key);
					sb.append("/>");
				} else {
					sb.append(toString(value, key));
				}
			}

			if (tagName != null) {
				sb.append("</");
				sb.append(tagName);
				sb.append('>');
			}
			return sb.toString();
		}
		if (object.getClass().isArray()) {
			object = new JSONArray(object);
		}
		if (object instanceof JSONArray) {
			JSONArray ja = (JSONArray)object;
			int length = ja.length();
			for (int i = 0; i < length; i++) {
				sb.append(toString(ja.opt(i), tagName != null ? tagName : "array"));
			}

			return sb.toString();
		} else {
			String string = object != null ? escape(object.toString()) : "null";
			return tagName != null ? string.length() != 0 ? (new StringBuilder()).append("<").append(tagName).append(">").append(string).append("</").append(tagName).append(">").toString() : (new StringBuilder()).append("<").append(tagName).append("/>").toString() : (new StringBuilder()).append("\"").append(string).append("\"").toString();
		}
	}

}
