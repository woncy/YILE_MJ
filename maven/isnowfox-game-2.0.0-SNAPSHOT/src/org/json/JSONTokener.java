// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   JSONTokener.java

package org.json;

import java.io.*;

// Referenced classes of package org.json:
//			JSONException, JSONObject, JSONArray

public class JSONTokener {

	private long character;
	private boolean eof;
	private long index;
	private long line;
	private char previous;
	private Reader reader;
	private boolean usePrevious;

	public JSONTokener(Reader reader) {
		this.reader = ((Reader) (reader.markSupported() ? reader : ((Reader) (new BufferedReader(reader)))));
		eof = false;
		usePrevious = false;
		previous = '\0';
		index = 0L;
		character = 1L;
		line = 1L;
	}

	public JSONTokener(InputStream inputStream) throws JSONException {
		this(((Reader) (new InputStreamReader(inputStream))));
	}

	public JSONTokener(String s) {
		this(((Reader) (new StringReader(s))));
	}

	public void back() throws JSONException {
		if (usePrevious || index <= 0L) {
			throw new JSONException("Stepping back two steps is not supported");
		} else {
			index--;
			character--;
			usePrevious = true;
			eof = false;
			return;
		}
	}

	public static int dehexchar(char c) {
		if (c >= '0' && c <= '9') {
			return c - 48;
		}
		if (c >= 'A' && c <= 'F') {
			return c - 55;
		}
		if (c >= 'a' && c <= 'f') {
			return c - 87;
		} else {
			return -1;
		}
	}

	public boolean end() {
		return eof && !usePrevious;
	}

	public boolean more() throws JSONException {
		next();
		if (end()) {
			return false;
		} else {
			back();
			return true;
		}
	}

	public char next() throws JSONException {
		int c;
		if (usePrevious) {
			usePrevious = false;
			c = previous;
		} else {
			try {
				c = reader.read();
			}
			catch (IOException exception) {
				throw new JSONException(exception);
			}
			if (c <= 0) {
				eof = true;
				c = 0;
			}
		}
		index++;
		if (previous == '\r') {
			line++;
			character = c != 10 ? 1L : 0L;
		} else
		if (c == 10) {
			line++;
			character = 0L;
		} else {
			character++;
		}
		previous = (char)c;
		return previous;
	}

	public char next(char c) throws JSONException {
		char n = next();
		if (n != c) {
			throw syntaxError((new StringBuilder()).append("Expected '").append(c).append("' and instead saw '").append(n).append("'").toString());
		} else {
			return n;
		}
	}

	public String next(int n) throws JSONException {
		if (n == 0) {
			return "";
		}
		char chars[] = new char[n];
		for (int pos = 0; pos < n; pos++) {
			chars[pos] = next();
			if (end()) {
				throw syntaxError("Substring bounds error");
			}
		}

		return new String(chars);
	}

	public char nextClean() throws JSONException {
		char c;
		do {
			c = next();
		} while (c != 0 && c <= ' ');
		return c;
	}

	public String nextString(char quote) throws JSONException {
		StringBuffer sb = new StringBuffer();
		do {
			char c = next();
			switch (c) {
			case 0: // '\0'
			case 10: // '\n'
			case 13: // '\r'
				throw syntaxError("Unterminated string");

			case 92: // '\\'
				c = next();
				switch (c) {
				case 98: // 'b'
					sb.append('\b');
					break;

				case 116: // 't'
					sb.append('\t');
					break;

				case 110: // 'n'
					sb.append('\n');
					break;

				case 102: // 'f'
					sb.append('\f');
					break;

				case 114: // 'r'
					sb.append('\r');
					break;

				case 117: // 'u'
					sb.append((char)Integer.parseInt(next(4), 16));
					break;

				case 34: // '"'
				case 39: // '\''
				case 47: // '/'
				case 92: // '\\'
					sb.append(c);
					break;

				default:
					throw syntaxError("Illegal escape.");
				}
				break;

			default:
				if (c == quote) {
					return sb.toString();
				}
				sb.append(c);
				break;
			}
		} while (true);
	}

	public String nextTo(char delimiter) throws JSONException {
		StringBuffer sb = new StringBuffer();
		do {
			char c = next();
			if (c == delimiter || c == 0 || c == '\n' || c == '\r') {
				if (c != 0) {
					back();
				}
				return sb.toString().trim();
			}
			sb.append(c);
		} while (true);
	}

	public String nextTo(String delimiters) throws JSONException {
		StringBuffer sb = new StringBuffer();
		do {
			char c = next();
			if (delimiters.indexOf(c) >= 0 || c == 0 || c == '\n' || c == '\r') {
				if (c != 0) {
					back();
				}
				return sb.toString().trim();
			}
			sb.append(c);
		} while (true);
	}

	public Object nextValue() throws JSONException {
		char c = nextClean();
		StringBuffer sb;
		switch (c) {
		case 34: // '"'
		case 39: // '\''
			return nextString(c);

		case 123: // '{'
			back();
			return new JSONObject(this);

		case 91: // '['
			back();
			return new JSONArray(this);

		default:
			sb = new StringBuffer();
			break;
		}
		for (; c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0; c = next()) {
			sb.append(c);
		}

		back();
		String string = sb.toString().trim();
		if ("".equals(string)) {
			throw syntaxError("Missing value");
		} else {
			return JSONObject.stringToValue(string);
		}
	}

	public char skipTo(char to) throws JSONException {
		long startIndex;
		long startCharacter;
		long startLine;
		startIndex = index;
		startCharacter = character;
		startLine = line;
		reader.mark(0xf4240);
_L2:
		char c;
		c = next();
		if (c != 0) {
			continue; /* Loop/switch isn't completed */
		}
		reader.reset();
		index = startIndex;
		character = startCharacter;
		line = startLine;
		return c;
		if (c != to) goto _L2; else goto _L1
_L1:
		break MISSING_BLOCK_LABEL_79;
		IOException exc;
		exc;
		throw new JSONException(exc);
		back();
		return c;
	}

	public JSONException syntaxError(String message) {
		return new JSONException((new StringBuilder()).append(message).append(toString()).toString());
	}

	public String toString() {
		return (new StringBuilder()).append(" at ").append(index).append(" [character ").append(character).append(" line ").append(line).append("]").toString();
	}
}
