// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   SpringIocFactory.java

package com.isnowfox.core;

import java.util.Map;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// Referenced classes of package com.isnowfox.core:
//			IocFactory

public class SpringIocFactory
	implements IocFactory {

	private int autowireType;
	private ClassPathXmlApplicationContext ctx;

	public void initBean(Object o) {
		getAutowireCapableBeanFactory().autowireBeanProperties(o, autowireType, false);
	}

	public transient SpringIocFactory(String configsPath[]) {
		autowireType = 2;
		ctx = new ClassPathXmlApplicationContext(configsPath);
	}

	protected AutowireCapableBeanFactory getAutowireCapableBeanFactory() {
		return ctx.getAutowireCapableBeanFactory();
	}

	public Object getBean(Class cls) {
		String str[] = ctx.getBeanNamesForType(cls);
		if (!ArrayUtils.isEmpty(str)) {
			return ctx.getBean(str[0], cls);
		} else {
			return null;
		}
	}

	public Object getBean(String name, Class cls) {
		return ctx.getBean(name, cls);
	}

	public void setAutowireType(int autowireType) {
		this.autowireType = autowireType;
	}

	public Map getBeansOfType(Class cls) {
		return ctx.getBeansOfType(cls);
	}

	public Object getBean(String name) {
		return ctx.getBean(name);
	}

	public void destroy() {
		ctx.destroy();
	}
}
