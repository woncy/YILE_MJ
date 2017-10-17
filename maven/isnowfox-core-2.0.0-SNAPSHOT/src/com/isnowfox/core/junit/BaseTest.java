// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BaseTest.java

package com.isnowfox.core.junit;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseTest extends TestCase {

	protected static final Logger log = LoggerFactory.getLogger(com/isnowfox/core/junit/BaseTest);

	public BaseTest() {
	}

	protected void setUp() throws Exception {
		try {
			super.setUp();
		}
		catch (Exception t) {
			log.error(t.getMessage(), t);
			throw t;
		}
	}

	protected void runTest() throws Throwable {
		try {
			super.runTest();
		}
		catch (Throwable t) {
			log.error(t.getMessage(), t);
			throw t;
		}
	}

}
