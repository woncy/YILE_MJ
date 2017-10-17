// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   WebResponseEvent.java

package com.isnowfox.web.listener;

import com.isnowfox.web.Response;
import java.util.EventObject;

public class WebResponseEvent extends EventObject {

	private static final long serialVersionUID = 0xf7705ea9c1fdf70fL;

	public WebResponseEvent(Object source) {
		super(source);
	}

	public Response getResponse() {
		return (Response)super.getSource();
	}
}
