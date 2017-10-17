// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) braces fieldsfirst ansi nonlb space 
// Source File Name:   WebContextEvent.java

package com.isnowfox.web.listener;

import com.isnowfox.web.Context;
import java.util.EventObject;

public class WebContextEvent extends EventObject {

	private static final long serialVersionUID = 0xb43b87c49259de8aL;

	public WebContextEvent(Object source) {
		super(source);
	}

	public Context getContext() {
		return (Context)super.getSource();
	}
}
