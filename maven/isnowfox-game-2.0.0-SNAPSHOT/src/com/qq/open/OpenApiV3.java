// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   OpenApiV3.java

package com.qq.open;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.json.JSONException;
import org.json.JSONObject;

// Referenced classes of package com.qq.open:
//			OpensnsException, ErrorCode, SnsSigCheck, SnsNetwork, 
//			SnsStat

public class OpenApiV3 {

	private String appid;
	private String appkey;
	private String serverName;

	public OpenApiV3(String appid, String appkey) {
		this.appid = appid;
		this.appkey = appkey;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String api(String scriptName, HashMap params, String protocol) throws OpensnsException {
		if (params.get("openid") == null) {
			throw new OpensnsException(1801, "openid is empty");
		}
		if (!isOpenid((String)params.get("openid"))) {
			throw new OpensnsException(1802, "openid is invalid");
		}
		params.remove("sig");
		params.put("appid", appid);
		String method = "post";
		String secret = (new StringBuilder()).append(appkey).append("&").toString();
		String sig = SnsSigCheck.makeSig(method, scriptName, params, secret);
		params.put("sig", sig);
		StringBuilder sb = new StringBuilder(64);
		sb.append(protocol).append("://").append(serverName).append(scriptName);
		String url = sb.toString();
		HashMap cookies = null;
		long startTime = System.currentTimeMillis();
		printRequest(url, method, params);
		String resp = SnsNetwork.postRequest(url, params, cookies, protocol);
		JSONObject jo = null;
		try {
			jo = new JSONObject(resp);
		}
		catch (JSONException e) {
			throw new OpensnsException(1803, e);
		}
		int rc = jo.optInt("ret", 0);
		SnsStat.statReport(startTime, serverName, params, method, protocol, rc, scriptName);
		printRespond(resp);
		return resp;
	}

	public String apiUploadFile(String scriptName, HashMap params, FilePart fp, String protocol) throws OpensnsException {
		if (params.get("openid") == null) {
			throw new OpensnsException(1801, "openid is empty");
		}
		if (!isOpenid((String)params.get("openid"))) {
			throw new OpensnsException(1802, "openid is invalid");
		}
		params.remove("sig");
		params.put("appid", appid);
		String method = "post";
		String secret = (new StringBuilder()).append(appkey).append("&").toString();
		String sig = SnsSigCheck.makeSig(method, scriptName, params, secret);
		params.put("sig", sig);
		StringBuilder sb = new StringBuilder(64);
		sb.append(protocol).append("://").append(serverName).append(scriptName);
		String url = sb.toString();
		HashMap cookies = null;
		long startTime = System.currentTimeMillis();
		printRequest(url, method, params);
		String resp = SnsNetwork.postRequestWithFile(url, params, cookies, fp, protocol);
		JSONObject jo = null;
		try {
			jo = new JSONObject(resp);
		}
		catch (JSONException e) {
			throw new OpensnsException(1803, e);
		}
		int rc = jo.optInt("ret", 0);
		SnsStat.statReport(startTime, serverName, params, method, protocol, rc, scriptName);
		printRespond(resp);
		return resp;
	}

	private void printRequest(String url, String method, HashMap params) throws OpensnsException {
		System.out.println("==========Request Info==========\n");
		System.out.println((new StringBuilder()).append("method:  ").append(method).toString());
		System.out.println((new StringBuilder()).append("url:  ").append(url).toString());
		System.out.println("params:");
		System.out.println(params);
		System.out.println("querystring:");
		StringBuilder buffer = new StringBuilder(128);
		for (Iterator iter = params.entrySet().iterator(); iter.hasNext();) {
			java.util.Map.Entry entry = (java.util.Map.Entry)iter.next();
			try {
				buffer.append(URLEncoder.encode((String)entry.getKey(), "UTF-8").replace("+", "%20").replace("*", "%2A")).append("=").append(URLEncoder.encode((String)entry.getValue(), "UTF-8").replace("+", "%20").replace("*", "%2A")).append("&");
			}
			catch (UnsupportedEncodingException e) {
				throw new OpensnsException(1804, e);
			}
		}

		String tmp = buffer.toString();
		tmp = tmp.substring(0, tmp.length() - 1);
		System.out.println(tmp);
		System.out.println();
	}

	private void printRespond(String resp) {
		System.out.println("===========Respond Info============");
		System.out.println(resp);
	}

	private boolean isOpenid(String openid) {
		return openid.length() == 32 && openid.matches("^[0-9A-Fa-f]+$");
	}
}
