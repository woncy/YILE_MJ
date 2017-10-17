// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttlUtils.java

package com.isnowfox.io.serialize.tool;

import httl.Engine;
import httl.Template;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Map;
import java.util.Properties;

public class HttlUtils {
	private static class Inner {

		static final Engine engine;

		static  {
			Properties configProperties = new Properties();
			try {
				configProperties.load(com/isnowfox/io/serialize/tool/HttlUtils.getResourceAsStream("/serializeHttl.properties"));
			}
			catch (IOException e) {
				throw new RuntimeException();
			}
			engine = Engine.getEngine(configProperties);
		}

		private Inner() {
		}
	}


	public HttlUtils() {
	}

	private static Engine getEngine() {
		return Inner.engine;
	}

	public static void render(String name, Map context, OutputStream out) throws IOException, ParseException {
		Engine engine = getEngine();
		Template template = engine.getTemplate(name);
		template.render(context, out);
	}
}
