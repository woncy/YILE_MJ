// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HTTPTokener.java

package org.json;


// Referenced classes of package org.json:
//			JSONTokener, JSONException

public class HTTPTokener extends JSONTokener {

	public HTTPTokener(String string) {
		super(string);
	}

	public String nextToken() throws JSONException {
		StringBuffer sb = new StringBuffer();
		char c;
		do {
			c = next();
		} while (Character.isWhitespace(c));
		if (c == '"' || c == '\'') {
			char q = c;
			do {
				c = next();
				if (c < ' ') {
					throw syntaxError("Unterminated string.");
				}
				if (c == q) {
					return sb.toString();
				}
				sb.append(c);
			} while (true);
		}
		do {
			if (c == 0 || Character.isWhitespace(c)) {
				return sb.toString();
			}
			sb.append(c);
			c = next();
		} while (true);
	}
}
