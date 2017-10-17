// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   BaseDispatcher.java

package com.isnowfox.web.impl;

import com.isnowfox.core.IocFactory;
import com.isnowfox.web.*;
import com.isnowfox.web.codec.Uri;
import com.isnowfox.web.config.ActionConfig;
import com.isnowfox.web.config.HeaderConfig;
import com.isnowfox.web.proxy.ActionProxy;
import com.isnowfox.web.proxy.ProxyManager;
import java.io.FileNotFoundException;
import java.util.List;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseDispatcher
	implements Dispatcher {

	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/web/impl/BaseDispatcher);
	private final Config config;
	private final Server server;
	private final Context context = Context.getInstance();
	private final ProxyManager proxyManager = new ProxyManager();
	private final IocFactory iocFactory;
	private final ActionObjectPool actionObjectPool;

	BaseDispatcher(Config config, Server server) throws Exception {
		this.config = config;
		this.server = server;
		iocFactory = server.getIocFactory();
		proxyManager.initAction(server.getActionList(), config);
		actionObjectPool = new ActionObjectPool(iocFactory);
	}

	public abstract boolean disposeStaticFile(Uri uri, Response response) throws Exception;

	public void service(Uri uri, Request req, Response resp) {
		try {
			if (uri.isExtensionType(config.getSuffix())) {
				ActionProxy p = proxyManager.get(uri.getPattern());
				p.invoke(iocFactory, actionObjectPool, req, resp);
				HeaderConfig headerConfig = p.getActionConfig().getHeaderConfig();
				List list = headerConfig.getList();
				for (int i = 0; i < list.size(); i++) {
					com.isnowfox.web.config.HeaderConfig.Item item = (com.isnowfox.web.config.HeaderConfig.Item)list.get(i);
					resp.setHeader(item.name, item.value);
				}

				resp.flushAndClose();
			} else {
				disposeStaticFile(uri, resp);
				resp.flushAndClose();
			}
		}
		catch (FileNotFoundException e) {
			resp.sendError(HttpResponseStatus.NOT_FOUND);
			log.error((new StringBuilder()).append("文件没找到").append(e.getMessage()).toString(), e);
		}
		catch (Exception e) {
			resp.sendError(HttpResponseStatus.INTERNAL_SERVER_ERROR);
			log.error("服务器错误", e);
		}
	}

}
