// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   WebRequestEvent.java

package com.isnowfox.web.listener;

import com.isnowfox.web.Request;
import java.util.EventObject;

public class WebRequestEvent extends EventObject {

	private static final long serialVersionUID = 0xa85fda802a66aef3L;

	public WebRequestEvent(Object source) {
		super(source);
	}

	public Request getRequest() {
		return (Request)super.getSource();
	}
}
