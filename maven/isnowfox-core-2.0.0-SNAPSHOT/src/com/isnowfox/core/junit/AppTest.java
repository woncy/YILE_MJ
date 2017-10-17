// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   AppTest.java

package com.isnowfox.core.junit;

import junit.framework.TestCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public abstract class AppTest extends TestCase {

	protected static final Logger log = LoggerFactory.getLogger(com/isnowfox/core/junit/AppTest);
	private String configLocation;

	public AppTest(String configLocation) {
		this.configLocation = configLocation;
	}

	protected void setUp() throws Exception {
		try {
			super.setUp();
			(new ClassPathXmlApplicationContext(new String[] {
				configLocation
			})).getAutowireCapableBeanFactory().autowireBeanProperties(this, 2, false);
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
