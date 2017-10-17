// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   TgwPayHttp.java

package com.isnowfox.game.platform.tgw;

import com.isnowfox.core.IocFactory;
import com.isnowfox.web.Config;
import com.isnowfox.web.Server;
import com.isnowfox.web.config.ActionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Referenced classes of package com.isnowfox.game.platform.tgw:
//			PayAction

public class TgwPayHttp {

	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/game/platform/tgw/TgwPayHttp);

	public TgwPayHttp() {
	}

	public static Server start(IocFactory ioc) {
		Server server;
		Config cfg = new Config();
		cfg.setPorts(new int[] {
			9001, 8000
		});
		cfg.setDebug(true);
		cfg.setSuffix("do");
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		cfg.setTemplateFilePath("template");
		cfg.setStaticFilePath(null);
		cfg.setEnableHttl(false);
		log.info("启动http服务器");
		server = new Server(cfg);
		server.setIocFactory(ioc);
		server.regSingleton(com/isnowfox/game/platform/tgw/PayAction, "pay", "pay").json();
		server.regSingleton(com/isnowfox/game/platform/tgw/PayAction, "error", "error").json().header("Access-Control-Allow-Origin", "*");
		server.regSingleton(com/isnowfox/game/platform/tgw/PayAction, "gamelogin", "gamelogin").json().header("Access-Control-Allow-Origin", "*");
		server.start();
		return server;
		Exception e;
		e;
		log.error("严重错误,启动失败!", e);
		return server;
	}

}
