// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   TestOpenApiV3.java

package com.isnowfox.game.platform.tgw;

import com.qq.open.OpenApiV3;
import com.qq.open.OpensnsException;
import java.io.PrintStream;
import java.util.HashMap;

public class TestOpenApiV3 {

	public TestOpenApiV3() {
	}

	public static void main(String args[]) {
		String appid = "";
		String appkey = "";
		String openid = "";
		String openkey = "";
		String serverName = "";
		String pf = "qzone";
		OpenApiV3 sdk = new OpenApiV3(appid, appkey);
		sdk.setServerName(serverName);
		System.out.println("===========test GetUserInfo===========");
		testGetUserInfo(sdk, openid, openkey, pf);
	}

	public static void testGetUserInfo(OpenApiV3 sdk, String openid, String openkey, String pf) {
		String scriptName = "/v3/user/get_info";
		String protocol = "http";
		HashMap params = new HashMap();
		params.put("openid", openid);
		params.put("openkey", openkey);
		params.put("pf", pf);
		try {
			String resp = sdk.api(scriptName, params, protocol);
			System.out.println(resp);
		}
		catch (OpensnsException e) {
			System.out.printf("Request Failed. code:%d, msg:%s\n", new Object[] {
				Integer.valueOf(e.getErrorCode()), e.getMessage()
			});
			e.printStackTrace();
		}
	}
}
