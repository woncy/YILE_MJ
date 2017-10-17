// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   XmlUtils.java

package com.isnowfox.util;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.thoughtworks.xstream.XStream;
import java.io.IOException;

public final class XmlUtils {

	private static final JacksonXmlModule module;
	private static final ObjectMapper mapper;
	private static final XStream xstream;

	public XmlUtils() {
	}

	public static final String serialize(Object o) {
		return xstream.toXML(o);
	}

	public static final Object deserialize(String xml) {
		return xstream.fromXML(xml);
	}

	public static final String baseSerialize(Object o) {
		return mapper.writeValueAsString(o);
		IOException e;
		e;
		throw new RuntimeException(e.getMessage(), e);
	}

	public static final Object baseDeserialize(String xml, Class valueType) {
		return mapper.readValue(xml, valueType);
		IOException e;
		e;
		throw new RuntimeException(e.getMessage(), e);
	}

	static  {
		module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		mapper = new XmlMapper(module);
		mapper.registerModule(new GuavaModule());
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		xstream = new XStream();
		xstream.aliasPackage("igu", "io.grass.util");
		xstream.aliasPackage("kgb", "koc.game.build");
		xstream.aliasPackage("kg", "koc.game");
		xstream.aliasPackage("kgt", "koc.game.task");
		xstream.aliasPackage("kgc", "koc.net.c2s");
		xstream.aliasPackage("kgs", "koc.net.s2c");
		xstream.aliasPackage("kgi", "koc.net.info");
	}
}
