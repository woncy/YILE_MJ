// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ViewType.java

package com.isnowfox.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.isnowfox.core.JsonTransform;
import com.isnowfox.util.JsonUtils;
import httl.Engine;
import httl.Template;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;

// Referenced classes of package com.isnowfox.web:
//			ViewType, View, Config, Context, 
//			Response, Request

static class ViewType$2 extends ViewType {

	private String extName;
	private Engine engine;

	public void init(Config config) throws Exception {
		Properties configure;
		InputStream is;
		if (!config.isEnableHttl()) {
			break MISSING_BLOCK_LABEL_133;
		}
		configure = new Properties();
		URL url = com/isnowfox/web/ViewType.getResource("/httl.properties");
		ViewType.access$100().info("配置路径:{}", url);
		is = url.openStream();
		configure.load(is);
		ViewType.access$100().info("启动httl模板引擎:[template.directory:{}]", config.getTemplateFilePath());
		configure.put("template.directory", config.getTemplateFilePath());
		engine = Engine.getEngine(configure);
		if (is != null) {
			is.close();
		}
		break MISSING_BLOCK_LABEL_107;
		Exception exception;
		exception;
		if (is != null) {
			is.close();
		}
		throw exception;
		extName = (new StringBuilder()).append(".").append(config.getHttlTemplateFileSuffix()).toString();
	}

	public void doView(String pattern, Object action, Object result, String value, Request request, Response response) throws Exception {
		if (engine == null) {
			throw new Exception("为开起httl支持！");
		}
		Map context = Maps.newHashMap();
		context.put("context", Context.getInstance().getAttributesMap());
		String viewName = null;
		if (result instanceof View) {
			View v = (View)result;
			viewName = v.getName();
			result = v.getValue();
		} else
		if (result instanceof CharSequence) {
			viewName = String.valueOf(result);
		}
		context.put("result", result);
		context.put("r", result);
		if (viewName == null) {
			viewName = pattern;
		}
		if (viewName != null) {
			engine.getTemplate((new StringBuilder()).append(viewName).append(extName).toString()).render(context, response.getWriter());
		}
	}

	ViewType$2(String s, int i) {
		super(s, i);
	}

	// Unreferenced inner class com/isnowfox/web/ViewType$1

/* anonymous class */
	static class ViewType._cls1 extends ViewType {

		public void doView(String pattern, Object action, Object result, String value, Request request, Response response) throws Exception {
			if (result != null) {
				if (result instanceof CharSequence) {
					String str = result.toString();
					response.oneWrite(str.getBytes(request.getCharset()));
				} else
				if (result instanceof JsonTransform) {
					JsonTransform jt = (JsonTransform)result;
					jt.toJson(response.getAppendable());
				} else {
					JsonUtils.mapper.writeValue(response.getWriter(), result);
				}
			}
		}

	}

}
