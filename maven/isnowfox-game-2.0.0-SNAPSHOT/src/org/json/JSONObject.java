// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   JSONObject.java

package org.json;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.*;
import java.util.*;

// Referenced classes of package org.json:
//			JSONTokener, JSONArray, JSONException, JSONString

public class JSONObject {
	private static final class Null {

		protected final Object clone() {
			return this;
		}

		public boolean equals(Object object) {
			return object == null || object == this;
		}

		public String toString() {
			return "null";
		}

		private Null() {
		}

	}


	private final Map map;
	public static final Object NULL = new Null();

	public JSONObject() {
		map = new HashMap();
	}

	public JSONObject(JSONObject jo, String names[]) {
		this();
		for (int i = 0; i < names.length; i++) {
			try {
				putOnce(names[i], jo.opt(names[i]));
			}
			catch (Exception exception) { }
		}

	}

	public JSONObject(JSONTokener x) throws JSONException {
		this();
		if (x.nextClean() != '{') {
			throw x.syntaxError("A JSONObject text must begin with '{'");
		}
		do {
			char c = x.nextClean();
			switch (c) {
			case 0: // '\0'
				throw x.syntaxError("A JSONObject text must end with '}'");

			case 125: // '}'
				return;
			}
			x.back();
			String key = x.nextValue().toString();
			c = x.nextClean();
			if (c == '=') {
				if (x.next() != '>') {
					x.back();
				}
			} else
			if (c != ':') {
				throw x.syntaxError("Expected a ':' after a key");
			}
			putOnce(key, x.nextValue());
			switch (x.nextClean()) {
			case 44: // ','
			case 59: // ';'
				if (x.nextClean() == '}') {
					return;
				}
				x.back();
				break;

			case 125: // '}'
				return;

			default:
				throw x.syntaxError("Expected a ',' or '}'");
			}
		} while (true);
	}

	public JSONObject(Map map) {
		this.map = new HashMap();
		if (map != null) {
			Iterator i = map.entrySet().iterator();
			do {
				if (!i.hasNext()) {
					break;
				}
				java.util.Map.Entry e = (java.util.Map.Entry)i.next();
				Object value = e.getValue();
				if (value != null) {
					this.map.put(e.getKey(), wrap(value));
				}
			} while (true);
		}
	}

	public JSONObject(Object bean) {
		this();
		populateMap(bean);
	}

	public JSONObject(Object object, String names[]) {
		this();
		Class c = object.getClass();
		for (int i = 0; i < names.length; i++) {
			String name = names[i];
			try {
				putOpt(name, c.getField(name).get(object));
			}
			catch (Exception exception) { }
		}

	}

	public JSONObject(String source) throws JSONException {
		this(new JSONTokener(source));
	}

	public JSONObject(String baseName, Locale locale) throws JSONException {
		this();
		ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, Thread.currentThread().getContextClassLoader());
		Enumeration keys = bundle.getKeys();
		do {
			if (!keys.hasMoreElements()) {
				break;
			}
			Object key = keys.nextElement();
			if (key instanceof String) {
				String path[] = ((String)key).split("\\.");
				int last = path.length - 1;
				JSONObject target = this;
				for (int i = 0; i < last; i++) {
					String segment = path[i];
					JSONObject nextTarget = target.optJSONObject(segment);
					if (nextTarget == null) {
						nextTarget = new JSONObject();
						target.put(segment, nextTarget);
					}
					target = nextTarget;
				}

				target.put(path[last], bundle.getString((String)key));
			}
		} while (true);
	}

	public JSONObject accumulate(String key, Object value) throws JSONException {
		testValidity(value);
		Object object = opt(key);
		if (object == null) {
			put(key, (value instanceof JSONArray) ? ((Object) ((new JSONArray()).put(value))) : value);
		} else
		if (object instanceof JSONArray) {
			((JSONArray)object).put(value);
		} else {
			put(key, (new JSONArray()).put(object).put(value));
		}
		return this;
	}

	public JSONObject append(String key, Object value) throws JSONException {
		testValidity(value);
		Object object = opt(key);
		if (object == null) {
			put(key, (new JSONArray()).put(value));
		} else
		if (object instanceof JSONArray) {
			put(key, ((JSONArray)object).put(value));
		} else {
			throw new JSONException((new StringBuilder()).append("JSONObject[").append(key).append("] is not a JSONArray.").toString());
		}
		return this;
	}

	public static String doubleToString(double d) {
		if (Double.isInfinite(d) || Double.isNaN(d)) {
			return "null";
		}
		String string = Double.toString(d);
		if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string.indexOf('E') < 0) {
			for (; string.endsWith("0"); string = string.substring(0, string.length() - 1)) { }
			if (string.endsWith(".")) {
				string = string.substring(0, string.length() - 1);
			}
		}
		return string;
	}

	public Object get(String key) throws JSONException {
		if (key == null) {
			throw new JSONException("Null key.");
		}
		Object object = opt(key);
		if (object == null) {
			throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] not found.").toString());
		} else {
			return object;
		}
	}

	public boolean getBoolean(String key) throws JSONException {
		Object object = get(key);
		if (object.equals(Boolean.FALSE) || (object instanceof String) && ((String)object).equalsIgnoreCase("false")) {
			return false;
		}
		if (object.equals(Boolean.TRUE) || (object instanceof String) && ((String)object).equalsIgnoreCase("true")) {
			return true;
		} else {
			throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] is not a Boolean.").toString());
		}
	}

	public double getDouble(String key) throws JSONException {
		Object object = get(key);
		return (object instanceof Number) ? ((Number)object).doubleValue() : Double.parseDouble((String)object);
		Exception e;
		e;
		throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] is not a number.").toString());
	}

	public int getInt(String key) throws JSONException {
		Object object = get(key);
		return (object instanceof Number) ? ((Number)object).intValue() : Integer.parseInt((String)object);
		Exception e;
		e;
		throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] is not an int.").toString());
	}

	public JSONArray getJSONArray(String key) throws JSONException {
		Object object = get(key);
		if (object instanceof JSONArray) {
			return (JSONArray)object;
		} else {
			throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] is not a JSONArray.").toString());
		}
	}

	public JSONObject getJSONObject(String key) throws JSONException {
		Object object = get(key);
		if (object instanceof JSONObject) {
			return (JSONObject)object;
		} else {
			throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] is not a JSONObject.").toString());
		}
	}

	public long getLong(String key) throws JSONException {
		Object object = get(key);
		return (object instanceof Number) ? ((Number)object).longValue() : Long.parseLong((String)object);
		Exception e;
		e;
		throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] is not a long.").toString());
	}

	public static String[] getNames(JSONObject jo) {
		int length = jo.length();
		if (length == 0) {
			return null;
		}
		Iterator iterator = jo.keys();
		String names[] = new String[length];
		for (int i = 0; iterator.hasNext(); i++) {
			names[i] = (String)iterator.next();
		}

		return names;
	}

	public static String[] getNames(Object object) {
		if (object == null) {
			return null;
		}
		Class klass = object.getClass();
		Field fields[] = klass.getFields();
		int length = fields.length;
		if (length == 0) {
			return null;
		}
		String names[] = new String[length];
		for (int i = 0; i < length; i++) {
			names[i] = fields[i].getName();
		}

		return names;
	}

	public String getString(String key) throws JSONException {
		Object object = get(key);
		if (object instanceof String) {
			return (String)object;
		} else {
			throw new JSONException((new StringBuilder()).append("JSONObject[").append(quote(key)).append("] not a string.").toString());
		}
	}

	public boolean has(String key) {
		return map.containsKey(key);
	}

	public JSONObject increment(String key) throws JSONException {
		Object value = opt(key);
		if (value == null) {
			put(key, 1);
		} else
		if (value instanceof Integer) {
			put(key, ((Integer)value).intValue() + 1);
		} else
		if (value instanceof Long) {
			put(key, ((Long)value).longValue() + 1L);
		} else
		if (value instanceof Double) {
			put(key, ((Double)value).doubleValue() + 1.0D);
		} else
		if (value instanceof Float) {
			put(key, ((Float)value).floatValue() + 1.0F);
		} else {
			throw new JSONException((new StringBuilder()).append("Unable to increment [").append(quote(key)).append("].").toString());
		}
		return this;
	}

	public boolean isNull(String key) {
		return NULL.equals(opt(key));
	}

	public Iterator keys() {
		return map.keySet().iterator();
	}

	public int length() {
		return map.size();
	}

	public JSONArray names() {
		JSONArray ja = new JSONArray();
		for (Iterator keys = keys(); keys.hasNext(); ja.put(keys.next())) { }
		return ja.length() != 0 ? ja : null;
	}

	public static String numberToString(Number number) throws JSONException {
		if (number == null) {
			throw new JSONException("Null pointer");
		}
		testValidity(number);
		String string = number.toString();
		if (string.indexOf('.') > 0 && string.indexOf('e') < 0 && string.indexOf('E') < 0) {
			for (; string.endsWith("0"); string = string.substring(0, string.length() - 1)) { }
			if (string.endsWith(".")) {
				string = string.substring(0, string.length() - 1);
			}
		}
		return string;
	}

	public Object opt(String key) {
		return key != null ? map.get(key) : null;
	}

	public boolean optBoolean(String key) {
		return optBoolean(key, false);
	}

	public boolean optBoolean(String key, boolean defaultValue) {
		return getBoolean(key);
		Exception e;
		e;
		return defaultValue;
	}

	public double optDouble(String key) {
		return optDouble(key, (0.0D / 0.0D));
	}

	public double optDouble(String key, double defaultValue) {
		return getDouble(key);
		Exception e;
		e;
		return defaultValue;
	}

	public int optInt(String key) {
		return optInt(key, 0);
	}

	public int optInt(String key, int defaultValue) {
		return getInt(key);
		Exception e;
		e;
		return defaultValue;
	}

	public JSONArray optJSONArray(String key) {
		Object o = opt(key);
		return (o instanceof JSONArray) ? (JSONArray)o : null;
	}

	public JSONObject optJSONObject(String key) {
		Object object = opt(key);
		return (object instanceof JSONObject) ? (JSONObject)object : null;
	}

	public long optLong(String key) {
		return optLong(key, 0L);
	}

	public long optLong(String key, long defaultValue) {
		return getLong(key);
		Exception e;
		e;
		return defaultValue;
	}

	public String optString(String key) {
		return optString(key, "");
	}

	public String optString(String key, String defaultValue) {
		Object object = opt(key);
		return NULL.equals(object) ? defaultValue : object.toString();
	}

	private void populateMap(Object bean) {
		Class klass = bean.getClass();
		boolean includeSuperClass = klass.getClassLoader() != null;
		Method methods[] = includeSuperClass ? klass.getMethods() : klass.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			try {
				Method method = methods[i];
				if (!Modifier.isPublic(method.getModifiers())) {
					continue;
				}
				String name = method.getName();
				String key = "";
				if (name.startsWith("get")) {
					if ("getClass".equals(name) || "getDeclaringClass".equals(name)) {
						key = "";
					} else {
						key = name.substring(3);
					}
				} else
				if (name.startsWith("is")) {
					key = name.substring(2);
				}
				if (key.length() <= 0 || !Character.isUpperCase(key.charAt(0)) || method.getParameterTypes().length != 0) {
					continue;
				}
				if (key.length() == 1) {
					key = key.toLowerCase();
				} else
				if (!Character.isUpperCase(key.charAt(1))) {
					key = (new StringBuilder()).append(key.substring(0, 1).toLowerCase()).append(key.substring(1)).toString();
				}
				Object result = method.invoke(bean, (Object[])null);
				if (result != null) {
					map.put(key, wrap(result));
				}
			}
			catch (Exception exception) { }
		}

	}

	public JSONObject put(String key, boolean value) throws JSONException {
		put(key, value ? ((Object) (Boolean.TRUE)) : ((Object) (Boolean.FALSE)));
		return this;
	}

	public JSONObject put(String key, Collection value) throws JSONException {
		put(key, new JSONArray(value));
		return this;
	}

	public JSONObject put(String key, double value) throws JSONException {
		put(key, new Double(value));
		return this;
	}

	public JSONObject put(String key, int value) throws JSONException {
		put(key, new Integer(value));
		return this;
	}

	public JSONObject put(String key, long value) throws JSONException {
		put(key, new Long(value));
		return this;
	}

	public JSONObject put(String key, Map value) throws JSONException {
		put(key, new JSONObject(value));
		return this;
	}

	public JSONObject put(String key, Object value) throws JSONException {
		if (key == null) {
			throw new JSONException("Null key.");
		}
		if (value != null) {
			testValidity(value);
			map.put(key, value);
		} else {
			remove(key);
		}
		return this;
	}

	public JSONObject putOnce(String key, Object value) throws JSONException {
		if (key != null && value != null) {
			if (opt(key) != null) {
				throw new JSONException((new StringBuilder()).append("Duplicate key \"").append(key).append("\"").toString());
			}
			put(key, value);
		}
		return this;
	}

	public JSONObject putOpt(String key, Object value) throws JSONException {
		if (key != null && value != null) {
			put(key, value);
		}
		return this;
	}

	public static String quote(String string) {
		if (string == null || string.length() == 0) {
			return "\"\"";
		}
		char c = '\0';
		int len = string.length();
		StringBuffer sb = new StringBuffer(len + 4);
		sb.append('"');
		for (int i = 0; i < len; i++) {
			char b = c;
			c = string.charAt(i);
			switch (c) {
			case 34: // '"'
			case 92: // '\\'
				sb.append('\\');
				sb.append(c);
				break;

			case 47: // '/'
				if (b == '<') {
					sb.append('\\');
				}
				sb.append(c);
				break;

			case 8: // '\b'
				sb.append("\\b");
				break;

			case 9: // '\t'
				sb.append("\\t");
				break;

			case 10: // '\n'
				sb.append("\\n");
				break;

			case 12: // '\f'
				sb.append("\\f");
				break;

			case 13: // '\r'
				sb.append("\\r");
				break;

			default:
				if (c < ' ' || c >= '\200' && c < '\240' || c >= '\u2000' && c < '\u2100') {
					String hhhh = (new StringBuilder()).append("000").append(Integer.toHexString(c)).toString();
					sb.append((new StringBuilder()).append("\\u").append(hhhh.substring(hhhh.length() - 4)).toString());
				} else {
					sb.append(c);
				}
				break;
			}
		}

		sb.append('"');
		return sb.toString();
	}

	public Object remove(String key) {
		return map.remove(key);
	}

	public static Object stringToValue(String string) {
		if (string.equals("")) {
			return string;
		}
		if (string.equalsIgnoreCase("true")) {
			return Boolean.TRUE;
		}
		if (string.equalsIgnoreCase("false")) {
			return Boolean.FALSE;
		}
		if (string.equalsIgnoreCase("null")) {
			return NULL;
		}
		char b = string.charAt(0);
		if ((b < '0' || b > '9') && b != '.' && b != '-' && b != '+') {
			break MISSING_BLOCK_LABEL_177;
		}
		Double d;
		if (string.indexOf('.') <= -1 && string.indexOf('e') <= -1 && string.indexOf('E') <= -1) {
			break MISSING_BLOCK_LABEL_137;
		}
		d = Double.valueOf(string);
		if (!d.isInfinite() && !d.isNaN()) {
			return d;
		}
		break MISSING_BLOCK_LABEL_177;
		Long myLong = new Long(string);
		if (myLong.longValue() == (long)myLong.intValue()) {
			return new Integer(myLong.intValue());
		}
		return myLong;
		Exception exception;
		exception;
		return string;
	}

	public static void testValidity(Object o) throws JSONException {
		if (o != null) {
			if (o instanceof Double) {
				if (((Double)o).isInfinite() || ((Double)o).isNaN()) {
					throw new JSONException("JSON does not allow non-finite numbers.");
				}
			} else
			if ((o instanceof Float) && (((Float)o).isInfinite() || ((Float)o).isNaN())) {
				throw new JSONException("JSON does not allow non-finite numbers.");
			}
		}
	}

	public JSONArray toJSONArray(JSONArray names) throws JSONException {
		if (names == null || names.length() == 0) {
			return null;
		}
		JSONArray ja = new JSONArray();
		for (int i = 0; i < names.length(); i++) {
			ja.put(opt(names.getString(i)));
		}

		return ja;
	}

	public String toString() {
		StringBuffer sb;
		Iterator keys = keys();
		sb = new StringBuffer("{");
		Object o;
		for (; keys.hasNext(); sb.append(valueToString(map.get(o)))) {
			if (sb.length() > 1) {
				sb.append(',');
			}
			o = keys.next();
			sb.append(quote(o.toString()));
			sb.append(':');
		}

		sb.append('}');
		return sb.toString();
		Exception e;
		e;
		return null;
	}

	public String toString(int indentFactor) throws JSONException {
		return toString(indentFactor, 0);
	}

	String toString(int indentFactor, int indent) throws JSONException {
		int length = length();
		if (length == 0) {
			return "{}";
		}
		Iterator keys = keys();
		int newindent = indent + indentFactor;
		StringBuffer sb = new StringBuffer("{");
		if (length == 1) {
			Object object = keys.next();
			sb.append(quote(object.toString()));
			sb.append(": ");
			sb.append(valueToString(map.get(object), indentFactor, indent));
		} else {
			Object object;
			for (; keys.hasNext(); sb.append(valueToString(map.get(object), indentFactor, newindent))) {
				object = keys.next();
				if (sb.length() > 1) {
					sb.append(",\n");
				} else {
					sb.append('\n');
				}
				for (int i = 0; i < newindent; i++) {
					sb.append(' ');
				}

				sb.append(quote(object.toString()));
				sb.append(": ");
			}

			if (sb.length() > 1) {
				sb.append('\n');
				for (int i = 0; i < indent; i++) {
					sb.append(' ');
				}

			}
		}
		sb.append('}');
		return sb.toString();
	}

	public static String valueToString(Object value) throws JSONException {
		if (value == null || value.equals(null)) {
			return "null";
		}
		if (value instanceof JSONString) {
			Object object;
			try {
				object = ((JSONString)value).toJSONString();
			}
			catch (Exception e) {
				throw new JSONException(e);
			}
			if (object instanceof String) {
				return (String)object;
			} else {
				throw new JSONException((new StringBuilder()).append("Bad value from toJSONString: ").append(object).toString());
			}
		}
		if (value instanceof Number) {
			return numberToString((Number)value);
		}
		if ((value instanceof Boolean) || (value instanceof JSONObject) || (value instanceof JSONArray)) {
			return value.toString();
		}
		if (value instanceof Map) {
			return (new JSONObject((Map)value)).toString();
		}
		if (value instanceof Collection) {
			return (new JSONArray((Collection)value)).toString();
		}
		if (value.getClass().isArray()) {
			return (new JSONArray(value)).toString();
		} else {
			return quote(value.toString());
		}
	}

	static String valueToString(Object value, int indentFactor, int indent) throws JSONException {
		if (value == null || value.equals(null)) {
			return "null";
		}
		Object o;
		if (!(value instanceof JSONString)) {
			break MISSING_BLOCK_LABEL_48;
		}
		o = ((JSONString)value).toJSONString();
		if (o instanceof String) {
			return (String)o;
		}
		break MISSING_BLOCK_LABEL_48;
		Exception exception;
		exception;
		if (value instanceof Number) {
			return numberToString((Number)value);
		}
		if (value instanceof Boolean) {
			return value.toString();
		}
		if (value instanceof JSONObject) {
			return ((JSONObject)value).toString(indentFactor, indent);
		}
		if (value instanceof JSONArray) {
			return ((JSONArray)value).toString(indentFactor, indent);
		}
		if (value instanceof Map) {
			return (new JSONObject((Map)value)).toString(indentFactor, indent);
		}
		if (value instanceof Collection) {
			return (new JSONArray((Collection)value)).toString(indentFactor, indent);
		}
		if (value.getClass().isArray()) {
			return (new JSONArray(value)).toString(indentFactor, indent);
		} else {
			return quote(value.toString());
		}
	}

	public static Object wrap(Object object) {
		if (object == null) {
			return NULL;
		}
		if ((object instanceof JSONObject) || (object instanceof JSONArray) || NULL.equals(object) || (object instanceof JSONString) || (object instanceof Byte) || (object instanceof Character) || (object instanceof Short) || (object instanceof Integer) || (object instanceof Long) || (object instanceof Boolean) || (object instanceof Float) || (object instanceof Double) || (object instanceof String)) {
			return object;
		}
		if (object instanceof Collection) {
			return new JSONArray((Collection)object);
		}
		if (object.getClass().isArray()) {
			return new JSONArray(object);
		}
		if (object instanceof Map) {
			return new JSONObject((Map)object);
		}
		String objectPackageName;
		Package objectPackage = object.getClass().getPackage();
		objectPackageName = objectPackage == null ? "" : objectPackage.getName();
		if (objectPackageName.startsWith("java.") || objectPackageName.startsWith("javax.") || object.getClass().getClassLoader() == null) {
			return object.toString();
		}
		return new JSONObject(object);
		Exception exception;
		exception;
		return null;
	}

	public Writer write(Writer writer) throws JSONException {
		boolean commanate = false;
		Iterator keys = keys();
		writer.write(123);
		for (; keys.hasNext(); commanate = true) {
			if (commanate) {
				writer.write(44);
			}
			Object key = keys.next();
			writer.write(quote(key.toString()));
			writer.write(58);
			Object value = map.get(key);
			if (value instanceof JSONObject) {
				((JSONObject)value).write(writer);
				continue;
			}
			if (value instanceof JSONArray) {
				((JSONArray)value).write(writer);
			} else {
				writer.write(valueToString(value));
			}
		}

		writer.write(125);
		return writer;
		IOException exception;
		exception;
		throw new JSONException(exception);
	}

}
