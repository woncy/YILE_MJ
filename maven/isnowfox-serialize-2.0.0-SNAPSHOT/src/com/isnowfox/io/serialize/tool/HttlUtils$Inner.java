// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttlUtils.java

package com.isnowfox.io.serialize.tool;

import httl.Engine;
import java.io.IOException;
import java.util.Properties;

// Referenced classes of package com.isnowfox.io.serialize.tool:
//			HttlUtils

private static class HttlUtils$Inner {

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

	private HttlUtils$Inner() {
	}
}
