// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   HttlUtils.java

package org.forkjoin.jdbckit.mysql;

import httl.Engine;
import java.io.IOException;
import java.util.Properties;

// Referenced classes of package org.forkjoin.jdbckit.mysql:
//			HttlUtils

private static class HttlUtils$Inner {

	static final Engine engine;

	static  {
		Properties configProperties = new Properties();
		try {
			configProperties.load(org/forkjoin/jdbckit/mysql/HttlUtils.getResourceAsStream("/org/forkjoin/jdbckit/mysql/httl.properties"));
		}
		catch (IOException e) {
			throw new RuntimeException();
		}
		engine = Engine.getEngine(configProperties);
	}

	private HttlUtils$Inner() {
	}
}
