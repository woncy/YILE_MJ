// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   PayAction.java

package com.isnowfox.game.platform.tgw;

import com.google.common.collect.Maps;
import com.isnowfox.game.platform.GamePayResult;
import com.isnowfox.game.platform.Platform;
import com.isnowfox.web.Context;
import com.isnowfox.web.Request;
import java.io.FileNotFoundException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PayAction {

	private static final Logger log = LoggerFactory.getLogger(com/isnowfox/game/platform/tgw/PayAction);
	private Platform platform;
	private GamePayResult gamePayResult;

	public PayAction() {
		log.info("腾讯请求充值！");
	}

	public Object pay() throws FileNotFoundException {
		Request request = Context.getInstance().getRequest();
		int localPort = request.getLocalPort();
		if (localPort != 9001) {
			throw new FileNotFoundException("pay.do找不到");
		} else {
			Map allParams = request.getParamsMap();
			log.info("腾讯请求充值！{}", allParams);
			platform.payResult(allParams);
			Map map = Maps.newHashMap();
			map.put("ret", Integer.valueOf(0));
			map.put("msg", "OK");
			return map;
		}
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Object error() {
		Request request = Context.getInstance().getRequest();
		Map allParams = request.getParamsMap();
		gamePayResult.error(allParams, request.getRemoteInfo(), request.getHeader("user-agent"));
		return "ok";
	}

	public String gamelogin() {
		Request request = Context.getInstance().getRequest();
		Map allParams = request.getParamsMap();
		gamePayResult.login(allParams, request.getRemoteInfo(), request.getHeader("user-agent"));
		return "ok";
	}

	public void setGamePayResult(GamePayResult gamePayResult) {
		this.gamePayResult = gamePayResult;
	}

}
