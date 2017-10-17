// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   JSONArray.java

package org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.*;

// Referenced classes of package org.json:
//			JSONTokener, JSONException, JSONObject

public class JSONArray {

	private final ArrayList myArrayList;

	public JSONArray() {
		myArrayList = new ArrayList();
	}

	public JSONArray(JSONTokener x) throws JSONException {
		this();
		if (x.nextClean() != '[') {
			throw x.syntaxError("A JSONArray text must start with '['");
		}
		if (x.nextClean() != ']') {
			x.back();
			do {
				if (x.nextClean() == ',') {
					x.back();
					myArrayList.add(JSONObject.NULL);
				} else {
					x.back();
					myArrayList.add(x.nextValue());
				}
				switch (x.nextClean()) {
				case 44: // ','
				case 59: // ';'
					if (x.nextClean() == ']') {
						return;
					}
					x.back();
					break;

				case 93: // ']'
					return;

				default:
					throw x.syntaxError("Expected a ',' or ']'");
				}
			} while (true);
		} else {
			return;
		}
	}

	public JSONArray(String source) throws JSONException {
		this(new JSONTokener(source));
	}

	public JSONArray(Collection collection) {
		myArrayList = new ArrayList();
		if (collection != null) {
			for (Iterator iter = collection.iterator(); iter.hasNext(); myArrayList.add(JSONObject.wrap(iter.next()))) { }
		}
	}

	public JSONArray(Object array) throws JSONException {
		this();
		if (array.getClass().isArray()) {
			int length = Array.getLength(array);
			for (int i = 0; i < length; i++) {
				put(JSONObject.wrap(Array.get(array, i)));
			}

		} else {
			throw new JSONException("JSONArray initial value should be a string or collection or array.");
		}
	}

	public Object get(int index) throws JSONException {
		Object object = opt(index);
		if (object == null) {
			throw new JSONException((new StringBuilder()).append("JSONArray[").append(index).append("] not found.").toString());
		} else {
			return object;
		}
	}

	public boolean getBoolean(int index) throws JSONException {
		Object object = get(index);
		if (object.equals(Boolean.FALSE) || (object instanceof String) && ((String)object).equalsIgnoreCase("false")) {
			return false;
		}
		if (object.equals(Boolean.TRUE) || (object instanceof String) && ((String)object).equalsIgnoreCase("true")) {
			return true;
		} else {
			throw new JSONException((new StringBuilder()).append("JSONArray[").append(index).append("] is not a boolean.").toString());
		}
	}

	public double getDouble(int index) throws JSONException {
		Object object = get(index);
		return (object instanceof Number) ? ((Number)object).doubleValue() : Double.parseDouble((String)object);
		Exception e;
		e;
		throw new JSONException((new StringBuilder()).append("JSONArray[").append(index).append("] is not a number.").toString());
	}

	public int getInt(int index) throws JSONException {
		Object object = get(index);
		return (object instanceof Number) ? ((Number)object).intValue() : Integer.parseInt((String)object);
		Exception e;
		e;
		throw new JSONException((new StringBuilder()).append("JSONArray[").append(index).append("] is not a number.").toString());
	}

	public JSONArray getJSONArray(int index) throws JSONException {
		Object object = get(index);
		if (object instanceof JSONArray) {
			return (JSONArray)object;
		} else {
			throw new JSONException((new StringBuilder()).append("JSONArray[").append(index).append("] is not a JSONArray.").toString());
		}
	}

	public JSONObject getJSONObject(int index) throws JSONException {
		Object object = get(index);
		if (object instanceof JSONObject) {
			return (JSONObject)object;
		} else {
			throw new JSONException((new StringBuilder()).append("JSONArray[").append(index).append("] is not a JSONObject.").toString());
		}
	}

	public long getLong(int index) throws JSONException {
		Object object = get(index);
		return (object instanceof Number) ? ((Number)object).longValue() : Long.parseLong((String)object);
		Exception e;
		e;
		throw new JSONException((new StringBuilder()).append("JSONArray[").append(index).append("] is not a number.").toString());
	}

	public String getString(int index) throws JSONException {
		Object object = get(index);
		if (object instanceof String) {
			return (String)object;
		} else {
			throw new JSONException((new StringBuilder()).append("JSONArray[").append(index).append("] not a string.").toString());
		}
	}

	public boolean isNull(int index) {
		return JSONObject.NULL.equals(opt(index));
	}

	public String join(String separator) throws JSONException {
		int len = length();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < len; i++) {
			if (i > 0) {
				sb.append(separator);
			}
			sb.append(JSONObject.valueToString(myArrayList.get(i)));
		}

		return sb.toString();
	}

	public int length() {
		return myArrayList.size();
	}

	public Object opt(int index) {
		return index >= 0 && index < length() ? myArrayList.get(index) : null;
	}

	public boolean optBoolean(int index) {
		return optBoolean(index, false);
	}

	public boolean optBoolean(int index, boolean defaultValue) {
		return getBoolean(index);
		Exception e;
		e;
		return defaultValue;
	}

	public double optDouble(int index) {
		return optDouble(index, (0.0D / 0.0D));
	}

	public double optDouble(int index, double defaultValue) {
		return getDouble(index);
		Exception e;
		e;
		return defaultValue;
	}

	public int optInt(int index) {
		return optInt(index, 0);
	}

	public int optInt(int index, int defaultValue) {
		return getInt(index);
		Exception e;
		e;
		return defaultValue;
	}

	public JSONArray optJSONArray(int index) {
		Object o = opt(index);
		return (o instanceof JSONArray) ? (JSONArray)o : null;
	}

	public JSONObject optJSONObject(int index) {
		Object o = opt(index);
		return (o instanceof JSONObject) ? (JSONObject)o : null;
	}

	public long optLong(int index) {
		return optLong(index, 0L);
	}

	public long optLong(int index, long defaultValue) {
		return getLong(index);
		Exception e;
		e;
		return defaultValue;
	}

	public String optString(int index) {
		return optString(index, "");
	}

	public String optString(int index, String defaultValue) {
		Object object = opt(index);
		return JSONObject.NULL.equals(object) ? defaultValue : object.toString();
	}

	public JSONArray put(boolean value) {
		put(value ? ((Object) (Boolean.TRUE)) : ((Object) (Boolean.FALSE)));
		return this;
	}

	public JSONArray put(Collection value) {
		put(new JSONArray(value));
		return this;
	}

	public JSONArray put(double value) throws JSONException {
		Double d = new Double(value);
		JSONObject.testValidity(d);
		put(d);
		return this;
	}

	public JSONArray put(int value) {
		put(new Integer(value));
		return this;
	}

	public JSONArray put(long value) {
		put(new Long(value));
		return this;
	}

	public JSONArray put(Map value) {
		put(new JSONObject(value));
		return this;
	}

	public JSONArray put(Object value) {
		myArrayList.add(value);
		return this;
	}

	public JSONArray put(int index, boolean value) throws JSONException {
		put(index, value ? ((Object) (Boolean.TRUE)) : ((Object) (Boolean.FALSE)));
		return this;
	}

	public JSONArray put(int index, Collection value) throws JSONException {
		put(index, new JSONArray(value));
		return this;
	}

	public JSONArray put(int index, double value) throws JSONException {
		put(index, new Double(value));
		return this;
	}

	public JSONArray put(int index, int value) throws JSONException {
		put(index, new Integer(value));
		return this;
	}

	public JSONArray put(int index, long value) throws JSONException {
		put(index, new Long(value));
		return this;
	}

	public JSONArray put(int index, Map value) throws JSONException {
		put(index, new JSONObject(value));
		return this;
	}

	public JSONArray put(int index, Object value) throws JSONException {
		JSONObject.testValidity(value);
		if (index < 0) {
			throw new JSONException((new StringBuilder()).append("JSONArray[").append(index).append("] not found.").toString());
		}
		if (index < length()) {
			myArrayList.set(index, value);
		} else {
			for (; index != length(); put(JSONObject.NULL)) { }
			put(value);
		}
		return this;
	}

	public Object remove(int index) {
		Object o = opt(index);
		myArrayList.remove(index);
		return o;
	}

	public JSONObject toJSONObject(JSONArray names) throws JSONException {
		if (names == null || names.length() == 0 || length() == 0) {
			return null;
		}
		JSONObject jo = new JSONObject();
		for (int i = 0; i < names.length(); i++) {
			jo.put(names.getString(i), opt(i));
		}

		return jo;
	}

	public String toString() {
		return (new StringBuilder()).append('[').append(join(",")).append(']').toString();
		Exception e;
		e;
		return null;
	}

	public String toString(int indentFactor) throws JSONException {
		return toString(indentFactor, 0);
	}

	String toString(int indentFactor, int indent) throws JSONException {
		int len = length();
		if (len == 0) {
			return "[]";
		}
		StringBuffer sb = new StringBuffer("[");
		if (len == 1) {
			sb.append(JSONObject.valueToString(myArrayList.get(0), indentFactor, indent));
		} else {
			int newindent = indent + indentFactor;
			sb.append('\n');
			for (int i = 0; i < len; i++) {
				if (i > 0) {
					sb.append(",\n");
				}
				for (int j = 0; j < newindent; j++) {
					sb.append(' ');
				}

				sb.append(JSONObject.valueToString(myArrayList.get(i), indentFactor, newindent));
			}

			sb.append('\n');
			for (int i = 0; i < indent; i++) {
				sb.append(' ');
			}

		}
		sb.append(']');
		return sb.toString();
	}

	public Writer write(Writer writer) throws JSONException {
		boolean b = false;
		int len = length();
		writer.write(91);
		for (int i = 0; i < len; i++) {
			if (b) {
				writer.write(44);
			}
			Object v = myArrayList.get(i);
			if (v instanceof JSONObject) {
				((JSONObject)v).write(writer);
			} else
			if (v instanceof JSONArray) {
				((JSONArray)v).write(writer);
			} else {
				writer.write(JSONObject.valueToString(v));
			}
			b = true;
		}

		writer.write(93);
		return writer;
		IOException e;
		e;
		throw new JSONException(e);
	}
}
