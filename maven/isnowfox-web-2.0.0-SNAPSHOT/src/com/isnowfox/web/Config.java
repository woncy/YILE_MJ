// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   Config.java

package com.isnowfox.web;

import com.isnowfox.web.codec.Uri;
import com.isnowfox.web.config.ActionConfig;
import java.nio.charset.Charset;

// Referenced classes of package com.isnowfox.web:
//			ViewType, ViewTypeInterface

public final class Config {
	public static final class StaticHandleType extends Enum {

		public static final StaticHandleType NONE;
		public static final StaticHandleType CACHE;
		public static final StaticHandleType NOT_CACHE;
		private static final StaticHandleType $VALUES[];

		public static StaticHandleType[] values() {
			return (StaticHandleType[])$VALUES.clone();
		}

		public static StaticHandleType valueOf(String name) {
			return (StaticHandleType)Enum.valueOf(com/isnowfox/web/Config$StaticHandleType, name);
		}

		static  {
			NONE = new StaticHandleType("NONE", 0);
			CACHE = new StaticHandleType("CACHE", 1);
			NOT_CACHE = new StaticHandleType("NOT_CACHE", 2);
			$VALUES = (new StaticHandleType[] {
				NONE, CACHE, NOT_CACHE
			});
		}

		private StaticHandleType(String s, int i) {
			super(s, i);
		}
	}


	private Charset requestCharset;
	private Charset responseCharset;
	private int ports[] = {
		80
	};
	private int responseBufferSize;
	private String templateFilePath;
	private String staticFilePath;
	private String httlTemplateFileSuffix;
	private StaticHandleType staticType;
	private boolean isDebug;
	private int outBufferSize;
	private ViewTypeInterface defaultViewType;
	private int ParamsMax;
	private String indexPage;
	private String suffix;
	private boolean isIocCreateObject;
	private boolean enableHttl;

	public Config() {
		requestCharset = Charset.forName("utf-8");
		responseCharset = Charset.forName("utf-8");
		responseBufferSize = 2048;
		templateFilePath = "src/main/resources/template/";
		staticFilePath = "src/main/resources/page/";
		httlTemplateFileSuffix = "httl";
		staticType = StaticHandleType.NOT_CACHE;
		isDebug = false;
		outBufferSize = 8192;
		defaultViewType = ViewType.HTTL;
		ParamsMax = 1024;
		indexPage = "index.html";
		suffix = "do";
		enableHttl = true;
	}

	public ActionConfig reg(Class cls, String methodName) {
		return null;
	}

	public Charset getRequestCharset() {
		return requestCharset;
	}

	public void setRequestCharset(Charset requestCharset) {
		this.requestCharset = requestCharset;
	}

	public Charset getResponseCharset() {
		return responseCharset;
	}

	public void setResponseCharset(Charset responseCharset) {
		this.responseCharset = responseCharset;
	}

	public void setCharset(Charset charset) {
		responseCharset = charset;
		requestCharset = charset;
	}

	public int[] getPorts() {
		return ports;
	}

	public transient void setPorts(int ports[]) {
		this.ports = ports;
	}

	public int getResponseBufferSize() {
		return responseBufferSize;
	}

	void setResponseBufferSize(int responseBufferSize) {
		this.responseBufferSize = responseBufferSize;
	}

	public void setTemplateFilePath(String templateFilePath) {
		this.templateFilePath = templateFilePath;
	}

	String getTemplateFilePath() {
		return templateFilePath;
	}

	public void setStaticFilePath(String staticFilePath) {
		this.staticFilePath = staticFilePath;
	}

	public String getStaticFilePath() {
		return staticFilePath;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public StaticHandleType getStaticType() {
		return staticType;
	}

	public void setStaticType(StaticHandleType staticType) {
		this.staticType = staticType;
	}

	public boolean isDebug() {
		return isDebug;
	}

	public void setDebug(boolean debug) {
		isDebug = debug;
	}

	public void setOutBufferSize(int outBufferSize) {
		this.outBufferSize = outBufferSize;
	}

	public int getOutBufferSize() {
		return outBufferSize;
	}

	public ViewTypeInterface getDefaultViewType() {
		return defaultViewType;
	}

	public void setDefaultViewType(ViewTypeInterface defaultViewType) {
		this.defaultViewType = defaultViewType;
	}

	public int getParamsMax() {
		return ParamsMax;
	}

	public void setParamsMax(int paramsMax) {
		ParamsMax = paramsMax;
	}

	public String getIndexPage() {
		return indexPage;
	}

	public void setIndexPage(String indexPage) {
		this.indexPage = indexPage;
	}

	public boolean isIocCreateObject() {
		return isIocCreateObject;
	}

	public void setIocCreateObject(boolean isIocCreateObject) {
		this.isIocCreateObject = isIocCreateObject;
	}

	public void setHttlTemplateFileSuffix(String httlTemplateFileSuffix) {
		this.httlTemplateFileSuffix = httlTemplateFileSuffix;
	}

	public String getHttlTemplateFileSuffix() {
		return httlTemplateFileSuffix;
	}

	public void setEnableHttl(boolean enableHttl) {
		this.enableHttl = enableHttl;
	}

	public boolean isEnableHttl() {
		return enableHttl;
	}
}
