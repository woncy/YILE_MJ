// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   JSONML.java

package org.json;

import java.util.Iterator;

// Referenced classes of package org.json:
//			JSONException, JSONArray, JSONObject, XMLTokener, 
//			XML

public class JSONML {

	public JSONML() {
	}

	private static Object parse(XMLTokener x, boolean arrayForm, JSONArray ja) throws JSONException {
		String closeTag = null;
		JSONArray newja = null;
		JSONObject newjo = null;
		String tagName = null;
		do {
			if (!x.more()) {
				throw x.syntaxError("Bad XML");
			}
			Object token = x.nextContent();
			if (token == XML.LT) {
				token = x.nextToken();
				if (token instanceof Character) {
					if (token == XML.SLASH) {
						token = x.nextToken();
						if (!(token instanceof String)) {
							throw new JSONException((new StringBuilder()).append("Expected a closing name instead of '").append(token).append("'.").toString());
						}
						if (x.nextToken() != XML.GT) {
							throw x.syntaxError("Misshaped close tag");
						} else {
							return token;
						}
					}
					if (token == XML.BANG) {
						char c = x.next();
						if (c == '-') {
							if (x.next() == '-') {
								x.skipPast("-->");
							}
							x.back();
						} else
						if (c == '[') {
							token = x.nextToken();
							if (token.equals("CDATA") && x.next() == '[') {
								if (ja != null) {
									ja.put(x.nextCDATA());
								}
							} else {
								throw x.syntaxError("Expected 'CDATA['");
							}
						} else {
							int i = 1;
							do {
								token = x.nextMeta();
								if (token == null) {
									throw x.syntaxError("Missing '>' after '<!'.");
								}
								if (token == XML.LT) {
									i++;
								} else
								if (token == XML.GT) {
									i--;
								}
							} while (i > 0);
						}
					} else
					if (token == XML.QUEST) {
						x.skipPast("?>");
					} else {
						throw x.syntaxError("Misshaped tag");
					}
				} else {
					if (!(token instanceof String)) {
						throw x.syntaxError((new StringBuilder()).append("Bad tagName '").append(token).append("'.").toString());
					}
					tagName = (String)token;
					newja = new JSONArray();
					newjo = new JSONObject();
					if (arrayForm) {
						newja.put(tagName);
						if (ja != null) {
							ja.put(newja);
						}
					} else {
						newjo.put("tagName", tagName);
						if (ja != null) {
							ja.put(newjo);
						}
					}
					token = null;
					do {
						if (token == null) {
							token = x.nextToken();
						}
						if (token == null) {
							throw x.syntaxError("Misshaped tag");
						}
						if (!(token instanceof String)) {
							break;
						}
						String attribute = (String)token;
						if (!arrayForm && ("tagName".equals(attribute) || "childNode".equals(attribute))) {
							throw x.syntaxError("Reserved attribute.");
						}
						token = x.nextToken();
						if (token == XML.EQ) {
							token = x.nextToken();
							if (!(token instanceof String)) {
								throw x.syntaxError("Missing value");
							}
							newjo.accumulate(attribute, XML.stringToValue((String)token));
							token = null;
						} else {
							newjo.accumulate(attribute, "");
						}
					} while (true);
					if (arrayForm && newjo.length() > 0) {
						newja.put(newjo);
					}
					if (token == XML.SLASH) {
						if (x.nextToken() != XML.GT) {
							throw x.syntaxError("Misshaped tag");
						}
						if (ja == null) {
							if (arrayForm) {
								return newja;
							} else {
								return newjo;
							}
						}
					} else {
						if (token != XML.GT) {
							throw x.syntaxError("Misshaped tag");
						}
						closeTag = (String)parse(x, arrayForm, newja);
						if (closeTag != null) {
							if (!closeTag.equals(tagName)) {
								throw x.syntaxError((new StringBuilder()).append("Mismatched '").append(tagName).append("' and '").append(closeTag).append("'").toString());
							}
							tagName = null;
							if (!arrayForm && newja.length() > 0) {
								newjo.put("childNodes", newja);
							}
							if (ja == null) {
								if (arrayForm) {
									return newja;
								} else {
									return newjo;
								}
							}
						}
					}
				}
			} else
			if (ja != null) {
				ja.put((token instanceof String) ? XML.stringToValue((String)token) : token);
			}
		} while (true);
	}

	public static JSONArray toJSONArray(String string) throws JSONException {
		return toJSONArray(new XMLTokener(string));
	}

	public static JSONArray toJSONArray(XMLTokener x) throws JSONException {
		return (JSONArray)parse(x, true, null);
	}

	public static JSONObject toJSONObject(XMLTokener x) throws JSONException {
		return (JSONObject)parse(x, false, null);
	}

	public static JSONObject toJSONObject(String string) throws JSONException {
		return toJSONObject(new XMLTokener(string));
	}

	public static String toString(JSONArray ja) throws JSONException {
		StringBuffer sb = new StringBuffer();
		String tagName = ja.getString(0);
		XML.noSpace(tagName);
		tagName = XML.escape(tagName);
		sb.append('<');
		sb.append(tagName);
		Object object = ja.opt(1);
		int i;
		if (object instanceof JSONObject) {
			i = 2;
			JSONObject jo = (JSONObject)object;
			Iterator keys = jo.keys();
			do {
				if (!keys.hasNext()) {
					break;
				}
				String key = keys.next().toString();
				XML.noSpace(key);
				String value = jo.optString(key);
				if (value != null) {
					sb.append(' ');
					sb.append(XML.escape(key));
					sb.append('=');
					sb.append('"');
					sb.append(XML.escape(value));
					sb.append('"');
				}
			} while (true);
		} else {
			i = 1;
		}
		int length = ja.length();
		if (i >= length) {
			sb.append('/');
			sb.append('>');
		} else {
			sb.append('>');
			do {
				object = ja.get(i);
				i++;
				if (object != null) {
					if (object instanceof String) {
						sb.append(XML.escape(object.toString()));
					} else
					if (object instanceof JSONObject) {
						sb.append(toString((JSONObject)object));
					} else
					if (object instanceof JSONArray) {
						sb.append(toString((JSONArray)object));
					}
				}
			} while (i < length);
			sb.append('<');
			sb.append('/');
			sb.append(tagName);
			sb.append('>');
		}
		return sb.toString();
	}

	public static String toString(JSONObject jo) throws JSONException {
		StringBuffer sb = new StringBuffer();
		String tagName = jo.optString("tagName");
		if (tagName == null) {
			return XML.escape(jo.toString());
		}
		XML.noSpace(tagName);
		tagName = XML.escape(tagName);
		sb.append('<');
		sb.append(tagName);
		Iterator keys = jo.keys();
		do {
			if (!keys.hasNext()) {
				break;
			}
			String key = keys.next().toString();
			if (!"tagName".equals(key) && !"childNodes".equals(key)) {
				XML.noSpace(key);
				String value = jo.optString(key);
				if (value != null) {
					sb.append(' ');
					sb.append(XML.escape(key));
					sb.append('=');
					sb.append('"');
					sb.append(XML.escape(value));
					sb.append('"');
				}
			}
		} while (true);
		JSONArray ja = jo.optJSONArray("childNodes");
		if (ja == null) {
			sb.append('/');
			sb.append('>');
		} else {
			sb.append('>');
			int length = ja.length();
			for (int i = 0; i < length; i++) {
				Object object = ja.get(i);
				if (object == null) {
					continue;
				}
				if (object instanceof String) {
					sb.append(XML.escape(object.toString()));
					continue;
				}
				if (object instanceof JSONObject) {
					sb.append(toString((JSONObject)object));
					continue;
				}
				if (object instanceof JSONArray) {
					sb.append(toString((JSONArray)object));
				} else {
					sb.append(object.toString());
				}
			}

			sb.append('<');
			sb.append('/');
			sb.append(tagName);
			sb.append('>');
		}
		return sb.toString();
	}
}
