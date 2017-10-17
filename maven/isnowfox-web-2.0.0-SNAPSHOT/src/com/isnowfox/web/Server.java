// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Server.java

package com.isnowfox.web;

import com.isnowfox.core.IocFactory;
import com.isnowfox.web.adapter.WebPipelineFactory;
import com.isnowfox.web.config.ActionConfig;
import com.isnowfox.web.impl.DispatcherFactory;
import com.isnowfox.web.listener.WebListener;
import com.isnowfox.web.listener.WebRequestEvent;
import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.Executors;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package com.isnowfox.web:
//			ViewType, Config, ViewTypeInterface, Dispatcher, 
//			Request

public class Server {

	private static final Runtime runtime = Runtime.getRuntime();
	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/web/Server);
	private List actionList;
	private List viewTypes;
	private IocFactory iocFactory;
	private Config config;
	private Dispatcher dispatcher;
	private List listeners;
	private ServerBootstrap webBootstrap;

	public static Server create(Config config) {
		return new Server(config);
	}

	public Server(Config config) {
		actionList = new ArrayList();
		viewTypes = new ArrayList();
		listeners = new ArrayList();
		this.config = config;
	}

	void initConfig() {
	}

	public void start() throws Exception {
		ViewType aviewtype[] = ViewType.values();
		int j = aviewtype.length;
		for (int k = 0; k < j; k++) {
			ViewType viewType = aviewtype[k];
			regViewType(viewType);
		}

		dispatcher = DispatcherFactory.create(config, this);
		long freeMem = runtime.freeMemory();
		log.info("开始启动:[JVM freeMem:{}MB ; JVM maxMemory:{}MB ; JVM totalMemory:{}MB ]", new Object[] {
			Float.valueOf((float)freeMem / 1024F / 1024F), Float.valueOf((float)runtime.maxMemory() / 1024F / 1024F), Float.valueOf((float)runtime.totalMemory() / 1024F / 1024F)
		});
		try {
			webBootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool()));
			webBootstrap.setPipelineFactory(new WebPipelineFactory(this, config));
			int ports[] = config.getPorts();
			for (int i = 0; i < ports.length; i++) {
				webBootstrap.bind(new InetSocketAddress(ports[i]));
			}

			log.info("服务器启动成功:[JVM freeMem:{}MB ; JVM maxMemory:{}MB ; JVM totalMemory:{}MB ]", new Object[] {
				Float.valueOf((float)freeMem / 1024F / 1024F), Float.valueOf((float)runtime.maxMemory() / 1024F / 1024F), Float.valueOf((float)runtime.totalMemory() / 1024F / 1024F)
			});
			System.gc();
		}
		catch (Exception e) {
			log.error("严重错误!服务器启动失败", e);
		}
	}

	public void close() {
		webBootstrap.shutdown();
	}

	public ActionConfig regSingleton(Class cls, String methodAndPattern) {
		return regSingleton(cls, methodAndPattern, methodAndPattern);
	}

	public ActionConfig regSingleton(Class cls, String method, String pattern) {
		ActionConfig action = new ActionConfig(cls, method, pattern, config, com.isnowfox.web.config.ActionConfig.LiefCycleType.SINGLETON);
		actionList.add(action);
		return action;
	}

	public ActionConfig regRequest(Class cls, String methodAndPattern) {
		return regRequest(cls, methodAndPattern, methodAndPattern);
	}

	public ActionConfig regRequest(Class cls, String method, String pattern) {
		ActionConfig action = new ActionConfig(cls, method, pattern, config, com.isnowfox.web.config.ActionConfig.LiefCycleType.REQUEST);
		actionList.add(action);
		return action;
	}

	public ViewTypeInterface regViewType(ViewTypeInterface viewType) throws Exception {
		viewType.init(config);
		viewTypes.add(viewType);
		return viewType;
	}

	public void addListener(WebListener listener) {
		listeners.add(listener);
	}

	public WebRequestEvent fireRequestStartEvent(Request req) {
		WebRequestEvent evt = new WebRequestEvent(req);
		if (!listeners.isEmpty()) {
			WebListener l;
			for (Iterator iterator = listeners.iterator(); iterator.hasNext(); l.requestHandlerStart(evt)) {
				l = (WebListener)iterator.next();
			}

		}
		return evt;
	}

	public void fireRequestEndEvent(WebRequestEvent evt) {
		if (!listeners.isEmpty()) {
			WebListener l;
			for (Iterator iterator = listeners.iterator(); iterator.hasNext(); l.requestHandlerEnd(evt)) {
				l = (WebListener)iterator.next();
			}

		}
	}

	public IocFactory getIocFactory() {
		return iocFactory;
	}

	public void setIocFactory(IocFactory iocFactory) {
		this.iocFactory = iocFactory;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public List getActionList() {
		return Collections.unmodifiableList(actionList);
	}

}
