// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   JSONWriter.java

package org.json;

import java.io.IOException;
import java.io.Writer;

// Referenced classes of package org.json:
//			JSONObject, JSONException

public class JSONWriter {

	private static final int maxdepth = 200;
	private boolean comma;
	protected char mode;
	private final JSONObject stack[] = new JSONObject[200];
	private int top;
	protected Writer writer;

	public JSONWriter(Writer w) {
		comma = false;
		mode = 'i';
		top = 0;
		writer = w;
	}

	private JSONWriter append(String string) throws JSONException {
		if (string == null) {
			throw new JSONException("Null pointer");
		}
		if (mode == 'o' || mode == 'a') {
			try {
				if (comma && mode == 'a') {
					writer.write(44);
				}
				writer.write(string);
			}
			catch (IOException e) {
				throw new JSONException(e);
			}
			if (mode == 'o') {
				mode = 'k';
			}
			comma = true;
			return this;
		} else {
			throw new JSONException("Value out of sequence.");
		}
	}

	public JSONWriter array() throws JSONException {
		if (mode == 'i' || mode == 'o' || mode == 'a') {
			push(null);
			append("[");
			comma = false;
			return this;
		} else {
			throw new JSONException("Misplaced array.");
		}
	}

	private JSONWriter end(char mode, char c) throws JSONException {
		if (this.mode != mode) {
			throw new JSONException(mode != 'a' ? "Misplaced endObject." : "Misplaced endArray.");
		}
		pop(mode);
		try {
			writer.write(c);
		}
		catch (IOException e) {
			throw new JSONException(e);
		}
		comma = true;
		return this;
	}

	public JSONWriter endArray() throws JSONException {
		return end('a', ']');
	}

	public JSONWriter endObject() throws JSONException {
		return end('k', '}');
	}

	public JSONWriter key(String string) throws JSONException {
		if (string == null) {
			throw new JSONException("Null key.");
		}
		if (mode != 'k') {
			break MISSING_BLOCK_LABEL_101;
		}
		stack[top - 1].putOnce(string, Boolean.TRUE);
		if (comma) {
			writer.write(44);
		}
		writer.write(JSONObject.quote(string));
		writer.write(58);
		comma = false;
		mode = 'o';
		return this;
		IOException e;
		e;
		throw new JSONException(e);
		throw new JSONException("Misplaced key.");
	}

	public JSONWriter object() throws JSONException {
		if (mode == 'i') {
			mode = 'o';
		}
		if (mode == 'o' || mode == 'a') {
			append("{");
			push(new JSONObject());
			comma = false;
			return this;
		} else {
			throw new JSONException("Misplaced object.");
		}
	}

	private void pop(char c) throws JSONException {
		if (top <= 0) {
			throw new JSONException("Nesting error.");
		}
		char m = stack[top - 1] != null ? 'k' : 'a';
		if (m != c) {
			throw new JSONException("Nesting error.");
		} else {
			top--;
			mode = top != 0 ? ((char) (stack[top - 1] != null ? 'k' : 'a')) : 'd';
			return;
		}
	}

	private void push(JSONObject jo) throws JSONException {
		if (top >= 200) {
			throw new JSONException("Nesting too deep.");
		} else {
			stack[top] = jo;
			mode = jo != null ? 'k' : 'a';
			top++;
			return;
		}
	}

	public JSONWriter value(boolean b) throws JSONException {
		return append(b ? "true" : "false");
	}

	public JSONWriter value(double d) throws JSONException {
		return value(new Double(d));
	}

	public JSONWriter value(long l) throws JSONException {
		return append(Long.toString(l));
	}

	public JSONWriter value(Object object) throws JSONException {
		return append(JSONObject.valueToString(object));
	}
}
