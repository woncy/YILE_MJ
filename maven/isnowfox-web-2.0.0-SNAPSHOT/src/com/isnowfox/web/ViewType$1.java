// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   ViewType.java

package com.isnowfox.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.isnowfox.core.JsonTransform;
import com.isnowfox.util.JsonUtils;

// Referenced classes of package com.isnowfox.web:
//			ViewType, Request, Response

static class ViewType$1 extends ViewType {

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

	ViewType$1(String s, int i) {
		super(s, i, null);
	}
}
