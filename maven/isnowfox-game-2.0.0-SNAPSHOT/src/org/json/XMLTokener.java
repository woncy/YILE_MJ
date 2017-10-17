// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   XMLTokener.java

package org.json;

import java.util.HashMap;

// Referenced classes of package org.json:
//			JSONTokener, JSONException, XML

public class XMLTokener extends JSONTokener {

	public static final HashMap entity;

	public XMLTokener(String s) {
		super(s);
	}

	public String nextCDATA() throws JSONException {
		StringBuffer sb = new StringBuffer();
		int i;
		do {
			char c = next();
			if (end()) {
				throw syntaxError("Unclosed CDATA");
			}
			sb.append(c);
			i = sb.length() - 3;
		} while (i < 0 || sb.charAt(i) != ']' || sb.charAt(i + 1) != ']' || sb.charAt(i + 2) != '>');
		sb.setLength(i);
		return sb.toString();
	}

	public Object nextContent() throws JSONException {
		char c;
		do {
			c = next();
		} while (Character.isWhitespace(c));
		if (c == 0) {
			return null;
		}
		if (c == '<') {
			return XML.LT;
		}
		StringBuffer sb = new StringBuffer();
		do {
			if (c == '<' || c == 0) {
				back();
				return sb.toString().trim();
			}
			if (c == '&') {
				sb.append(nextEntity(c));
			} else {
				sb.append(c);
			}
			c = next();
		} while (true);
	}

	public Object nextEntity(char ampersand) throws JSONException {
		StringBuffer sb = new StringBuffer();
		char c;
		do {
			c = next();
			if (!Character.isLetterOrDigit(c) && c != '#') {
				break;
			}
			sb.append(Character.toLowerCase(c));
		} while (true);
		if (c != ';') {
			throw syntaxError((new StringBuilder()).append("Missing ';' in XML entity: &").append(sb).toString());
		} else {
			String string = sb.toString();
			Object object = entity.get(string);
			return object == null ? (new StringBuilder()).append(ampersand).append(string).append(";").toString() : object;
		}
	}

	public Object nextMeta() throws JSONException {
		char c;
		do {
			c = next();
		} while (Character.isWhitespace(c));
		switch (c) {
		case 0: // '\0'
			throw syntaxError("Misshaped meta tag");

		case 60: // '<'
			return XML.LT;

		case 62: // '>'
			return XML.GT;

		case 47: // '/'
			return XML.SLASH;

		case 61: // '='
			return XML.EQ;

		case 33: // '!'
			return XML.BANG;

		case 63: // '?'
			return XML.QUEST;

		case 34: // '"'
		case 39: // '\''
			char q = c;
			do {
				c = next();
				if (c == 0) {
					throw syntaxError("Unterminated string");
				}
			} while (c != q);
			return Boolean.TRUE;
		}
		do {
			c = next();
			if (Character.isWhitespace(c)) {
				return Boolean.TRUE;
			}
			switch (c) {
			case 0: // '\0'
			case 33: // '!'
			case 34: // '"'
			case 39: // '\''
			case 47: // '/'
			case 60: // '<'
			case 61: // '='
			case 62: // '>'
			case 63: // '?'
				back();
				return Boolean.TRUE;
			}
		} while (true);
	}

	public Object nextToken() throws JSONException {
		char c;
		do {
			c = next();
		} while (Character.isWhitespace(c));
		StringBuffer sb;
		switch (c) {
		case 0: // '\0'
			throw syntaxError("Misshaped element");

		case 60: // '<'
			throw syntaxError("Misplaced '<'");

		case 62: // '>'
			return XML.GT;

		case 47: // '/'
			return XML.SLASH;

		case 61: // '='
			return XML.EQ;

		case 33: // '!'
			return XML.BANG;

		case 63: // '?'
			return XML.QUEST;

		case 34: // '"'
		case 39: // '\''
			char q = c;
			sb = new StringBuffer();
			do {
				c = next();
				if (c == 0) {
					throw syntaxError("Unterminated string");
				}
				if (c == q) {
					return sb.toString();
				}
				if (c == '&') {
					sb.append(nextEntity(c));
				} else {
					sb.append(c);
				}
			} while (true);

		default:
			sb = new StringBuffer();
			break;
		}
		do {
			sb.append(c);
			c = next();
			if (Character.isWhitespace(c)) {
				return sb.toString();
			}
			switch (c) {
			case 0: // '\0'
				return sb.toString();

			case 33: // '!'
			case 47: // '/'
			case 61: // '='
			case 62: // '>'
			case 63: // '?'
			case 91: // '['
			case 93: // ']'
				back();
				return sb.toString();

			case 34: // '"'
			case 39: // '\''
			case 60: // '<'
				throw syntaxError("Bad character in a name");
			}
		} while (true);
	}

	public boolean skipPast(String to) throws JSONException {
		int offset = 0;
		int length = to.length();
		char circle[] = new char[length];
		for (int i = 0; i < length; i++) {
			char c = next();
			if (c == 0) {
				return false;
			}
			circle[i] = c;
		}

		do {
			do {
				int j = offset;
				boolean b = true;
				for (int i = 0; i < length; i++) {
					if (circle[j] != to.charAt(i)) {
						b = false;
						break;
					}
					if (++j >= length) {
						j -= length;
					}
				}

				if (b) {
					return true;
				}
				char c = next();
				if (c == 0) {
					return false;
				}
				circle[offset] = c;
			} while (++offset < length);
			offset -= length;
		} while (true);
	}

	static  {
		entity = new HashMap(8);
		entity.put("amp", XML.AMP);
		entity.put("apos", XML.APOS);
		entity.put("gt", XML.GT);
		entity.put("lt", XML.LT);
		entity.put("quot", XML.QUOT);
	}
}
