// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   JsonObject.java

package com.isnowfox.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

// Referenced classes of package com.isnowfox.core:
//			JsonTransform

public class JsonObject
	implements JsonTransform {

	protected static ObjectMapper mapper = new ObjectMapper();

	public JsonObject() {
	}

	public static Object readObject(String json, Class cls) {
		return mapper.readValue(json, cls);
		IOException e;
		e;
		throw new RuntimeException("json read exception", e);
	}

	public void toJson(Appendable appendable) {
		try {
			appendable.append(mapper.writeValueAsString(this));
		}
		catch (Exception e) {
			throw new RuntimeException("json toJson exception", e);
		}
	}

}
