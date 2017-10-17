// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   CmsMain.java

package com.isnowfox.cms;

import com.isnowfox.cms.action.IndexAction;
import com.isnowfox.core.SpringIocFactory;
import com.isnowfox.web.*;
import com.isnowfox.web.config.ActionConfig;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CmsMain {

	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/cms/CmsMain);

	public CmsMain() {
	}

	public static void main(String args[]) {
		Config cfg = new Config();
		SpringIocFactory ioc = new SpringIocFactory(new String[] {
			"CmsContext.xml"
		});
		cfg.setDebug(true);
		cfg.setPorts(new int[] {
			8080
		});
		cfg.setSuffix(null);
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		cfg.setTemplateFilePath("template");
		cfg.setStaticFilePath(loader.getResource("page").getFile());
		log.info("启动http服务器");
		Server server = new Server(cfg);
		server.setIocFactory(ioc);
		try {
			server.regSingleton(com/isnowfox/cms/action/IndexAction, "index", "index1").json().param(java/lang/String, ParameterType.HEADER, "User-Agent");
		}
		catch (Exception e) {
			log.info("验证错误,action注册失败!", e);
		}
		try {
			server.start();
		}
		catch (Exception e) {
			log.error("严重错误,启动失败!", e);
		}
	}

}
