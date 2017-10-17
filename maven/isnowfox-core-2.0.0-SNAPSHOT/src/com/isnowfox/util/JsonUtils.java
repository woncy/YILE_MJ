// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   JsonUtils.java

package com.isnowfox.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import java.io.IOException;
import java.util.List;

public class JsonUtils {

	public static final ObjectMapper mapper;
	public static final JsonFactory jsonFactory = new JsonFactory();

	public JsonUtils() {
	}

	public static final String serialize(Object o) {
		if (o instanceof List) {
			return mapper.writeValueAsString(((Object) (((List)o).toArray())));
		}
		return mapper.writeValueAsString(o);
		IOException e;
		e;
		throw new RuntimeException(e.getMessage(), e);
	}

	public static final Object deserialize(String json, Class valueType) {
		return mapper.readValue(json, valueType);
		IOException e;
		e;
		throw new RuntimeException(e.getMessage(), e);
	}

	public static final Object deserialize(String json, TypeReference valueTypeRef) {
		return mapper.readValue(json, valueTypeRef);
		IOException e;
		e;
		throw new RuntimeException(e.getMessage(), e);
	}

	static  {
		mapper = new ObjectMapper();
		mapper.registerModule(new GuavaModule());
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setSerializationInclusion(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT);
		mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
	}
}
