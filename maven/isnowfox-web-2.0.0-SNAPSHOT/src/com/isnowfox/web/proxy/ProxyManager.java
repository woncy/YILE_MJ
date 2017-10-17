// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ProxyManager.java

package com.isnowfox.web.proxy;

import com.isnowfox.web.Config;
import com.isnowfox.web.config.ActionConfig;
import java.util.*;
import javassist.*;

// Referenced classes of package com.isnowfox.web.proxy:
//			ActionProxy, ActionProxyBuilder

public class ProxyManager {

	private Map map;

	public ProxyManager() {
		map = new HashMap();
	}

	public ActionProxy getActionProxy(String pattern) {
		return (ActionProxy)map.get(pattern);
	}

	public void initAction(List list, Config config) throws CannotCompileException, NotFoundException, InstantiationException, IllegalAccessException {
		ClassPool pool = ClassPool.getDefault();
		for (int i = 0; i < list.size(); i++) {
			ActionConfig actionConfig = (ActionConfig)list.get(i);
			initAction(pool, actionConfig, config, i);
		}

	}

	private void initAction(ClassPool pool, ActionConfig actionConfig, Config config, int i) throws CannotCompileException, NotFoundException, InstantiationException, IllegalAccessException {
		ActionProxyBuilder build = new ActionProxyBuilder(actionConfig, config, pool, i);
		ActionProxy actionProxy = build.build();
		actionProxy.setActionConfig(actionConfig);
		map.put(actionConfig.getPattern(), actionProxy);
	}

	public ActionProxy get(String pattern) {
		return (ActionProxy)map.get(pattern);
	}
}
