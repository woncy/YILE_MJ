// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   TestUploadFile.java

package com.isnowfox.game.platform.tgw;

import com.qq.open.OpenApiV3;
import com.qq.open.OpensnsException;
import java.io.*;
import java.util.HashMap;
import org.apache.commons.httpclient.methods.multipart.FilePart;

public class TestUploadFile {

	public TestUploadFile() {
	}

	public static void main(String args[]) {
		String appid = "";
		String appkey = "";
		String openid = "";
		String openkey = "";
		String serverName = "";
		String pf = "tapp";
		OpenApiV3 sdk = new OpenApiV3(appid, appkey);
		sdk.setServerName(serverName);
		System.out.println("===========test AddPicWeibo===========");
		testAddPicWeibo(sdk, openid, openkey, pf);
	}

	public static void testAddPicWeibo(OpenApiV3 sdk, String openid, String openkey, String pf) {
		String scriptName = "/v3/t/add_pic_t";
		String protocol = "http";
		HashMap params = new HashMap();
		params.put("openid", openid);
		params.put("openkey", openkey);
		params.put("pf", pf);
		params.put("content", "Õº∆¨√Ë ˆ°£°£°£@xxx");
		params.put("format", "json");
		String filepath = "/data/home/coolinchen/photo/Õº∆¨.jpg";
		File p = new File(filepath);
		try {
			FilePart pic = new FilePart("pic", new File(filepath));
			String resp = sdk.apiUploadFile(scriptName, params, pic, protocol);
			System.out.println(resp);
		}
		catch (OpensnsException e) {
			System.out.printf("Request Failed. code:%d, msg:%s\n", new Object[] {
				Integer.valueOf(e.getErrorCode()), e.getMessage()
			});
			e.printStackTrace();
		}
		catch (FileNotFoundException filenotfoundexception) { }
	}
}
