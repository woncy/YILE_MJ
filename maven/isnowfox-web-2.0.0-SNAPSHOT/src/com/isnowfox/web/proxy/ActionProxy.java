// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ActionProxy.java

package com.isnowfox.web.proxy;

import com.isnowfox.core.IocFactory;
import com.isnowfox.web.*;
import com.isnowfox.web.config.ActionConfig;

public abstract class ActionProxy {

	private ActionConfig actionConfig;
	ViewTypeInterface viewTypes[];

	public ActionProxy() {
	}

	public abstract void invoke(IocFactory iocfactory, ActionObjectPool actionobjectpool, Request request, Response response) throws Exception;

	public void setActionConfig(ActionConfig actionConfig) {
		this.actionConfig = actionConfig;
	}

	public ActionConfig getActionConfig() {
		return actionConfig;
	}
}
